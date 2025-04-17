package model.api;

public interface Level {
    int getZombieCount();

    int getZombieWaveCount();

    int getZombieInWaveCount();

    long getSunSpawnRate();

    long getZombieSpawnRate();

    long getSunSpawnRateDecrementRange();

    long getZombieSpawnRateDecrementRange();
}
