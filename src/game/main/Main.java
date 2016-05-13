package game.main;

import javax.swing.*;


public class Main {
    public static final String GAME_TITLE = "Color Blind";
    public static final int GAME_WIDTH = 720;
    public static final int GAME_HEIGHT = 480;

    public static JFrame frame;
    public static Game game;

    public static void main(String[] args) {
        frame = new JFrame(GAME_TITLE);
        game = new Game(GAME_WIDTH, GAME_HEIGHT);

        frame.add(game);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBounds(500, 100, GAME_WIDTH, GAME_HEIGHT);
        frame.setVisible(true);
    }
}