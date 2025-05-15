package view.impl;

import model.impl.Pair;
import view.api.View;
import controller.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    }
}
