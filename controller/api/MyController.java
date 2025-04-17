package controller.api;

import model.api.Entities;
import model.impl.Pair;

import java.util.Optional;
import java.util.Set;

public interface MyController {
    void callMainLoop();

    Set<Entities> getEntities();

    void newPlant(Pair<Integer, Integer> position);

    void increaseSunPoints();

    int getSunScore();

    void chooseLevel(int numberOfTheLevel);

    Optional<Integer> getChosenLevel();

    int getLevelCount();
}
