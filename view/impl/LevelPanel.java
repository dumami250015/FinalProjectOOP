package view.impl;

import javax.swing.*;
import java.awt.*;

public class LevelPanel extends GenericPanel {
    private static final long serialVersionUID = 1234500003L;

    private static final int FONT_SIZE = 24;
    private static final int LAYOUT_HGAP = 20;
    private static final int LAYOUT_VGAP = 20;
    private static final String BUTTON_TEXTURE = "images/tombstoneTexture.jpg";
    private static final Dimension MENU_BUTTON_DIMENSION = new Dimension(
            SwingViewImpl.APPLICATION_WIDTH / 6, SwingViewImpl.APPLICATION_HEIGHT / 8);
    private final transient SwingViewImpl parent;

    public LevelPanel(final SwingViewImpl parent, final String backgroundSource) {
        super(parent, backgroundSource);
        this.parent = parent;
        final int levelCount = this.parent.getController().getLevelCount();

        if (levelCount <= 0) {
            throw new IllegalStateException("There no valid levels to load!");
        }

        this.setLayout(new FlowLayout(FlowLayout.CENTER, LAYOUT_HGAP, SwingViewImpl.APPLICATION_HEIGHT / 2 - LAYOUT_VGAP));
        final ImageIcon texture = new ImageIcon(ClassLoader.getSystemResource(BUTTON_TEXTURE));

        for (int i = 0; i < levelCount; i++) {
            final int numberOfTheLevel = i;
            final JButton button = new JButton(String.valueOf(numberOfTheLevel + 1), texture);
            this.setButton(button);
            button.addActionListener(e -> {
                this.parent.getController().chooseLevel(numberOfTheLevel);
                this.parent.setScene(SwingViewImpl.MENU_PANEL_CONSTRAINT);
            });
            this.add(button);
        }
    }

    private void setButton(final JButton button) {
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setPreferredSize(MENU_BUTTON_DIMENSION);
        button.setFont(new Font(this.getFont().getName(), Font.BOLD, FONT_SIZE));
        button.setForeground(Color.BLACK);
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);

        if (g instanceof Graphics2D) {
            final Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.getBackgroundImage(), 0, 0, null);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
