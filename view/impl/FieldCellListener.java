package view.impl;

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
        this.parent.setPlant();
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        hoverHandler(false);
    }

    private void hoverHandler(final boolean hover) {
        if (this.parent.hasPlant()) {
            return;
        }
        this.parent.cellHover(hover);
    }

}
