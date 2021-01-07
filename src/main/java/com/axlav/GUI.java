package com.axlav;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JPanel {
    public void start() {
        JFrame frame = new JFrame("Maze");
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setWalls();
        char[][] charMaze = backend.genCharMaze(data.getInitialSolution(), 1, 0);
        String[] printableMaze = backend.genPrintableMaze(charMaze);
        setText(textPane, printableMaze);
        frame.getContentPane().add(textPane);
        frame.pack();
        frame.setVisible(true);
    }
    private void setText(JTextPane textPane, String[] text) {
        try {
            StyledDocument doc = textPane.getStyledDocument();
            for (String s : text) {
                doc.insertString(doc.getLength(), s, doc.getStyle("regular"));
            }
            textPane.setStyledDocument(doc);
        } catch (BadLocationException ble) {
            System.err.println("Couldn't insert text into text pane.");
        }
    }
    private void setWalls() {
        JTextPane dummyWallsPane = new JTextPane();
        JTextPane dummyEmptyPane = new JTextPane();
        for (String walls: new String[]{"{}","[]"}) {
            data.setWallString(walls);
            for (int i = 1; i < 10; i++) {
                dummyWallsPane.setText(walls);
                dummyEmptyPane.setText(new String(new char[i]).replace("\0", " "));
                if (dummyWallsPane.getPreferredSize().width==dummyEmptyPane.getPreferredSize().width) {
                    data.setEmptyString(new String(new char[i]).replace("\0", " "));
                }
            }
        }
    }
}
