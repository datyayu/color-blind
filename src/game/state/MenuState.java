package game.state;

import game.levels.LevelOne;
import game.main.Main;
import game.utils.RNG;
import game.main.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class MenuState extends State {
    private int red, green, blue;
    private int red_step, green_step, blue_step;
    private int selectedOption = 0;


    private final int MAX_STEP = 3;
    private final int BOUNDS_PADDING = 20;

    @Override
    public void init() {
        red_step = RNG.getRandomIntBetween(1, MAX_STEP);
        blue_step = RNG.getRandomIntBetween(1, MAX_STEP);
        green_step = RNG.getRandomIntBetween(1, MAX_STEP);

        red = 125;
        green = 145;
        blue = 132;
    }

    @Override
    public void update(float delta) {
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
        g.setColor(new Color(red, green, blue));
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);

        if (selectedOption == 0) {
            g.drawImage(Resources.menuPlayImg, 0, 0, null);
        } else {
            g.drawImage(Resources.menuExitImg, 0, 0, null);
        }
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP) {
            selectedOption = selectedOption == 0 ? 1 : 0;
        } else if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
            if (selectedOption == 0) {
                transitionToState(new LevelOne());
            } else {
                Main.game.exitGame();
            }
        }
    }


    @Override
    public void onKeyRelease(KeyEvent e) {}
    @Override
    public void onClick(MouseEvent e) {}
}
