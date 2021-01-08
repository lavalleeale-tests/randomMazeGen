package com.axlav;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JPanel implements KeyListener {
    private long wrongTime = System.currentTimeMillis();
    private final JTextPane textPane = new JTextPane();
    public void start() {
        JFrame frame = new JFrame("Maze");
        textPane.setEditable(false);
        boolean[][] maze = data.getMaze();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        textPane.addKeyListener(this);
        frame.getContentPane().add(textPane);
        setWalls();
        updateMaze(textPane, maze, 1, 0);
        frame.pack();
        frame.setVisible(true);
    }
    private void setText(JTextPane textPane, String[] text, boolean hitWall, boolean  finished) {
        try {
            StyledDocument doc = textPane.getStyledDocument();
            doc.remove(0, doc.getLength());
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet red = sc.addAttribute(
                        SimpleAttributeSet.EMPTY,
                        StyleConstants.Foreground, new Color(0xFF0000));
            AttributeSet green = sc.addAttribute(
                    SimpleAttributeSet.EMPTY,
                    StyleConstants.Foreground, new Color(0xFF00FF00, true));
                for (String s : text) {
                    doc.insertString(doc.getLength(), s, doc.getStyle("regular"));
                }
                if (hitWall) {
                    doc.setCharacterAttributes((text[0].length() * data.getPlayerPos()[0]) + data.getPlayerPos()[1] * 2, 1, red, true);
                } else if (finished) {
                    doc.setCharacterAttributes((text[0].length() * data.getPlayerPos()[0]) + data.getPlayerPos()[1] * 2, 1, green, true);
                }
            textPane.setDocument(doc);
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
    private void updateMaze(JTextPane textPane, boolean[][] maze, int playerX, int playerY) {
        boolean hitWall = true;
        boolean finished = false;
        try {
            if (!maze[playerX][playerY]) {
                data.setPlayerPos(new int[]{playerX, playerY});
                hitWall = false;
            }
            char[][] charMaze = backend.genCharMaze(maze, data.getPlayerPos()[0], data.getPlayerPos()[1]);
            String[] printableMaze = backend.genPrintableMaze(charMaze);
            if (playerX == maze.length-2 && playerY == maze[0].length-1) {
                finished = true;
            }
            setText(textPane, printableMaze, hitWall, finished);
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        if (hitWall) {
            wrongTime = System.currentTimeMillis();
        }
    }

    @Override
    public void keyTyped(KeyEvent key) {
        if (wrongTime<System.currentTimeMillis()-500) {
            switch (key.getKeyChar()) {
                case 'w':
                    updateMaze(textPane, data.getMaze(), data.getPlayerPos()[0]-1, data.getPlayerPos()[1]);
                    break;
                case 'a':
                    updateMaze(textPane, data.getMaze(), data.getPlayerPos()[0], data.getPlayerPos()[1]-1);
                    break;
                case 's':
                    updateMaze(textPane, data.getMaze(), data.getPlayerPos()[0]+1, data.getPlayerPos()[1]);
                    break;
                case 'd':
                    updateMaze(textPane, data.getMaze(), data.getPlayerPos()[0], data.getPlayerPos()[1]+1);
                    break;
                default:
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent ignored) {

    }

    @Override
    public void keyReleased(KeyEvent ignored) {

    }
}
