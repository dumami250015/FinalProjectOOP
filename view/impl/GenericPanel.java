package view.impl;

import javax.swing.*;
import java.awt.*;

public abstract class GenericPanel extends JPanel {
    private static final long serialVersionUID = 1234500000L;

    private final SwingViewImpl parent;

    private final Image background;

    public GenericPanel(final SwingViewImpl parent, final String backgroundSource) {
        this.parent = parent;
        this.background = new ImageIcon(ClassLoader.getSystemResource(backgroundSource)).getImage();
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setSize(SwingViewImpl.APPLICATION_WIDTH, SwingViewImpl.APPLICATION_HEIGHT);
    }

    public SwingViewImpl getView() {
        return this.parent;
    }

    public Image getBackgroundImage() {
        return new ImageIcon(this.background.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)).getImage();
    }
}
