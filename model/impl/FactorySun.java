package model.impl;

import model.api.Entities;
import model.api.FactoryEntities;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class FactorySun implements FactoryEntities {
    private static final int SPEED_Y_AXIS = 2;
    private static final int STARTING_X_GAME_FIELD = 220;
    private static final int FINAL_X_GAME_FIELD = 750;
    private static final int STARTING_Y_POSITION = -15;
    private final Random random = new Random();

    @Override
    public Entities createEntity() {
        return new SunImpl(
                new Pair<Integer, Integer>(random.nextInt(FINAL_X_GAME_FIELD - STARTING_X_GAME_FIELD) + STARTING_X_GAME_FIELD, STARTING_Y_POSITION),
                SPEED_Y_AXIS
            );
    }

    @Override
    public Set<Entities> createEntities(final int n) {
        final Set<Entities> sunSet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            sunSet.add(createEntity());
        }
        return sunSet;
    }
}
