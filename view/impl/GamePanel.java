package view.impl;

import model.api.Entities;

import javax.swing.*;
import model.api.*;
import model.impl.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.*;

public final class GamePanel extends GenericPanel {
    private static final long serialVersionUID = 1234500002L;

    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 9;

    private static final int X_OFFSET = 70;
    private static final int Y_OFFSET = 110;
    private static final int X_MARGIN = 10;
    private static final int Y_MARGIN = 15;

    private static final String SUNFLOWER_CARD = "images/sunflowerCard.png";
    private static final String PEASHOOTER_CARD = "images/peashooterCard.png";
    private static final String WALLNUT_CARD = "images/wallnutCard.png";
    private static final String SUN_COUNTER_IMAGE = "images/sunCounter.jpg";
    private static final String PEASHOOTER_IMAGE = "images/peashooter.png";
    private static final String SUNFLOWER_IMAGE = "images/sunflower.png";
    private static final String WALLNUT_IMAGE = "images/wallnut.png";
    private static final String ZOMBIE_IMAGE = "images/zombieEntity.png";
    private static final String BULLET_IMAGE = "images/ProjectilePea.png";
    private static final String SUN_IMAGE = "images/sunEntity.png";
    private static final String WIN_IMAGE = "images/winner.gif";
    private static final String LOSE_IMAGE = "images/loser.gif";

    private static final int FIELD_STARTING_X = 220;
    private static final int FIELD_STARTING_Y = 110;

    private static final int CARD_SUNFLOWER_STARTING_X = 50;
    private static final int CARD_SUNFLOWER_STARTING_Y = 280;
    private static final int CARD_SUNFLOWER_WIDTH = 69;
    private static final int CARD_SUNFLOWER_HEIGHT = 95;

    private static final int CARD_PEASHOOTER_STARTING_X = 50;
    private static final int CARD_PEASHOOTER_STARTING_Y = 190;
    private static final int CARD_PEASHOOTER_WIDTH = 112;
    private static final int CARD_PEASHOOTER_HEIGHT = 70;

    private static final int CARD_WALLNUT_STARTING_X = 50;
    private static final int CARD_WALLNUT_STARTING_Y = 395;
    private static final int CARD_WALLNUT_WIDTH = 69;
    private static final int CARD_WALLNUT_HEIGHT = 95;

    private static final int SUN_COUNTER_STARTING_X = 50;
    private static final int SUN_COUNTER_STARTING_Y = 50;
    private static final int SUN_COUNTER_WIDTH = 104;
    private static final int SUN_COUNTER_HEIGHT = 119;

    private static final int POINTS_STARTING_X = 72;
    private static final int POINTS_STARTING_Y = 130;
    private static final int POINTS_WIDTH = 60;
    private static final int POINTS_HEIGHT = 40;

    private static final int SUN_ENTITY_WIDTH = 55;
    private static final int SUN_ENTITY_HEIGHT = 55;

    private final transient Map<Entities, ImageIcon> entities = new HashMap<>();

    private final transient Set<Pair<ImageIcon, Pair<Integer, Integer>>> images = new HashSet<>();

    private final transient SwingViewImpl parent;

    private transient Pair<Double, Double> scale;

    public static final int CELL_WIDTH = X_OFFSET - X_MARGIN;
    public static final int CELL_HEIGHT = Y_OFFSET - Y_MARGIN;
    private final FieldCell[][] fieldMatrix;

    private final JLabel points;

    private boolean userIsPlanting;

    public GamePanel(final SwingViewImpl parent, final String backgroundSource) {
        super(parent, backgroundSource);
        this.parent = parent;
        this.scale = this.parent.getScale();
        this.fieldMatrix = new FieldCell[ROW_COUNT][COLUMN_COUNT];
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                final int xCoord = FIELD_STARTING_X + X_OFFSET * j;
                final int yCoord = i == 0
                        ? FIELD_STARTING_Y + Y_OFFSET * i
                        : FIELD_STARTING_Y + Y_OFFSET * i + Y_MARGIN / 4;
                this.fieldMatrix[i][j] = new FieldCell(this, new Pair<>(xCoord, yCoord),
                        FieldCell.CELL_TEXT_INITIALIZER, parent.getController());
            }
        }

