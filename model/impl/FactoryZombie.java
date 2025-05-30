package model.impl;

import model.api.Entities;
import model.api.FactoryEntities;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class FactoryZombie implements FactoryEntities {
    private static final double DAMAGE = 50;
    private static final double REMAINING_HEALTH = 100;
    private static final int ZOMBIE_SPEED = 10;
//    private static final int ZOMBIE_SPEED = 2;
    private static final long COOLDOWN = 3000;
    private static final int STARTING_X_ZOMBIE = 800;
    private static final int STARTING_Y_ZOMBIE = 50;
    private static final int DELTA_Y_ZOMBIE = 110;
    private static final int POSSIBLE_Y = 5;

    private final Random random;

    public FactoryZombie() {
        random = new Random();
    }

    @Override
    public Entities createEntity() {
        return new ZombieImpl(DAMAGE, ZOMBIE_SPEED, COOLDOWN, REMAINING_HEALTH,
                new Pair<Integer, Integer>(STARTING_X_ZOMBIE, STARTING_Y_ZOMBIE + DELTA_Y_ZOMBIE * random.nextInt(0, POSSIBLE_Y)));
    }

    public Entities createEntity(Pair<Integer, Integer> position) {
        return new ZombieImpl(DAMAGE, ZOMBIE_SPEED, COOLDOWN, REMAINING_HEALTH, position);
    }

    @Override
    public Set<Entities> createEntities(final int n) {
        Set<Entities> zombieSet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            zombieSet.add(createEntity());
        }
        return zombieSet;
    }
}
