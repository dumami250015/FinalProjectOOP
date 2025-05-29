package model.impl;

import view.impl.FieldCell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shovel extends JButton {
    private static final String SHOVEL_IMAGE = "images/shovel.png";
    private static final String SHOVEL_BACKGROUND_IMAGE = "images/shovel2.png";
    private static final int SHOVEL_STARTING_X = 870;
    private static final int SHOVEL_STARTING_Y = 600;
    private static final int SHOVEL_WIDTH = 56;
    private static final int SHOVEL_HEIGHT = 26;
    private boolean isSelected;

    public Shovel () {
        setIcon(new ImageIcon(ClassLoader.getSystemResource(SHOVEL_IMAGE)));
        setBounds(SHOVEL_STARTING_X, SHOVEL_STARTING_Y, SHOVEL_WIDTH, SHOVEL_HEIGHT);
//        setContentAreaFilled(false);
        setBorderPainted(false);
//        .setFocusPainted(false);
        isSelected = false;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
