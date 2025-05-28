package model.api;

import model.impl.Pair;

import java.util.Set;

public interface FactoryEntities {
    Entities createEntity();

    public Entities createEntity(Pair<Integer, Integer> position);

    Set<Entities> createEntities(int n);
}
