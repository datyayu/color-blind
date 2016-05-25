package game.model;


import game.animation.FrameAnimation;
import game.main.Resources;
import game.model.base.Enemy;

import java.awt.*;


public class Zombie extends Enemy {
    private FrameAnimation zombieRAnimation, zombieLAnimation;

    public Zombie(int x, int y, int width, int height) {
        super(x, y, width, height);

        zombieRAnimation = FrameAnimation.getAnimationFromSpriteSheet(Resources.zombieRSpriteSheet, .3f, 0, 7, true);
        zombieLAnimation = FrameAnimation.getAnimationFromSpriteSheet(Resources.zombieLSpriteSheet, .3f, 0, 7, true);
    }


    @Override
    public void update(float delta, int offsetX, int offsetY) {
        super.update(delta, offsetX, offsetY);

        zombieRAnimation.update(delta);
        zombieLAnimation.update(delta);
    }

    @Override
    public void render(Graphics g) {
        if (lastDirection < 0) {
            zombieRAnimation.render(g, (int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        } else {
            zombieLAnimation.render(g, (int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        }
    }
}
