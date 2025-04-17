package model.api;

import model.impl.LevelsManager;

public interface World {
    void setLevel(Level level);

    void setGame(Game game);

    void setLevelsManager(LevelsManager levelsManager);

    Level getLevel();

    Game getGame();

    LevelsManager getLevelsManager();
}
