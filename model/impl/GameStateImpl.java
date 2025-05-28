package model.impl;

import model.api.GameState;

import java.util.Optional;

public final class GameStateImpl implements GameState {
    private static final int INCREASING_SUN = 25;
    private static final int INITIAL_SUNS = 100;
    private final int totalZombies;
    private int zombieKilled;
    private int zombieGenerated;
    private int sunScore;

    private Optional<Boolean> winState;

    public GameStateImpl(final int totalZombies) {
        this.totalZombies = totalZombies;
        zombieKilled = 0;
        sunScore = INITIAL_SUNS;
        winState = Optional.empty();
    }

    @Override
    public void increaseZombieGenerated() {
        zombieGenerated++;
    }

    @Override
    public void increaseZombieKilled() {
        zombieKilled++;
    }

    @Override
    public void increaseSunScore() {
        sunScore += INCREASING_SUN;
    }

    @Override
    public void decreaseSunScore(final int costPlant) {
        sunScore -= costPlant;
    }

    @Override
    public int getZombieKilled() {
        return zombieKilled;
    }

    @Override
    public int getZombieGenerated() {
        return zombieGenerated;
    }

    @Override
    public int getSunScore() {
        return sunScore;
    }

    @Override
    public boolean areAllZombieKilled() {
        return zombieKilled == totalZombies;
    }

    @Override
    public void setWinState(final boolean winState) {
        this.winState = Optional.of(winState);
    }

    @Override
    public Optional<Boolean> getWinState() {
        return winState;
    }
}
