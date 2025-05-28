package model.impl;

public class Peashooter extends PlantImpl {
    private static final String NAME = "Peashooter";
    private static final int HEALTH = 300;
    public static final int COST = 100;
    private static final int DAMAGE = 20;
    private static final int COOLDOWN = 1500;

    public Peashooter(Pair<Integer, Integer> position) {
        super(DAMAGE, NAME, position, COOLDOWN, HEALTH, COST);
    }
}
