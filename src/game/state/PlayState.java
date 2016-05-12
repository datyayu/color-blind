package game.state;

import game.main.Main;
import game.model.Player;
import game.state.State;
import game.utils.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class PlayState extends State {
    private Player player;

    private final int PLAYER_HEIGHT = 50;
    private final int PLAYER_WIDTH = 30;
    private final int GROUND_HEIGHT = 50;


    @Override
    public void init() {
        player = new Player(0, Main.GAME_HEIGHT - (GROUND_HEIGHT + PLAYER_HEIGHT), PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    @Override
    public void update(float delta) {
        player.update(delta);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Resources.COLOR_RED);
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);
        player.render(g);
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            player.moveLeft();
        } else if (key == KeyEvent.VK_RIGHT) {
            player.moveRight();
        }

        if (key == KeyEvent.VK_SPACE) {
            player.jump();
        }
    }

    @Override
    public void onKeyRelease(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.stopMovement();
        }
    }

    @Override
    public void onClick(MouseEvent e) {

    }
}
