package model.impl;

import model.api.Zombie;

public final class ZombieImpl implements Zombie {
    private final double damage;
    private final int xShift;
    private final long coolDown;

    private boolean canGo = true;
    private double remainingHealth;
    private long lastTimeAttack;
    private Pair<Integer, Integer> position;

    public ZombieImpl(final double damage, final int xShift, final long coolDown, final double remainingHealth, final Pair<Integer, Integer> position) {
        this.damage = damage;
        this.xShift = xShift;
        this.coolDown = coolDown;
        this.remainingHealth = remainingHealth;
        this.position = position;
        this.lastTimeAttack = 0;
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
        return "Zombie";
    }

    @Override
    public long getCoolDown() {
        return coolDown;
    }

    @Override
    public void receiveDamage(final double damageReceived) {
        remainingHealth -= damageReceived;
    }

    @Override
    public void setLastTimeAttack(final long lastTimeAttack) {
        this.lastTimeAttack = lastTimeAttack;
    }

    @Override
    public long getLastTimeAttack() {
        return lastTimeAttack;
    }

    @Override
    public void moveLeft() {
        position = new Pair<>(position.getX() - xShift, position.getY());
    }

    @Override
    public void setCanGo(final boolean canGo) {
        this.canGo = canGo;
    }

    @Override
    public boolean isCanGo() {
        return canGo;
    }
}
