package game.state;

import game.main.GameStateTree;
import game.model.*;
import game.utils.LevelMap;
import game.main.Main;
import game.utils.MapManager;
import game.main.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public abstract class PlayState extends State {
    private final int PLAYER_HEIGHT = 80;
    private final int PLAYER_WIDTH = 60;
    private final int GROUND_HEIGHT = 150;

    public Player player;
    public ArrayList<IEntity> entities;
    public int activeColor;
    public LevelMap map;
    public GameStateTree stateTree;

    private int offsetX;
    private int offsetY;

    public abstract void onLevelComplete();
    public abstract void onPlayerDeath();

    @Override
    public void init(GameStateTree stateTree) {
        this.stateTree = stateTree;
        player = new Player(
                MapManager.TILE_SIZE * 2,
                Main.GAME_HEIGHT - (MapManager.TILE_SIZE * 2 + PLAYER_HEIGHT),
                PLAYER_WIDTH,
                PLAYER_HEIGHT,
                stateTree.getActiveColor(),
                map.getHeight()
        );
        entities = new ArrayList<>();
        offsetX = 0;
        offsetY = 0;

        entities = map.getEntities();
    }

    @Override
    public void update(float delta, GameStateTree stateTree) {
        this.stateTree = stateTree;

        if (stateTree.isGamePaused()) return;

        if (!player.isAlive()) {
            stateTree.removeOneLife();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onPlayerDeath();
            return;
        }

        if (stateTree.isGameOver()) {
            transitionToState(new GameOverState());
            return;
        }

        stateTree.addTime(delta);
        stateTree.setActiveColor(activeColor);
        player.setColor(stateTree.getActiveColor());
        player.update(delta, entities, offsetX, offsetY);
        offsetX = updateOffsetX();
        offsetY = updateOffsetY();

        updateEntities(delta);
    }

    private int updateOffsetX() {
        int newOffset = (Main.GAME_WIDTH / 2) - player.getX() - MapManager.TILE_SIZE;
        newOffset = Math.min(newOffset, 0);
        newOffset = Math.max(newOffset, Main.GAME_WIDTH - map.getWidth()*MapManager.TILE_SIZE);

        return newOffset;
    }

    private int updateOffsetY() {
        int newOffset = (Main.GAME_HEIGHT / 2) - player.getY() - MapManager.TILE_SIZE;
        newOffset = Math.min(newOffset, 0);
        newOffset = Math.max(newOffset, Main.GAME_HEIGHT - map.getHeight()*MapManager.TILE_SIZE);

        return newOffset;
    }

    private void updateEntities(float delta) {
        for (IEntity entity : entities) {
            entity.update(delta, offsetX, offsetY);

            checkEdgeCasesOnEntity(entity);
        }
    }

    private void checkEdgeCasesOnEntity(IEntity entity) {
        switch (entity.getType()) {
            case "Portal":
                Portal portal = (Portal) entity;

                if (portal.hasWon()) {
                    onLevelComplete();
                }

                break;

            case "ColorToken":
                ColorToken cToken = (ColorToken) entity;

                if (!cToken.isAvailable() && !cToken.wasObtained()) {
                    cToken.setAsObtained();
                    stateTree.addColor(cToken.getColor());
                }

                break;

            case "HeartToken":
                HeartToken hToken = (HeartToken) entity;

                if (!hToken.isAvailable() && !hToken.wasObtained()) {
                    hToken.setAsObtained();
                    stateTree.addOneLife();
                }

                break;

            default:
                break;
        }
    }


    @Override
    public void render(Graphics g) {
        g.setColor(stateTree.getActiveColor());
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);

        renderEntities(g);

        player.render(g);
        renderHUD(g);

        if (stateTree.isGamePaused()) {
            renderPauseMenu(g);
        }
    }

    private void renderHUD(Graphics g) {
        int spacing = 0;
        ArrayList<Color> colors = stateTree.getAllColors();
        Color currentColor = stateTree.getActiveColor();
        int remainingLives = stateTree.getRemainingLives();

        /* Render color inventory */
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

        /* Render lives */
        for (spacing = 0; spacing < remainingLives*30; spacing += 30) {
            g.drawImage(Resources.heartImg, 30+spacing, 40, null);
        }

        /* Render time */
        g.setColor(Color.WHITE);
        g.setFont(Resources.timeFont);
        g.drawString("TIME: " + stateTree.getTimeString(), 30, 30);
    }

    private void renderEntities(Graphics g) {
        for (IEntity entity : entities) {
            Rectangle entRect = entity.getRect();

            if (entity.getColor() != stateTree.getActiveColor() &&
                    entRect.getX() + entRect.getWidth() > 0 &&
                    entRect.getX() < Main.GAME_WIDTH) {
                entity.render(g);
            }
        }
    }

    private void renderPauseMenu(Graphics g) {
        g.setColor(Resources.COLOR_PAUSE_OVERLAY);
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);
        g.drawImage(Resources.pauseMenuImg, 0, 0, null);
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
            case KeyEvent.VK_NUMPAD1:
                activeColor = 0;
                break;

            case KeyEvent.VK_2:
            case KeyEvent.VK_NUMPAD2:
                activeColor = 1;
                break;

            case KeyEvent.VK_3:
            case KeyEvent.VK_NUMPAD3:
                activeColor = 2;
                break;

            case KeyEvent.VK_4:
            case KeyEvent.VK_NUMPAD4:
                activeColor = 3;
                break;

            case KeyEvent.VK_5:
            case KeyEvent.VK_NUMPAD5:
                activeColor = 4;
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

            case KeyEvent.VK_SPACE:
                activeColor = stateTree.getNextColorIndex();
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
