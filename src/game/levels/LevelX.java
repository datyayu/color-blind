package game.levels;


import game.main.GameStateTree;
import game.main.Resources;
import game.state.GameOverState;
import game.state.PlayState;
import game.utils.MapManager;

import java.awt.event.KeyEvent;

public class LevelX extends PlayState {
    private float timer;

    @Override
    public void init(GameStateTree stateTree) {
        stateTree.setColorsInventory(
                Resources.COLOR_GREEN,
                Resources.COLOR_BLUE
        );

        map = MapManager.loadMap("level1");

        super.init(stateTree);

        entities = map.getEntities();
    }

    @Override
    public void update(float delta, GameStateTree stateTree) {
        timer += delta * 1000L;

        if (timer > 2000) {
            timer = 0;
        }

        if (timer > 1000) {
            activeColor = 1;
        } else {
            activeColor = 0;
        }

        super.update(delta, stateTree);
    }

    @Override
    public void onLevelComplete() {
        transitionToState(new GameOverState());
    }

    @Override
    public void onPlayerDeath() {
        transitionToState(new LevelX());
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
            case KeyEvent.VK_LEFT:
                player.moveLeft();
                break;

            case KeyEvent.VK_RIGHT:
                player.moveRight();
                break;

            case KeyEvent.VK_UP:
                player.jump();
                break;

            default:
                break;
        }

    }
}
