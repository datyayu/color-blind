package game.state;

import game.levels.LevelOne;
import game.main.GameStateTree;
import game.main.Main;
import game.utils.RNG;
import game.main.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;


public class MenuState extends State {
    private int red, green, blue;
    private int red_step, green_step, blue_step;
    private int selectedOption = 0;
    public GameStateTree stateTree;

    private final int MAX_STEP = 3;
    private final int BOUNDS_PADDING = 20;


    @Override
    public void init(GameStateTree stateTree) {
        this.stateTree = stateTree;

        red_step = RNG.getRandomIntBetween(1, MAX_STEP);
        blue_step = RNG.getRandomIntBetween(1, MAX_STEP);
        green_step = RNG.getRandomIntBetween(1, MAX_STEP);

        red = 125;
        green = 145;
        blue = 132;
    }


    @Override
    public void update(float delta, GameStateTree stateTree) {
        this.stateTree = stateTree;

        updateBGColors();
    }

    private void updateBGColors() {
        if (red > (255 - BOUNDS_PADDING) || red < BOUNDS_PADDING*3) {
            red -= red_step;
            red_step = -RNG.numberOrRandom(red_step, MAX_STEP);
        }
        if (green > (255 - BOUNDS_PADDING) || green < BOUNDS_PADDING*3) {
            green -= green_step;
            green_step = -RNG.numberOrRandom(green_step, MAX_STEP);
        }
        if (blue > (255 - BOUNDS_PADDING) || blue < BOUNDS_PADDING*3) {
            blue -= blue_step;
            blue_step = -RNG.numberOrRandom(blue_step, MAX_STEP);
        }

        red += red_step;
        green += green_step;
        blue += blue_step;
    }


    @Override
    public void render(Graphics g) {
        drawBackground(g);
        drawMenu(g);
    }

    public void drawBackground(Graphics g) {
        g.setColor(new Color(red, green, blue));
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);
    }

    private void drawMenu(Graphics g) {
        if (selectedOption == 0) {
            g.drawImage(Resources.mainMenuPlayImg, 0, 0, null);
        } else if (selectedOption == 1 && stateTree.hasSound()) {
            g.drawImage(Resources.mainMenuSoundOnImg, 0, 0, null);
        } else if (selectedOption == 1) {
            g.drawImage(Resources.mainMenuSoundOffImg, 0, 0, null);
        } else {
            g.drawImage(Resources.mainMenuExitImg, 0, 0, null);
        }
    }


    @Override
    public void onKeyPress(KeyEvent e) {
        int key = e.getKeyCode();

        switch(key) {
            case KeyEvent.VK_DOWN:
                selectedOption = selectedOption == 2 ? 0 : selectedOption + 1;
                break;

            case KeyEvent.VK_UP:
                selectedOption = selectedOption == 0 ? 2 : selectedOption - 1;
                break;

            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                if (selectedOption == 0) {
                    transitionToState(new LevelOne());
                } else if (selectedOption == 1) {
                    stateTree.setHasSound(!stateTree.hasSound());
                } else {
                    Main.game.exitGame();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onKeyRelease(KeyEvent e) {}
}
