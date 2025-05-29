package view.impl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import controller.api.*;
import model.api.Plant;
import model.impl.*;

import java.awt.*;

public class FieldCell extends JButton {
    private static final long serialVersionUID = 1234500005L;

    public static final String CELL_TEXT_INITIALIZER = "";

    private final transient Pair<Integer, Integer> coord;
    private final transient MyController controller;

    private final GamePanel parent;

    private final Color hoverColor = new Color(225, 215, 235);
    private final Border hoverBorder = new LineBorder(hoverColor, 3);

    private boolean hasPlant;
    private GameImpl.PlantType activePlantBrush;
    private Plant plant;
    private boolean canDeleted;

    public FieldCell(final GamePanel parent, final Pair<Integer, Integer> coord, final String text,
                     final MyController controller) {
        super(text);
        this.parent = parent;

        this.setEnabled(false);
        this.coord = coord;

        this.setBounds(coord.getX(), coord.getY(), GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
        this.setOpaque(false);
        this.setBorder(hoverBorder);
        this.setBorderPainted(false);
        this.setFocusPainted(false);

        this.freeCell();
        this.addMouseListener(new FieldCellListener(this));
        this.controller = controller;
        this.canDeleted = false;
        this.plant = null;
    }

    public void setCanDeleted(final boolean canDeleted) {
        this.canDeleted = canDeleted;
    }

    public boolean getCanDeleted() {
        return this.canDeleted;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setActivePlantBrush(GameImpl.PlantType activePlantBrush) {
        this.activePlantBrush = activePlantBrush;
    }

    public GameImpl.PlantType getActivePlantBrush() {
        return activePlantBrush;
    }

    private void freeCell() {
        this.hasPlant = false;
    }

    protected void setPlant(Plant plant) {
        this.hasPlant = true;
        this.plant = plant;
        this.setBorderPainted(false);
        this.parent.userPlantingStatus(false);
        this.parent.hideGrid();
        controller.newPlant(coord, plant);
    }

    protected void removePlant(Plant plant) {
        this.hasPlant = false;
        this.plant = null;
        this.setBorderPainted(false);
        this.parent.hideGrid();
        controller.removeCellPlant(plant);
    }

    protected void cellHover(final boolean isHovered) {
        if (this.hasPlant) {
            this.setBorderPainted(false);
        } else {
            this.setBorderPainted(isHovered);
        }
    }

    public boolean hasPlant() {
        return this.hasPlant;
    }

    public Pair<Integer, Integer> getCoord() {
        return this.coord;
    }
}
