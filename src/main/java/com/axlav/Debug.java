package com.axlav;

public class Debug {
    public static void main(String[] agrs) {
        long start = System.currentTimeMillis();
        Maze maze = new Maze();
        for (int i = 0; i < 50; i++) {
            maze.generate(50, 50);
            maze.genInitialSolution();
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
