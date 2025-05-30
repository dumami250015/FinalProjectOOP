package model.impl;

import model.api.Entities;

public class LawnMower implements Entities {
    private static final String NAME = "Lawn Mower";
    private static final int SPEED_X = 4;
    private static final int SCREEN_WIDTH = 1000;
    private static final int IMAGE_WIDTH = 82;
    private static final int DAMAGE = 1000;

    private Pair<Integer, Integer> position;
    private boolean isAlive;
    private boolean isRunning;

    public LawnMower(final Pair<Integer, Integer> position) {
        this.position = position;
        this.isAlive = true;
        this.isRunning = false;
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void kill() {
        this.isAlive = false;
        this.isRunning = false;
    }

    @Override
    public String getEntityName() {
        return this.NAME;
    }

    public int getDamage() {
        return this.DAMAGE;
    }

    public int getImageWidth() {
        return IMAGE_WIDTH;
    }

    public void run() {
        isRunning = true;
        position = new Pair<>(position.getX() + SPEED_X, position.getY());
        if (position.getX() >= SCREEN_WIDTH) {
            kill();
        }
    }
}
