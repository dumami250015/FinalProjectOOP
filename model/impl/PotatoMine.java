package model.impl;

public class PotatoMine extends PlantImpl {
    private static final String NAME = "Potato Mine";
    private static final double DAMAGE = 1800;
    private static final int COST = 25;
    private static final double HEALTH = 300;
    private static final long COOLDOWN = 15000;
    private static final long ACTIVATION_TIME = 5000;

    private long plantTime; // time when it was planted

    public PotatoMine(Pair<Integer, Integer> position, long currentTime) {
        super(DAMAGE, NAME, position, COOLDOWN, HEALTH, COST);
        this.plantTime = currentTime;
    }

    public boolean isArmed(long currentTime) {
        return currentTime - plantTime >= ACTIVATION_TIME;
    }

    public boolean shouldExplodeOnContact(long currentTime) {
        return isArmed(currentTime);
    }

    public void explode() {
        // One-time use - set health to 0
        receiveDamage(getRemainingHealth());
    }
}
