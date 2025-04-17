package model.api;

import java.util.Set;

public interface FactoryEntities {
    Entities createEntity();

    Set<Entities> createEntities(int n);
}
