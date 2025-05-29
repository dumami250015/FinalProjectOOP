package model.impl;

import view.impl.FieldCell;

public class Shovel {
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void removePlant(FieldCell cell) {
        if (!cell.hasPlant()) {
            return;
        }

    }
}
