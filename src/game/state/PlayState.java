package game.state;

import game.main.GameStateTree;
import game.model.Portal;
import game.utils.LevelMap;
import game.main.Main;
import game.model.Player;
import game.model.IEntity;
import game.utils.MapManager;
import game.main.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public abstract class PlayState extends State {
    private final int PLAYER_HEIGHT = 40;
    private final int PLAYER_WIDTH = 30;
    private final int GROUND_HEIGHT = 150;

    public Player player;
    public ArrayList<IEntity> entities;
    public int activeColor;
    public LevelMap map;
    public GameStateTree stateTree;

    private int offsetX;


    @Override
    public void init(GameStateTree stateTree) {
        this.stateTree = stateTree;
        player = new Player(30,
                Main.GAME_HEIGHT - (GROUND_HEIGHT + PLAYER_HEIGHT),
                PLAYER_WIDTH, PLAYER_HEIGHT,
                stateTree.getActiveColor()
        );
        entities = new ArrayList<>();
        offsetX = 0;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(player.getColor());
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);

        entities.forEach(entity -> entity.render(g));
        player.render(g);

        renderHUD(g);

        if (stateTree.isGamePaused()) {
            g.setColor(Resources.COLOR_PAUSE_OVERLAY);
            g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);
            g.drawImage(Resources.pauseMenuImg, 0, 0, null);
        }
    }

    @Override
    public void update(float delta, GameStateTree stateTree) {
        this.stateTree = stateTree;

        if (stateTree.isGamePaused()) return;

        if (!player.isAlive()) {
            stateTree.removeOneLife();
            onPlayerDeath();
            return;
        }

        if (stateTree.isGameOver()) {
            stateTree.resetState();
            transitionToState(new GameOverState());
            return;
        }

        stateTree.setActiveColor(activeColor);
        player.setColor(stateTree.getActiveColor());
        player.update(delta, entities, offsetX);

        offsetX = (Main.GAME_WIDTH / 2) - player.getX() - MapManager.TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, Main.GAME_WIDTH - map.getWidth()*MapManager.TILE_SIZE);


        for (IEntity entity : entities) {
            entity.update(delta, offsetX);

            if (entity.getType() == "Portal") {
                Portal portal = ((Portal) entity);

                if (portal.hasWon()) {
                    onLevelComplete();
                }
            }
        };
    }

    public abstract void onLevelComplete();
    public abstract void onPlayerDeath();

    private void renderHUD(Graphics g) {
        int spacing = 0;
        ArrayList<Color> colors = stateTree.getAllColors();
        Color currentColor = stateTree.getActiveColor();
        int remainingLives = stateTree.getRemainingLives();

        for (Color color : colors) {
            // Render color square.
            g.setColor(color);
            g.fillRect(20 + spacing, 433, 20, 20);

            // Show border on active color.
            if (color == currentColor) {
                g.setColor(Resources.COLOR_WHITE);
                g.drawRect(20 + spacing, 433, 20, 20);
            }

            // 20 per square + 5 spacing.
            spacing += 30;
        }

        for (spacing = 0; spacing < remainingLives*30; spacing += 30) {
            g.drawImage(Resources.heartImg, 30+spacing, 20, null);
        }
    }


    @Override
    public void onKeyPress(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            stateTree.togglePause();
        }

        if (stateTree.isGamePaused()) {
            return;
        }

        switch (key) {
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

            case KeyEvent.VK_LEFT:
                player.moveLeft();
                break;

            case KeyEvent.VK_RIGHT:
                player.moveRight();
                break;

            case KeyEvent.VK_UP:
                player.jump();
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
}
