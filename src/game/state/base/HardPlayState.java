package game.state.base;


import game.main.GameStateTree;
import game.state.base.PlayState;

import java.awt.event.KeyEvent;


abstract public class HardPlayState extends PlayState {
    private long timer;

    public HardPlayState() {
        super();
        timer = 0;
    }

    @Override
    public void update(float delta, GameStateTree stateTree) {
        timer += delta * 1000L;

        activeColor = (int) ((timer/3000) % stateTree.getNumColors());

        super.update(delta, stateTree);
    }


    @Override
    public void onKeyPress(KeyEvent e) {
        int key = e.getKeyCode();

        if (stateTree.isGamePaused()) {
            handlePauseKeyPress(key);
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

            case KeyEvent.VK_ESCAPE:
                stateTree.togglePause();
                break;

            default:
                break;
        }

    }

}
