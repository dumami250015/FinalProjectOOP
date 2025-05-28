package view.impl;

import model.api.Game;
import model.impl.GameImpl;
import model.impl.Peashooter;
import model.impl.Sunflower;
import model.impl.Wallnut;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FieldCellListener implements MouseListener {
    private final FieldCell parent;

    public FieldCellListener(FieldCell parent) {
        this.parent = parent;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {}

    @Override
    public void mousePressed(final MouseEvent e) {}

    @Override
    public void mouseReleased(final MouseEvent e) {
        if (this.parent.hasPlant()) {
            return;
        }
        if (parent.getActivePlantBrush() == GameImpl.PlantType.Sunflower) {
            parent.setPlant(new Sunflower(parent.getCoord(), System.currentTimeMillis()));
        }
        if (parent.getActivePlantBrush() == GameImpl.PlantType.Peashooter) {
            parent.setPlant(new Peashooter(parent.getCoord()));
        }
        if (parent.getActivePlantBrush() == GameImpl.PlantType.Wallnut) {
            parent.setPlant(new Wallnut(parent.getCoord()));
        }
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        hoverHandler(false);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hoverHandler(false);
    }

    private void hoverHandler(final boolean hover) {
        if (this.parent.hasPlant()) {
            return;
        }
        this.parent.cellHover(hover);
    }

}
