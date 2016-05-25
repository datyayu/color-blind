package game.state;


import game.main.GameStateTree;
import game.main.Main;
import game.main.Resources;
import game.state.base.IdleBGState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverState extends IdleBGState {
    private long time;
    private GameStateTree stateTree;

    @Override
    public void init(GameStateTree stateTree) {
        super.init(stateTree);
        this.stateTree = stateTree;
        time = 0;
    }

    @Override
    public void update(float delta, GameStateTree stateTree) {
        super.update(delta, stateTree);

        stateTree.setSong(Resources.gameOverSong);
        time += delta*1000L;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        drawScore(g);
    }

    private void drawScore(Graphics g) {
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
