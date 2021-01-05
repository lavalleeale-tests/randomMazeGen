package com.axlav;

import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = new Font("Serif", Font.PLAIN, 16);
        g2.setFont(font);
        int texty = 10;
        String[] printableMaze = backend.getPrintableMaze();
        for (String row : printableMaze) {
            g.drawString(row.replaceAll("X","{}").replaceAll("0","    "), 10, texty += g.getFontMetrics().getHeight());
        }
        g.drawRect(10,10, g.getFontMetrics().stringWidth(printableMaze[0].replaceAll("X","{}").replaceAll("0","    ")), texty);
    }
}
