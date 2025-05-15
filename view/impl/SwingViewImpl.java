package view.impl;

import model.impl.Pair;
import view.api.View;
import controller.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

public final class SwingViewImpl implements View {
    public static final int APPLICATION_WIDTH = 1000;
    public static final int APPLICATION_HEIGHT = 700;
    public static final String APPLICATION_TITLE = "Plants Vs Zombies";
    public static final String MENU_PANEL_CONSTRAINT = "MENU";
    public static final String LEVEL_PANEL_CONSTRAINT = "LEVEL";
    public static final String GAME_PANEL_CONSTRAINT = "GAME";
    private static final String MENU_BACKGROUND = "images/menuBackground.jpeg";
    private static final String LEVEL_BACKGROUND = "images/menuBackground.jpeg";
    private static final String GAME_BACKGROUND = "images/gameBackground.png";
    private static final String GAME_ICON = "images/pvzIcon.png";
    private static final boolean IS_APPLICATION_RESIZABLE = true;

    private final MyController controller;
    private final CardLayout sceneManager = new CardLayout();
    private final JPanel panel;
    private String currentConstraint = "";
    private final JFrame frame;
    private final GamePanel gamePanel;
    private Pair<Double, Double> scale;

    public SwingViewImpl(final MyController controller) {
        this.scale = new Pair<>(1.0, 1.0);
        this.controller = controller;
        this.frame = new JFrame(APPLICATION_TITLE);
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                final int n = JOptionPane.showConfirmDialog(frame, "Do you really want to quit?", "Quitting", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        this.frame.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.frame.setLocationRelativeTo(null);
        this.frame.setMinimumSize(new Dimension(APPLICATION_WIDTH, APPLICATION_HEIGHT));
        this.frame.setResizable(IS_APPLICATION_RESIZABLE);
        this.frame.setIconImage(new ImageIcon(ClassLoader.getSystemResource(GAME_ICON)).getImage());

        final MenuPanel menuPanel = new MenuPanel(this, MENU_BACKGROUND);
        final LevelPanel levelPanel = new LevelPanel(this, LEVEL_BACKGROUND);
        this.gamePanel = new GamePanel(this, GAME_BACKGROUND);

        this.panel = new JPanel(sceneManager);
        this.panel.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.panel.add(menuPanel, MENU_PANEL_CONSTRAINT);
        this.panel.add(levelPanel, LEVEL_PANEL_CONSTRAINT);
        this.panel.add(gamePanel, GAME_PANEL_CONSTRAINT);
        this.panel.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(final ComponentEvent e) {
                scale = new Pair<>(e.getComponent().getWidth() / (double) APPLICATION_WIDTH,
                        e.getComponent().getHeight() / (double) APPLICATION_HEIGHT);
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }

    @Override
    public MyController getController() {
        return this.controller;
    }

    @Override
    public void setScene(final String scene) {
        this.sceneManager.show(this.panel, scene);
        this.currentConstraint = scene;
    }

    @Override
    public String getSceneConstraint() {
        return this.currentConstraint;
    }

    @Override
    public void update() {
        this.panel.repaint();
    }

    @Override
    public void endGame(final Optional<Boolean> win) {
        if (win.isEmpty()) {
            throw new IllegalAccessError("Function not Accessible!");
        } else {
            this.gamePanel.endGame(win.get());
        }
    }

    @Override
    public Pair<Double, Double> getScale() {
        return this.scale;
    }
}
