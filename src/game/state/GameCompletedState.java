package game.state;

import game.main.GameStateTree;
import game.main.Resources;

import java.awt.*;

public class GameCompletedState extends GameOverState {
    @Override
    public void update(float delta, GameStateTree stateTree) {
        super.update(delta, stateTree);
        stateTree.setSong(Resources.menuSong);
    }

    @Override
    public void render(Graphics g) {
        drawBackground(g);
        g.drawImage(Resources.gameClearedImg, 0, 0, null);
        g.setColor(Resources.COLOR_BLACK);
        g.setFont(Resources.gameOverTimeFont);
        g.drawString("FINAL TIME", 210, 300);
        g.drawString(stateTree.getTimeString(), 280, 350);
    }
}
