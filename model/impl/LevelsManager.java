package model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class LevelsManager {
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 5;
    private static final int ZOMBIE_COUNT = 5;
    private static final int ZOMBIE_COUNT_STEP = 4;
    private static final int ZOMBIE_WAVE_COUNT = 1;
    private static final long SUN_SPAWN_RATE = 4000;
    private static final long SUN_SPAWN_RATE_STEP = 120;
    private static final long ZOMBIE_SPAWN_RATE = 12000;
    private static final long ZOMBIE_SPAWN_RATE_STEP = -300;
    private static final long SUN_SPAWN_RATE_DECREMENT_RANGE = 25;
    private static final long ZOMBIE_SPAWN_RATE_DECREMENT_RANGE = 75;
    private final List<LevelImpl> levelList;

    public LevelsManager(final int levelNumber) {
        int tempLevel = levelNumber;
        if (tempLevel < MIN_LEVEL) {
            tempLevel = MIN_LEVEL;
        } else if (tempLevel > MAX_LEVEL) {
            tempLevel = MAX_LEVEL;
        }

        levelList = new ArrayList<LevelImpl>();

        for (int i = 0; i < tempLevel; i++) {
            levelList.add(createLevel(i));
        }
    }

    public int getLevelCount() {
        return levelList.size();
    }

    public LevelImpl createLevel(final int delta) {
        if (delta < 0 || delta >= MAX_LEVEL) {
            throw new IllegalArgumentException("Invalid level number: " + delta);
        }

        return new LevelImpl(
                ZOMBIE_COUNT + ZOMBIE_COUNT_STEP * delta,
                 ZOMBIE_WAVE_COUNT,
                SUN_SPAWN_RATE + SUN_SPAWN_RATE_STEP * delta,
                ZOMBIE_SPAWN_RATE + ZOMBIE_SPAWN_RATE_STEP * delta,
                SUN_SPAWN_RATE_DECREMENT_RANGE + SUN_SPAWN_RATE_DECREMENT_RANGE * delta,
                ZOMBIE_SPAWN_RATE_DECREMENT_RANGE + ZOMBIE_SPAWN_RATE_DECREMENT_RANGE * delta
        );
    }

    public LevelImpl getLevel(final Optional<Integer> index) {
        if (index.isEmpty()) {
            throw new IllegalArgumentException("No valid level selected");
        }
        if (index.get() >= getLevelCount()) {
            return getLevel(Optional.of(getLevelCount() - 1));
        } else if (index.get() < 0) {
            return getLevel(Optional.of(0));
        }
        return levelList.get(index.get());
    }

    public List<LevelImpl> getLevelList() {
        if (levelList.isEmpty() || levelList == null) {
            throw new IllegalStateException("No valid level to load");
        }
        return new ArrayList<>(levelList);
    }
}
