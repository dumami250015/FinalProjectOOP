package model.impl;

import model.api.Level;

public final class LevelImpl implements Level {
    private final int zombieCount;
    private final int zombieWaveCount;
    private final long sunSpawnRate;
    private final long zombieSpawnRate;
    private final long sunSpawnRateDecrementRange;
    private final long zombieSpawnRateDecrementRange;

    private static final double WAVE_PERCENTAGE = 0.3;

    public LevelImpl(final int zombieCount, final int zombieWaveCount, final long sunSpawnRate, final long zombieSpawnRate,
                     final long sunSpawnRateDecrementRange, final long zombieSpawnRateDecrementRange) {
        this.zombieCount = zombieCount;
        this.zombieWaveCount = zombieWaveCount;
        this.sunSpawnRate = sunSpawnRate;
        this.zombieSpawnRate = zombieSpawnRate;
        this.sunSpawnRateDecrementRange = sunSpawnRateDecrementRange;
        this.zombieSpawnRateDecrementRange = zombieSpawnRateDecrementRange;
    }

    @Override
    public int getZombieCount() {
        return zombieCount;
    }

    @Override
    public int getZombieWaveCount() {
        return zombieWaveCount;
    }

    @Override
    public int getZombieInWaveCount() {
        return (int) Math.floor(getZombieCount() * WAVE_PERCENTAGE);
    }

    @Override
    public long getSunSpawnRate() {
        return sunSpawnRate;
    }

    @Override
    public long getZombieSpawnRate() {
        return zombieSpawnRate;
    }

    @Override
    public long getSunSpawnRateDecrementRange() {
        return sunSpawnRateDecrementRange;
    }

    @Override
    public long getZombieSpawnRateDecrementRange() {
        return zombieSpawnRateDecrementRange;
    }
}
