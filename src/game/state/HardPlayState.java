package game.state;


import game.main.GameStateTree;

import java.awt.event.KeyEvent;


abstract public class HardPlayState extends PlayState {
    private int timer;
    private int nColors;

    public HardPlayState() {
        super();
        timer = 0;
    }
    @Override
    public void init(GameStateTree stateTree) {
        super.init(stateTree);

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
