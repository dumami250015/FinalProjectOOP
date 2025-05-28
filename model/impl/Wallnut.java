package model.impl;

public class Wallnut extends PlantImpl {
    private static final String NAME = "Wallnut";
    private static final int HEALTH = 500;
    public static final int COST = 50;
    private static final int DAMAGE = 0;
    private static final int COOLDOWN = 50;

    public Wallnut(Pair<Integer, Integer> position) {
        super(DAMAGE, NAME, position, COOLDOWN, HEALTH, COST);
    }
}
