package game.model;


import game.animation.FrameAnimation;
import game.main.Resources;
import game.model.base.Enemy;

import java.awt.*;


public class JumpingZombie extends Zombie {
    public JumpingZombie(int x, int y, int width, int height) {
        super(x, y, width, height);

        JUMP_VELOCITY = -400;
    }


    @Override
    public void update(float delta, int offsetX, int offsetY) {
        if (isGrounded) {
            jump();
        }

        super.update(delta, offsetX, offsetY);
    }
}
