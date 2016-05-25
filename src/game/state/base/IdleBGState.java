package game.state.base;

import game.main.GameStateTree;
import game.main.Main;
import game.utils.RNG;

import java.awt.*;
import java.awt.event.KeyEvent;

public class IdleBGState extends State {
    private int red, green, blue;
    private int red_step, green_step, blue_step;

    private final int MAX_STEP = 2;
    private final int BOUNDS_PADDING = 20;

    @Override
    public void init(GameStateTree stateTree) {
        red_step = RNG.getRandomIntBetween(1, MAX_STEP);
        blue_step = RNG.getRandomIntBetween(1, MAX_STEP);
        green_step = RNG.getRandomIntBetween(1, MAX_STEP);

        red = 125;
        green = 145;
        blue = 132;
    }


    @Override
    public void update(float delta, GameStateTree stateTree) {
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
    }

    public void drawBackground(Graphics g) {
        g.setColor(new Color(red, green, blue));
        g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);
    }


    @Override
    public void onKeyPress(KeyEvent e) {}
    @Override
    public void onKeyRelease(KeyEvent e) {}
}
