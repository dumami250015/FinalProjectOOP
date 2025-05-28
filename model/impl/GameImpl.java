package model.impl;

import model.api.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public final class GameImpl implements Game {
    public enum PlantType {
        None,
        Sunflower,
        Peashooter,
        Wallnut
    }

    private static final int HOUSE_X_POSITION = 150;

    //Zombie
    private static final int DELTA_ZOMBIE = 10;
    private static final int DELTA_TIME_FIRST_ZOMBIE = 4000;

    //Base plant
    private static final int DAMAGE_BASE_PLANT = 20;
    private static final int HEALTH_BASE_PLANT = 100;
    private static final int COOLDOWN_BASE_PLANT = 3000;
    private static final int DELTA_PLANT = 35;
    private static final int DELTA_Y_PLANT = 63;

    //Bullet
    private static final int DELTA_X_BULLET = 30;
    private static final int BULLET_SPEED = 15;

    private final World world;
    private final GameState gameState;
    private final FactorySun factorySun;
    private final FactoryZombie factoryZombie;

    private Set<Peashooter> peashooters = new HashSet<>();
    private Set<Plant> plants = new HashSet<>();
    private Set<Zombie> zombies = new HashSet<>();
    private Set<Bullet> bullets = new HashSet<>();
    private Set<Sun> suns = new HashSet<>();

    private final long deltaTimeSunDecrement;
    private final long deltaTimeZombieDecrement;

    private long timeOfLastCreatedSun;
    private long timeOfLastCreatedZombie;
    private long deltaTimeSun;
    private long deltaTimeZombie;

    private boolean canSingleZombieGenerate;
    private int wavePassed;

    private final Random random = new Random();

    public GameImpl(final World world) {
        this.world = world;
        gameState = new GameStateImpl(this.world.getLevel().getZombieCount());
        factorySun = new FactorySun();
        factoryZombie = new FactoryZombie();

        deltaTimeSun = this.world.getLevel().getSunSpawnRate();
        deltaTimeZombie = this.world.getLevel().getZombieSpawnRate();
        deltaTimeSunDecrement = this.world.getLevel().getSunSpawnRateDecrementRange();
        deltaTimeZombieDecrement = this.world.getLevel().getZombieSpawnRateDecrementRange();

        canSingleZombieGenerate = true;
    }

    @Override
    public boolean isGameOver() {
        if (gameState.areAllZombieKilled()) {
            gameState.setWinState(true);
            return true;
        }
        for (final var zombie: zombies) {
            if (zombie.getPosition().getX() <= HOUSE_X_POSITION) {
                gameState.setWinState(false);
                return true;
            }
        }
        return false;
    }

    private void moveEntities() {
        for (final var zombie: zombies) {
            if (zombie.isCanGo()) {
                zombie.moveLeft();
            }
        }
        for (final var sun: suns) {
            sun.fallDown();
        }
        for (final var bullet: bullets) {
            bullet.move();
        }
    }

    private void removeKilledSuns() {
        final Set<Sun> remainingSuns = new HashSet<>();
        for (final var sun: suns) {
            if (sun.isAlive()) {
                remainingSuns.add(sun);
            }
        }
        suns = remainingSuns;
    }

    private boolean hasDeltaTimePassed(final long previousTime, final long currentTime, final long delta) {
        return (currentTime - previousTime) >= delta;
    }

    private void newSunGenerate(final long currentTime) {
        if (hasDeltaTimePassed(timeOfLastCreatedSun, currentTime, deltaTimeSun)) {
            timeOfLastCreatedSun = currentTime;
            suns.add((SunImpl) factorySun.createEntity());
            final long deltaDecrement = random.nextLong(2 * deltaTimeSunDecrement) - deltaTimeSunDecrement;
            deltaTimeSun -= deltaDecrement;
        }
    }

    private void newZombieGenerate(final long elapsed) {
        if (hasDeltaTimePassed(timeOfLastCreatedZombie, elapsed, deltaTimeZombie) && canSingleZombieGenerate ||
            hasDeltaTimePassed(timeOfLastCreatedSun, elapsed, DELTA_TIME_FIRST_ZOMBIE) && gameState.getZombieGenerated() == 0) {
            timeOfLastCreatedZombie = elapsed;
            zombies.add((ZombieImpl) factoryZombie.createEntity());
            final long deltaDecrement = random.nextLong(2 * deltaTimeZombieDecrement) - deltaTimeZombieDecrement;
            deltaTimeZombie -= deltaDecrement;

            gameState.increaseZombieGenerated();
        }
    }

    private void createWave() {
        final int totZombies = world.getLevel().getZombieCount();
        final int totZombieWave = world.getLevel().getZombieInWaveCount();

        if (gameState.getZombieGenerated() >= totZombies - totZombieWave &&
            wavePassed < world.getLevel().getZombieWaveCount()) {
            canSingleZombieGenerate = false;
            wavePassed++;

            final Set<Zombie> newWave = factoryZombie.createEntities(totZombieWave)
                    .stream()
                    .map(entity -> (Zombie) entity)
                    .collect(Collectors.toSet());

            zombies.addAll(newWave);
            gameState.increaseZombieGenerated();
        }
    }

    private void zombieEatPlant(final Zombie zombie, final Plant plant) {
        final long zombieLastAttack = zombie.getLastTimeAttack();
        final long currentTime = System.currentTimeMillis();
        if (currentTime - zombieLastAttack > zombie.getCoolDown()) {
            plant.receiveDamage(zombie.getDamage());
            zombie.setLastTimeAttack(currentTime);
        }
    }

    private void checkCollision() {
        final Set<Zombie> zombieTemp = new HashSet<>();
        final Set<Plant> plantTemp = new HashSet<>();
        final Set<Bullet> bulletTemp = new HashSet<>();
        bulletTemp.addAll(bullets);
        zombieTemp.addAll(zombies);
        plantTemp.addAll(plants);

        zombies.forEach(zombie -> plants.stream()
                .filter(plant -> (plant.getPosition().getY() == zombie.getPosition().getY() + DELTA_Y_PLANT
                        || plant.getPosition().getY() == zombie.getPosition().getY() + DELTA_Y_PLANT - 3)
                        && zombie.getPosition().getX() <= plant.getPosition().getX() + DELTA_PLANT)
                .forEach(plant -> {
                    zombieEatPlant(zombie, plant);
                    zombie.setCanGo(false);
                    if (!plant.isAlive()) {
                        plantTemp.remove(plant);
                        zombie.setCanGo(true);
                    }
                }));

        bullets.forEach(bullet -> zombies.stream()
                .filter(zombie -> (bullet.getPosition().getY() == zombie.getPosition().getY() + DELTA_Y_PLANT
                        || bullet.getPosition().getY() == zombie.getPosition().getY() + DELTA_Y_PLANT - 3)
                        && bullet.getPosition().getX() >= zombie.getPosition().getX() - DELTA_ZOMBIE)
                .forEach(zombie -> {
                    zombie.receiveDamage(bullet.getDamage());
                    bulletTemp.remove(bullet);
                    if (!zombie.isAlive()) {
                        zombieTemp.remove(zombie);
                        gameState.increaseZombieKilled();
                    }
                }));

        bullets = bulletTemp;
        plants = plantTemp;
        zombies = zombieTemp;
    }

    private void peashootersShoot() {
        peashooters.stream().filter(peashooter -> System.currentTimeMillis() - peashooter.getLastTimeAttack() > peashooter.getCoolDown())
                .forEach(peashooter -> zombies.stream()
                        .filter(zombie -> peashooter.getPosition().getY() == zombie.getPosition().getY() + DELTA_Y_PLANT
                        || peashooter.getPosition().getY() == zombie.getPosition().getY() + DELTA_Y_PLANT - 3)
                        .forEach(zombie -> {
                            bullets.add(new BulletImpl(
                                    new Pair<>(peashooter.getPosition().getX() + DELTA_X_BULLET, peashooter.getPosition().getY()),
                                    BULLET_SPEED,
                                    peashooter.getDamage(),
                                    "Bullet"));
                            peashooter.setLastTimeAttack(System.currentTimeMillis());
                        }));
    }

    @Override
    public void update(final long elapsed) {
        checkCollision();
        peashootersShoot();
        removeKilledSuns();
        moveEntities();
        newSunGenerate(elapsed);
        createWave();
        newZombieGenerate(elapsed);
    }

    @Override
    public boolean createPlant(final Pair<Integer, Integer> position, Plant plant) {
        if (gameState.getSunScore() >= plant.getPlantCost()) {
            if (plant instanceof Peashooter) {
                peashooters.add((Peashooter) plant);
            }
            plants.add(plant);
            gameState.decreaseSunScore(plant.getPlantCost());
            return true;
        }
        return false;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public Set<Entities> getEntities() {
        final Set<Entities> entities = new HashSet<>();
        entities.addAll(plants);
        entities.addAll(zombies);
        entities.addAll(suns);
        entities.addAll(bullets);
        entities.addAll(peashooters);
        return entities;
    }
}
