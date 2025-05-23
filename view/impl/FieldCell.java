package view.impl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import controller.api.*;
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
    }

    private void freeCell() {
        this.hasPlant = false;
    }

    protected void setPlant() {
        this.hasPlant = true;
        this.setBorderPainted(false);
        this.parent.userPlantingStatus(false);
        this.parent.hideGrid();
        controller.newPlant(coord);
    }

    protected void cellHover(final boolean isHovered) {
        if (this.hasPlant) {
            this.setBorderPainted(false);
        } else {
            this.setBorderPainted(isHovered);
        }
    }

    protected boolean hasPlant() {
        return this.hasPlant;
    }

    public Pair<Integer, Integer> getCoord() {
        return this.coord;
    }
}
