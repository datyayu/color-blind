package game.state;

import game.main.Main;
import game.model.Player;
import game.model.IEntity;
import game.utils.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public abstract  class PlayState extends State {
    public Player player;
    public ArrayList<IEntity> entities;

    private final int PLAYER_HEIGHT = 50;
    private final int PLAYER_WIDTH = 30;
    private final int GROUND_HEIGHT = 50;

    @Override
    public void init() {
        player = new Player(0, Main.GAME_HEIGHT - (GROUND_HEIGHT + PLAYER_HEIGHT), PLAYER_WIDTH, PLAYER_HEIGHT, Resources.COLOR_ORANGE);
        entities = new ArrayList<IEntity>();
    }

    @Override
    public void update(float delta) {
        player.update(delta, entities);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(player.getColor());
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);

        entities.forEach(entity -> entity.render(g));
        player.render(g);
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                player.moveLeft();
                break;

            case KeyEvent.VK_RIGHT:
                player.moveRight();
                break;

            case KeyEvent.VK_SPACE:
                player.jump();
                break;

            case KeyEvent.VK_1:
                player.setColor(Resources.COLOR_GREEN);
                break;

            case KeyEvent.VK_2:
                player.setColor(Resources.COLOR_YELLOW);
                break;

            case KeyEvent.VK_3:
                player.setColor(Resources.COLOR_BLUE);
                break;

            case KeyEvent.VK_4:
                player.setColor(Resources.COLOR_ORANGE);
                break;

            default:
                break;
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
