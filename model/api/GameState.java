package model.api;

import java.util.Optional;

public interface GameState {
    void increaseZombieGenerated();

    void increaseZombieKilled();

    void increaseSunScore();

    void decreaseSunScore(int costPlant);

    int getZombieKilled();

    int getZombieGenerated();

    int getSunScore();

    boolean areAllZombieKilled();

    void setWinState(boolean winState);

    Optional<Boolean> getWinState();
}
