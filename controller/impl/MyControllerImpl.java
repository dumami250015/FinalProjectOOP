package controller.impl;

import controller.api.*;
import model.api.Game;
import model.api.World;
import model.impl.LevelsManager;
import model.impl.WorldImpl;
import view.api.View;

import java.util.Locale;
import java.util.Optional;

public final class MyControllerImpl implements MyController {
    private static final long PERIOD = 70;
    private static final int LEVEL_COUNT = 5;

    private final World world;
    private final View view;
    private Game game;
    private Optional<Integer> chosenLevel;

    public MyControllerImpl() {
        Locale.setDefault(Locale.ENGLISH);

        world = new WorldImpl();
        world.setLevelsManager(new LevelsManager(LEVEL_COUNT));

        this.view = new SwingViewImpl(this);
        chosenLevel = Optional.empty();
    }

    @Override
}
