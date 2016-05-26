package game.model;

import game.main.GameStateTree;

import java.awt.*;


public class FlyingZombie extends Zombie {
    private boolean goingUp;
    private int initialY;

    public FlyingZombie(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update(float delta, int offsetX, int offsetY) {
        if (!isAlive) return;

        isGrounded = true;
        super.update(delta, offsetX, offsetY);
    }
}
