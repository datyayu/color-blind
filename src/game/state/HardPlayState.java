package game.state;


import game.main.GameStateTree;
import game.main.Main;
import game.model.IEntity;

import java.awt.*;
import java.awt.event.KeyEvent;


abstract public class HardPlayState extends PlayState {
    private long timer;

    public HardPlayState() {
        super();
        timer = 0;
    }
    @Override
    public void init(GameStateTree stateTree) {
        super.init(stateTree);
        entities = map.getEntities();
    }

    @Override
    public void update(float delta, GameStateTree stateTree) {
        timer += delta * 1000L;

        activeColor = (int) ((timer/3000) % stateTree.getNumColors());

        super.update(delta, stateTree);
    }

    @Override
    public void renderEntities(Graphics g) {
        for (IEntity entity : entities) {
            Rectangle entRect = entity.getRect();

            if (entity.getColor() != stateTree.getActiveColor() &&
                    entRect.getX() + entRect.getWidth() > 0 &&
                    entRect.getX() < Main.GAME_WIDTH
                    ) {
                entity.render(g);
            }
        }
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
