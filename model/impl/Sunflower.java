package model.impl;

public class Sunflower extends PlantImpl {
    private static final String NAME = "Sunflower";
    private static final int HEALTH = 250;
    public static final int COST = 50;
    private static final int DAMAGE = 0;
    private static final int COOLDOWN = 10000;

    private long lastTimeProduceSun;

    public Sunflower(final Pair<Integer, Integer> position, long lastTimeProduceSun) {
        super(DAMAGE, NAME, position, COOLDOWN, HEALTH, COST);
        this.lastTimeProduceSun = lastTimeProduceSun;
    }

    public long getLastTimeProduceSun() {
        return lastTimeProduceSun;
    }

    public void setLastTimeProduceSun(final long lastTimeProduceSun) {
        this.lastTimeProduceSun = lastTimeProduceSun;
    }
}
