package model.impl;

import model.api.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public final class GameImpl implements Game {
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

    //Movement of entities
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

    //Generate sun from the sky
    private void newSunGenerate(final long currentTime) {
        if (hasDeltaTimePassed(timeOfLastCreatedSun, currentTime, deltaTimeSun)) {
            timeOfLastCreatedSun = currentTime;
            suns.add((SunImpl) factorySun.createEntity());
            final long deltaDecrement = random.nextLong(2 * deltaTimeSunDecrement) - deltaTimeSunDecrement;
            deltaTimeSun -= deltaDecrement;
        }
    }

    //Generate sun from sunflower
//    private void generateSunflowerSuns(final long currentTime) {
//        for (Plant plant : plants) {
//            if (plant instanceof SunflowerImpl) {
//                SunflowerImpl sunflower = (SunflowerImpl) plant;
//                Sun newSun = sunflower.generateSun(currentTime);
//                if (newSun != null) {
//                    suns.add(newSun);
//                }
//            }
//        }
//    }

    //Generate zombie
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

    //Peashooter shoot
    private void plantsShoot() {
        plants.stream().filter(plant -> System.currentTimeMillis() - plant.getLastTimeAttack() > plant.getCoolDown())
                .forEach(plant -> zombies.stream()
                        .filter(zombie -> plant.getPosition().getY() == zombie.getPosition().getY() + DELTA_Y_PLANT
                        || plant.getPosition().getY() == zombie.getPosition().getY() + DELTA_Y_PLANT - 3)
                        .forEach(zombie -> {
                            bullets.add(new BulletImpl(
                                    new Pair<>(plant.getPosition().getX() + DELTA_X_BULLET, plant.getPosition().getY()),
                                    BULLET_SPEED,
                                    plant.getDamage(),
                                    "Bullet"));
                            plant.setLastTimeAttack(System.currentTimeMillis());
                        }));
    }

    @Override
    public void update(final long elapsed) {
        checkCollision();
        plantsShoot();
        removeKilledSuns();
        moveEntities();
        newSunGenerate(elapsed);
        createWave();
        newZombieGenerate(elapsed);
    }

    @Override
    public boolean createPlant(final Pair<Integer, Integer> position) {
        if (gameState.getSunScore() >= PlantImpl.PLANT_COST) {
            final PlantImpl newPlant = new PlantImpl(DAMAGE_BASE_PLANT, "Plant", position,
                    COOLDOWN_BASE_PLANT, HEALTH_BASE_PLANT);
            plants.add(newPlant);
            gameState.decreaseSunScore(newPlant.getPlantCost());
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
        return entities;
    }
}
