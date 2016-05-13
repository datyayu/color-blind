package game.state;

import game.main.Main;
import game.model.Block;
import game.model.Player;
import game.state.State;
import game.utils.IEntity;
import game.utils.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.block;


public class PlayState extends State {
    private Player player;
    private ArrayList<IEntity> entities;

    private final int PLAYER_HEIGHT = 50;
    private final int PLAYER_WIDTH = 30;
    private final int GROUND_HEIGHT = 50;


    @Override
    public void init() {
        player = new Player(0, Main.GAME_HEIGHT - (GROUND_HEIGHT + PLAYER_HEIGHT), PLAYER_WIDTH, PLAYER_HEIGHT);
        entities = new ArrayList<IEntity>();


        entities.add(new Block(0, Main.GAME_HEIGHT - 51, Main.GAME_WIDTH, 51, Resources.COLOR_BLACK));
        entities.add(new Block(100, 350, 150, 20, Resources.COLOR_GREEN));
        entities.add(new Block(300, 350, 150, 20, Resources.COLOR_YELLOW));
        entities.add(new Block(500, 350, 150, 20, Resources.COLOR_BLUE));

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

        if (key == KeyEvent.VK_LEFT) {
            player.moveLeft();
        } else if (key == KeyEvent.VK_RIGHT) {
            player.moveRight();
        }

        if (key == KeyEvent.VK_SPACE) {
            player.jump();
        }

        if (key == KeyEvent.VK_1) {
            player.setColor(Resources.COLOR_BLUE);
        } else if (key == KeyEvent.VK_2) {
            player.setColor(Resources.COLOR_GREEN);
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