//        final JButton plantCardButton = new JButton();
//        plantCardButton.setIcon(new ImageIcon(ClassLoader.getSystemResource(PLANT_CARD)));
//        plantCardButton.setBounds(CARD_STARTING_X, CARD_STARTING_Y, CARD_WIDTH, CARD_HEIGHT);
//        plantCardButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                if (parent.getController().getSunScore() >= PlantImpl.PLANT_COST) {
//                    userIsPlanting = !userIsPlanting;
//                    if (userIsPlanting) {
//                        showGrid();
//                    } else {
//                        hideGrid();
//                    }
//                }
//            }
//        });
//        this.add(plantCardButton);

        final JButton peashooterCardButton = new JButton();
        peashooterCardButton.setIcon(new ImageIcon(ClassLoader.getSystemResource(PEASHOOTER_CARD)));
        peashooterCardButton.setBounds(CARD_PEASHOOTER_STARTING_X, CARD_PEASHOOTER_STARTING_Y,
                CARD_PEASHOOTER_WIDTH, CARD_PEASHOOTER_HEIGHT);
        peashooterCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (parent.getController().getSunScore() >= Peashooter.COST) {
                    userIsPlanting = !userIsPlanting;
                    if (userIsPlanting) {
                        for (int i = 0; i < ROW_COUNT; i++) {
                            for (int j = 0; j < COLUMN_COUNT; j++) {
                                fieldMatrix[i][j].setActivePlantBrush(GameImpl.PlantType.Peashooter);
                            }
                        }
                        showGrid();
                    } else {
                        for (int i = 0; i < ROW_COUNT; i++) {
                            for (int j = 0; j < COLUMN_COUNT; j++) {
                                fieldMatrix[i][j].setActivePlantBrush(GameImpl.PlantType.None);
                            }
                        }
                        hideGrid();
                    }
                }
            }
        });
        this.add(peashooterCardButton);

        final JButton sunflowerCardButton = new JButton();
        sunflowerCardButton.setIcon(new ImageIcon(ClassLoader.getSystemResource(SUNFLOWER_CARD)));
        sunflowerCardButton.setBounds(CARD_SUNFLOWER_STARTING_X, CARD_SUNFLOWER_STARTING_Y,
                CARD_SUNFLOWER_WIDTH, CARD_SUNFLOWER_HEIGHT);
        sunflowerCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (parent.getController().getSunScore() >= Sunflower.COST) {
                    userIsPlanting = !userIsPlanting;
                    if (userIsPlanting) {
                        for (int i = 0; i < ROW_COUNT; i++) {
                            for (int j = 0; j < COLUMN_COUNT; j++) {
                                fieldMatrix[i][j].setActivePlantBrush(GameImpl.PlantType.Sunflower);
                            }
                        }
                        showGrid();
                    } else {
                        for (int i = 0; i < ROW_COUNT; i++) {
                            for (int j = 0; j < COLUMN_COUNT; j++) {
                                fieldMatrix[i][j].setActivePlantBrush(GameImpl.PlantType.None);
                            }
                        }
                        hideGrid();
                    }
                }
            }
        });
        this.add(sunflowerCardButton);

        final JButton wallnutCardButton = new JButton();
        wallnutCardButton.setIcon(new ImageIcon(ClassLoader.getSystemResource(WALLNUT_CARD)));
        wallnutCardButton.setBounds(CARD_WALLNUT_STARTING_X, CARD_WALLNUT_STARTING_Y,
                CARD_WALLNUT_WIDTH, CARD_WALLNUT_HEIGHT);
        wallnutCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (parent.getController().getSunScore() >= Wallnut.COST) {
                    userIsPlanting = !userIsPlanting;
                    if (userIsPlanting) {
                        for (int i = 0; i < ROW_COUNT; i++) {
                            for (int j = 0; j < COLUMN_COUNT; j++) {
                                fieldMatrix[i][j].setActivePlantBrush(GameImpl.PlantType.Wallnut);
                            }
                        }
                        showGrid();
                    } else {
                        for (int i = 0; i < ROW_COUNT; i++) {
                            for (int j = 0; j < COLUMN_COUNT; j++) {
                                fieldMatrix[i][j].setActivePlantBrush(GameImpl.PlantType.None);
                            }
                        }
                        hideGrid();
                    }
                }
            }
        });
        this.add(wallnutCardButton);

        this.points = new JLabel("100", SwingConstants.CENTER);
        this.points.setBounds(POINTS_STARTING_X, POINTS_STARTING_Y, POINTS_WIDTH, POINTS_HEIGHT);
        this.points.setFont(new Font("Arial", Font.BOLD, 16));
        this.points.setForeground(Color.BLACK);
        this.add(this.points);

        final JLabel sunCounterImage = new JLabel();
        sunCounterImage.setIcon(new ImageIcon(ClassLoader.getSystemResource(SUN_COUNTER_IMAGE)));
        sunCounterImage.setBounds(SUN_COUNTER_STARTING_X, SUN_COUNTER_STARTING_Y, SUN_COUNTER_WIDTH, SUN_COUNTER_HEIGHT);
        this.add(sunCounterImage);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (userIsPlanting) {
                    return;
                }

                Entities toRemove = null;

                for (final var el: entities.entrySet()) {
                    if (el.getKey() instanceof Sun
                            && e.getX() >= el.getKey().getPosition().getX()
                            && e.getX() <= el.getKey().getPosition().getX() + SUN_ENTITY_WIDTH
                            && e.getY() >= el.getKey().getPosition().getY()
                            && e.getY() <= el.getKey().getPosition().getY() + SUN_ENTITY_HEIGHT) {
                        final Sun sun = (Sun) el.getKey();
                        sun.kill();
                        toRemove = el.getKey();
                        parent.getController().increaseSunPoints();
                        points.setText(String.valueOf(parent.getController().getSunScore()));
                        break;
                    }
                }

                if (toRemove != null) {
                    entities.remove(toRemove);
                }
            }

