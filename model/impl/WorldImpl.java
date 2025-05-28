package model.impl;

import model.api.Game;
import model.api.Level;
import model.api.World;

public final class WorldImpl implements World {
    private Level level;
    private Game game;
    private LevelsManager levelsManager;

    @Override
    public void setLevel(final Level level) {
        this.level = level;
    }

    @Override
    public void setGame(final Game game) {
        this.game = game;
    }

    @Override
    public void setLevelsManager(final LevelsManager levelsManager) {
        this.levelsManager = levelsManager;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public LevelsManager getLevelsManager() {
        return levelsManager;
    }
}
