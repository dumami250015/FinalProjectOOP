package controller.impl;

import controller.api.*;
import model.api.*;
import model.impl.*;
import view.api.*;
import view.impl.*;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public final class MyControllerImpl implements MyController {
    private static final long PERIOD = 70;
    private static final int LEVEL_COUNT = 5;

    private final World world;
    private final View view;
    private Game game;
    private Optional<Integer> chosenLevel;

    public MyControllerImpl() {
        Locale.setDefault(Locale.ENGLISH);

        world = new WorldImpl();
        world.setLevelsManager(new LevelsManager(LEVEL_COUNT));

        this.view = new SwingViewImpl(this);
        chosenLevel = Optional.empty();
    }

    @Override
    public void callMainLoop() {
        new Thread(this::mainLoop).start();
    }

    private void mainLoop() {
        if (this.world == null || this.view == null) {
            return;
        }
        this.world.setLevel(this.world.getLevelsManager().getLevel(chosenLevel));
        this.world.setGame(new GameImpl(this.world));
        this.game = this.world.getGame();
        final long startTime = System.currentTimeMillis();
        while (!this.game.isGameOver()) {
            final long currentStartTime = System.currentTimeMillis();
            final long elapsed = currentStartTime - startTime;
            this.game.update(elapsed);
            this.view.update();
            waitForNextFrame(currentStartTime);
        }

        this.view.endGame(this.game.getGameState().getWinState());
    }

    private void waitForNextFrame(final long currentStartTime) {
        final long dt = System.currentTimeMillis() - currentStartTime;
        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public Set<Entities> getEntities() {
        if (this.game == null) {
            return new HashSet<Entities>();
        }
        return this.game.getEntities();
    }

    @Override
    public void newPlant(final Pair<Integer, Integer> position, Plant plant) {
        this.game.createPlant(position, plant);
    }

    @Override
    public void removeCellPlant(final Plant plant) {
        this.game.deleteCellPlant(plant);
    }

    @Override
    public void increaseSunPoints() {
        this.game.getGameState().increaseSunScore();
    }

    @Override
    public int getSunScore() {
        return this.game == null ? 0 : this.game.getGameState().getSunScore();
    }

    @Override
    public void chooseLevel(final int numberOfTheLevel) {
        this.chosenLevel = Optional.of(numberOfTheLevel);
    }

    @Override
    public Optional<Integer> getChosenLevel() {
        return this.chosenLevel;
    }

    @Override
    public int getLevelCount() {
        if (this.world.getLevelsManager() == null) {
            throw new IllegalStateException("There are no valid levels to load!");
        }
        return this.world.getLevelsManager().getLevelCount();
    }
}