//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (userIsPlanting) {
//                    return;
//                }
//
//                // 1) Use Iterator so removal is safe
//                Iterator<Map.Entry<Entities, ImageIcon>> it = entities.entrySet().iterator();
//
//                while (it.hasNext()) {
//                    Map.Entry<Entities, ?> el = it.next();
//                    Entities key = el.getKey();
//
//                    if (key instanceof Sun
//                            && e.getX() >= key.getPosition().getX() * scale.getX()
//                            && e.getX() <= (key.getPosition().getX() + SUN_ENTITY_WIDTH) * scale.getX()
//                            && e.getY() >= key.getPosition().getY() * scale.getY()
//                            && e.getY() <= (key.getPosition().getY() + SUN_ENTITY_HEIGHT) * scale.getY()) {
//
//                        // 2) Update model
//                        ((Sun) key).kill();
//                        parent.getController().increaseSunPoints();
//                        points.setText(
//                                String.valueOf(parent.getController().getSunScore())
//                        );
//
//                        // 3) Remove safely via iterator
//                        it.remove();
//
//                        // 4) Immediately break if you only expect one sun per click
//                        break;
//                    }
//                }
//
//                // 5) Tell Swing to redraw without the collected sun(s)
//                repaint();
//            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void showGrid() {
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                this.add(fieldMatrix[i][j]);
            }
        }
    }

    public void hideGrid() {
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                this.remove(fieldMatrix[i][j]);
            }
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);

        if (g instanceof Graphics2D) {
            final Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.getBackgroundImage(), 0, 0, null);
            this.points.setText(String.valueOf(this.parent.getController().getSunScore()));
            this.updateEntities(g2d);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void updateEntities(final Graphics2D g) {
        this.entities.clear();
        this.entities.entrySet().forEach(
                e -> this.images.add(new Pair<>(e.getValue(), e.getKey().getPosition())));
        this.entities.keySet().removeIf(e -> !this.getView().getController().getEntities().contains(e));
        this.getView().getController().getEntities().forEach(entity -> this.createEntity(g, entity));
    }

    private ImageIcon getEntityImage(final Entities entity) {
        final String resource = switch (entity.getEntityName()) {
            case "Peashooter" -> PEASHOOTER_IMAGE;
            case "Sunflower" -> SUNFLOWER_IMAGE;
            case "Wallnut" -> WALLNUT_IMAGE;
            case "Zombie" -> ZOMBIE_IMAGE;
            case "Bullet" -> BULLET_IMAGE;
            case "Sun" -> SUN_IMAGE;
            default -> throw new IllegalArgumentException("Unexpected value: " + entity.getClass().getName());
        };

        return new ImageIcon(ClassLoader.getSystemResource(resource));
    }

    private void createEntity(final Graphics2D g, final Entities entity) {
        if (!this.scale.equals(this.parent.getScale())) {
            this.scale = this.parent.getScale();
            updateMatrix();
        }
        this.entities.put(entity, getEntityImage(entity));
        final ImageIcon original = this.entities.get(entity);
        final Image scaledImage = new ImageIcon(
                original.getImage().getScaledInstance((int) (original.getImage().getWidth(this) * this.scale.getX()),
                        (int) (original.getImage().getHeight(this) * this.scale.getY()), Image.SCALE_SMOOTH)).getImage();
        final double scaledX = entity.getPosition().getX()  * this.scale.getX();
        final double scaledY = entity.getPosition().getY()  * this.scale.getY();
        g.drawImage(scaledImage, (int) (scaledX), (int) (scaledY), this);
    }

    private void updateMatrix() {
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                final int xCoord = FIELD_STARTING_X + X_OFFSET * j;
                final int yCoord = i == 0
                        ? FIELD_STARTING_Y + Y_OFFSET * i
                        : FIELD_STARTING_Y + Y_OFFSET * i + Y_MARGIN / 4;
                this.fieldMatrix[i][j].setLocation((int) (xCoord * this.scale.getX()), (int) (yCoord * this.scale.getY()));
                this.fieldMatrix[i][j].setSize(new Dimension((int) (GamePanel.CELL_WIDTH * this.scale.getX()),
                        (int) (GamePanel.CELL_HEIGHT * this.scale.getY())));
            }
        }
    }

    public void endGame(final boolean win) {
        this.removeAll();

        final URL url = win ? ClassLoader.getSystemResource(WIN_IMAGE) : ClassLoader.getSystemResource(LOSE_IMAGE);
        final int scaledX = (int) (SwingViewImpl.APPLICATION_WIDTH * this.parent.getScale().getX());
        final int scaledY = (int) (SwingViewImpl.APPLICATION_HEIGHT * this.parent.getScale().getY());
        final Icon icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(scaledX, scaledY, Image.SCALE_DEFAULT));
        final JLabel label = new JLabel();
        label.setBounds(0, 0, scaledX, scaledY);
        label.setIcon(icon);

        this.add(label);
        this.repaint();
    }

    public void userPlantingStatus(final boolean isUserPlanting) {
        this.userIsPlanting = isUserPlanting;
    }

    public boolean isUserPlanting() {
        return this.userIsPlanting;
    }
}
