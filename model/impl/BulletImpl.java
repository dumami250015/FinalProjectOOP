package model.impl;

import model.api.Bullet;

public final class BulletImpl implements Bullet {
    private Pair<Integer, Integer> position;
    private final int speed;
    private final double damage;
    private final String name;

    public BulletImpl(final Pair<Integer, Integer> position, final int speed, final double damage, final String name) {
        this.position = position;
        this.speed = speed;
        this.damage = damage;
        this.name = name;
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public String getEntityName() {
        return name;
    }

    @Override
    public void move() {
        this.position = new Pair<>(position.getX() + speed, position.getY());
    }
}
