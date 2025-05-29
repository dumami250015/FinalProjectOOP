package model.api;

import model.impl.Pair;
import view.impl.FieldCell;

import java.util.Set;

public interface Game {
    boolean isGameOver();

    void update(long elapsed);

    boolean createPlant(Pair<Integer, Integer> position, Plant plant);

    void deleteCellPlant(Plant plant);

    GameState getGameState();

    Set<Entities> getEntities();
}
