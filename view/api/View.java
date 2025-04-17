package view.api;

import java.util.Optional;

import controller.api.*;
import model.impl.Pair;

public interface View {
    void setScene(String scene);

    String getSceneConstraint();

    void update();

    MyController getController();

    void endGame(Optional<Boolean> win);

    Pair<Double, Double> getScale();
}
