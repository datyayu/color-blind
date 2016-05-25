package game.state;

import game.main.GameStateTree;
import game.model.*;
import game.utils.LevelMap;
import game.main.Main;
import game.utils.MapManager;
import game.main.Resources;
import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public abstract class PlayState extends State {
    protected final int PLAYER_HEIGHT = 80;
    protected final int PLAYER_WIDTH = 60;
    protected final int GROUND_HEIGHT = 150;

    protected Player player;
    protected ArrayList<IObject> objects;
    protected int activeColor;
    protected LevelMap map;
    protected GameStateTree stateTree;

    protected int offsetX;
    protected int offsetY;

    private int pauseMenuOption;

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
        objects = new ArrayList<>();
        offsetX = 0;
        offsetY = 0;
        pauseMenuOption = 0;

        objects = map.getObjects();
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

        updateOffsetX();
        updateOffsetY();


        player.update(delta, offsetX, offsetY);
        updateObjects(delta);
        player.checkForCollisions(objects);
    }

    private void updateOffsetX() {
        int newOffset = (Main.GAME_WIDTH / 2) - player.getX() - MapManager.TILE_SIZE;
        newOffset = Math.min(newOffset, 0);
        newOffset = Math.max(newOffset, Main.GAME_WIDTH - map.getWidth()*MapManager.TILE_SIZE);

        offsetX = newOffset;
    }

    private void updateOffsetY() {
        int newOffset = (Main.GAME_HEIGHT / 2) - player.getY() - MapManager.TILE_SIZE;
        newOffset = Math.min(newOffset, 0);
        newOffset = Math.max(newOffset, Main.GAME_HEIGHT - map.getHeight()*MapManager.TILE_SIZE);

        offsetY = newOffset;
    }

    private void updateObjects(float delta) {
        for (IObject object : objects) {
            object.update(delta, offsetX, offsetY);

            checkEdgeCasesOnObject(object);
        }
    }

    private void checkEdgeCasesOnObject(IObject object) {
        switch (object.getType()) {
            case "Enemy":
                Enemy enemy = (Enemy) object;
                enemy.setPlayerColor(player.getColor());
                enemy.checkForCollisions(objects);
                break;

            case "Portal":
                Portal portal = (Portal) object;

                if (portal.hasWon()) {
                    onLevelComplete();
                }

                break;

            case "ColorToken":
                IToken cToken = (IToken) object;

                if (!cToken.isAvailable() && !cToken.wasObtained()) {
                    cToken.setAsObtained();
                    stateTree.addColor(cToken.getColor());
                }

                break;

            case "HeartToken":
                IToken hToken = (IToken) object;

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

        renderObjects(g);

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

    public void renderObjects(Graphics g) {
        for (IObject object : objects) {
            Rectangle objRect = object.getRect();

            if (object.getColor() != stateTree.getActiveColor() &&
                    objRect.getX() + objRect.getWidth() > 0 &&
                    objRect.getX() < Main.GAME_WIDTH) {
                object.render(g);
            }
        }
    }

    private void renderPauseMenu(Graphics g) {
        g.setColor(Resources.COLOR_PAUSE_OVERLAY);
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);

        switch(pauseMenuOption) {
            case 0:
                g.drawImage(Resources.pauseMenuResumeImg, 0, 0, null);
                break;

            case 1:
                if (stateTree.hasSound()) {
                    g.drawImage(Resources.pauseMenuSoundOnImg, 0, 0, null);
                } else {
                    g.drawImage(Resources.pauseMenuSoundOffImg, 0, 0, null);
                }
                break;

            case 2:
                g.drawImage(Resources.pauseMenuToMainMenu, 0, 0, null);
                break;

            case 3:
                g.drawImage(Resources.pauseMenuExitImg, 0, 0, null);
                break;

            default:
                break;
        }

    }

    @Override
    public void onKeyPress(KeyEvent e) {
        int key = e.getKeyCode();

        if (stateTree.isGamePaused()) {
            handlePauseKeyPress(key);
            return;
        }

        switch (key) {
            case KeyEvent.VK_ESCAPE:
                stateTree.togglePause();
                break;

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


    protected void handlePauseKeyPress(int key) {
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                stateTree.togglePause();
                pauseMenuOption = 0;
                break;

            case KeyEvent.VK_UP:
                if (pauseMenuOption == 0) {
                    pauseMenuOption = 3;
                } else {
                    pauseMenuOption -= 1;
                }
                break;

            case KeyEvent.VK_DOWN :
                if (pauseMenuOption == 3) {
                    pauseMenuOption = 0;
                } else {
                    pauseMenuOption += 1;
                }
                break;

            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                if (pauseMenuOption == 0) {
                    stateTree.togglePause();
                } else if (pauseMenuOption == 1) {
                    stateTree.setHasSound(!stateTree.hasSound());
                } else if (pauseMenuOption == 2) {
                    stateTree.resumeGame();
                    transitionToState(new MenuState());
                } else if (pauseMenuOption == 3) {
                    Main.game.exitGame();
                }
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
