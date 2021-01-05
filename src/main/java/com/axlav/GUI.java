package com.axlav;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JPanel {
    public void paint(Graphics g) {
        String wall = backend.getWallString();
        String empty = backend.getEmptyString();
        Font font = new Font("Serif", Font.PLAIN, 16);
        int texty = 0;
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(font);
        String[] printableMaze = backend.getPrintableMaze();

        if (wall!=null&&empty!=null) {
            for (String row : printableMaze) {
                g.drawString(row.replaceAll("X",wall).replaceAll("0",empty), 0, texty += g.getFontMetrics().getHeight());
            }
        } else {
            throw new RuntimeException("Could not find walls and empty that have the same length");
        }

    }
    public static void start(int mazex) {
        JFrame f = new JFrame();
        FontMetrics metrics = f.getFontMetrics(new Font("Serif", Font.PLAIN, 16));
        for (String walls: new String[]{"{}","[]"}) {
            for (int i = 1; i < 10; i++) {
                if (metrics.stringWidth(walls)==metrics.stringWidth(new String(new char[i]).replace("\0", " "))) {
                    backend.setWallString(walls);
                    backend.setEmptyString(new String(new char[i]).replace("\0", " "));
                }
            }
        }
        f.getContentPane().add(new GUI());
        f.setSize(metrics.stringWidth(backend.getPrintableMaze()[0].replaceAll("X",backend.getWallString()).replaceAll("0",backend.getEmptyString())),(mazex+2)*metrics.getHeight());
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setVisible(true);
    }
}
