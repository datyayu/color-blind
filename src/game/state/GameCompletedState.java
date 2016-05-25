package game.state;

import game.main.GameStateTree;
import game.main.Resources;
import game.state.base.IdleBGState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameCompletedState extends IdleBGState {
    private GameStateTree stateTree;
    private long time;

    @Override
    public void init(GameStateTree stateTree) {
        super.init(stateTree);
        this.stateTree = stateTree;
        time = 0;
    }

    @Override
    public void update(float delta, GameStateTree stateTree) {
        super.update(delta, stateTree);
        stateTree.setSong(Resources.menuSong);
        time += delta*1000L;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        drawScore(g);
    }

    private void drawScore(Graphics g) {
        g.drawImage(Resources.gameClearedImg, 0, 0, null);
        g.setColor(Resources.COLOR_BLACK);
        g.setFont(Resources.gameOverTimeFont);
        g.drawString("FINAL TIME", 210, 300);
        g.drawString(stateTree.getTimeString(), 280, 350);
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        if (time > 1000) {
            transitionToState(new MenuState());
            stateTree.resetState();
        }
    }
}
