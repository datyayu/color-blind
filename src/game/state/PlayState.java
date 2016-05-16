package game.state;

import game.levels.LevelMap;
import game.main.Main;
import game.levels.HUD;
import game.model.Player;
import game.model.IEntity;
import game.levels.MapManager;
import game.utils.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public abstract  class PlayState extends State {
    private final int PLAYER_HEIGHT = 40;
    private final int PLAYER_WIDTH = 30;
    private final int GROUND_HEIGHT = 150;

    public Player player;
    public ArrayList<IEntity> entities;
    public HUD hud;
    public int activeColor;
    public LevelMap map;

    private int offsetX;
    private int offsetY;


    @Override
    public void init() {
        player = new Player(30,
                Main.GAME_HEIGHT - (GROUND_HEIGHT + PLAYER_HEIGHT),
                PLAYER_WIDTH, PLAYER_HEIGHT,
                Resources.COLOR_ORANGE);
        entities = new ArrayList<IEntity>();
        offsetX = 0;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(player.getColor());
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);

        entities.forEach(entity -> entity.render(g));
        player.render(g);

        hud.render(g);
    }

    @Override
    public void update(float delta) {
        hud.update(activeColor);
        player.setColor(hud.getActiveColor());
        player.update(delta, entities, offsetX);

        offsetX = (Main.GAME_WIDTH / 2) - player.getX() - MapManager.TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, Main.GAME_WIDTH - map.getWidth()*MapManager.TILE_SIZE);

        entities.forEach(entity -> entity.update(delta, offsetX));
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
                activeColor = 0;
                break;

            case KeyEvent.VK_2:
                activeColor = 1;
                break;

            case KeyEvent.VK_3:
                activeColor = 2;
                break;

            case KeyEvent.VK_4:
                activeColor = 3;
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
