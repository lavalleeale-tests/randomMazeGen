package com.axlav;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int mazex = 40;
        int mazey = 60;
        if (args.length==2) {
            try {
                mazex = Integer.parseInt(args[0]);
                mazey = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Failed trying to parse a non-numeric argument");
            }
        }
        boolean[][] maze = backend.genMaze(mazex, mazey);
        boolean [][] initialSolution = backend.genInitialSolution(maze);
        backend.setPrintableMaze(backend.genPrintableMaze(initialSolution));
        JFrame f = new JFrame();
        f.getContentPane().add(new GUI());
        f.setSize(1080, 720);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setVisible(true);
    }
}
