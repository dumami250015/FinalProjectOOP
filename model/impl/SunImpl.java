package model.impl;

import model.api.Sun;

public final class SunImpl implements Sun {
    private static final String NAME = "Sun";
    private static final int POINTS = 25;
    private static final int SCREEN_BOTTOM = 700;
    private static final int IMAGE_HEIGHT = 150;
    private final int speedYAxis;
    private boolean isAlive;
    private Pair<Integer, Integer> position;

    public SunImpl(final Pair<Integer, Integer> position, final int speedYAxis) {
        this.position = position;
        this.speedYAxis = speedYAxis;
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public String getEntityName() {
        return NAME;
    }

    @Override
    public void kill() {
        isAlive = false;
    }

    @Override
    public int getPoints() {
        return !isAlive && position.getY() != SCREEN_BOTTOM ? POINTS : 0;
    }

    @Override
    public void fallDown() {
        position = new Pair<>(position.getX(), position.getY() + speedYAxis);
        if (position.getY() + IMAGE_HEIGHT >= SCREEN_BOTTOM) {
            kill();
        }
    }
}
