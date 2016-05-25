package game.state;


import game.main.GameStateTree;
import game.main.Main;
import game.main.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverState extends MenuState {
    private long time = 0;

    @Override
    public void init(GameStateTree stateTree) {
        super.init(stateTree);
    }

    @Override
    public void update(float delta, GameStateTree stateTree) {
        stateTree.setSong(Resources.gameOverSong);
        super.update(delta, stateTree);
        time += delta*1000L;
    }

    @Override
    public void render(Graphics g) {
        drawBackground(g);
        g.drawImage(Resources.gameOverImg, 0, 0, null);
        g.setColor(Resources.COLOR_BLACK);
        g.setFont(Resources.gameOverTimeFont);
        g.drawString("FINAL TIME", 210, 100);
        g.drawString(stateTree.getTimeString(), 280, 150);
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        if (time > 1000) {
            transitionToState(new MenuState());
            stateTree.resetState();
        }
    }
}
