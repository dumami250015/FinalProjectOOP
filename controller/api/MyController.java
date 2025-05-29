package controller.api;

import model.api.Entities;
import model.api.Plant;
import model.impl.Pair;
import model.impl.PlantImpl;
import view.impl.FieldCell;

import java.util.Optional;
import java.util.Set;

public interface MyController {
    void callMainLoop();

    Set<Entities> getEntities();

    void newPlant(Pair<Integer, Integer> position, Plant plant);

    void removeCellPlant(Plant plant);

    void increaseSunPoints();

    int getSunScore();

    void chooseLevel(int numberOfTheLevel);

    Optional<Integer> getChosenLevel();

    int getLevelCount();
}
