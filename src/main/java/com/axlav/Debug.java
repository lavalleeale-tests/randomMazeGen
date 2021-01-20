package com.axlav;

public class Debug {
    public static void main(String[] agrs) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            boolean[][] maze = backend.genMaze(50, 50);
            backend.genInitialSolution(maze);
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
