package game.state;


import game.main.Main;
import game.main.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverState extends MenuState {
    @Override
    public void onKeyPress(KeyEvent e) {
        transitionToState(new MenuState());
    }

    @Override
    public void render(Graphics g) {
        drawBackground(g);
        g.drawImage(Resources.gameOverImg, 0, 0, null);
    }
}
