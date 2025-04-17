package model.impl;

import model.api.Plant;

public class PlantImpl implements Plant {
    public static final int PLANT_COST = 100;

    private final double damage;
    private final String entityName;
    private final Pair<Integer, Integer> position;
    private final long coolDown;

    private double remainingHealth;
    private long lastTimeAttack;

    public PlantImpl(final double damage, final String entityName, final Pair<Integer, Integer> position,
                     final long coolDown, final double remainingHealth) {
        this.damage = damage;
        this.entityName = entityName;
        this.position = position;
        this.coolDown = coolDown;
        this.remainingHealth = remainingHealth;
    }

    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public double getRemainingHealth() {
        return remainingHealth;
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    @Override
    public boolean isAlive() {
        return remainingHealth > 0;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    @Override
    public void receiveDamage(final double damage) {
        remainingHealth -= damage;
    }

    @Override
    public long getCoolDown() {
        return coolDown;
    }

    @Override
    public long getLastTimeAttack() {
        return lastTimeAttack;
    }

    @Override
    public void setLastTimeAttack(final long lastTimeAttack) {
        this.lastTimeAttack = lastTimeAttack;
    }

    @Override
    public int getPlantCost() {
        return PLANT_COST;
    }
}
