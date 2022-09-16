package com.example.scolorpicker;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static java.awt.Color.RED;

import javax.swing.JLabel;

/**
 * @author Teodor MAMOLEA
 */
class ImagePanel extends JLabel {

    static final int IMAGE_SIZE = 150;

    private static final int DELTA = 19;

    ImagePanel(final String name, final int hPos, final String actionBtnName) {
        super(name, hPos);

        setToolTipText("Move mouse pointer to `" + actionBtnName + "` button and press it. Drag the pointer and take the pixel color.");
        setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE));
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final int x = (getWidth() - DELTA) >> 1;
        final int y = (getHeight() - DELTA) >> 1;

        final Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(RED);
        g2.drawRect(x, y, DELTA, DELTA);
    }
}
