package model.api;

import model.impl.Pair;

public interface Entities {
    Pair<Integer, Integer> getPosition();

    boolean isAlive();

    String getEntityName();
}
