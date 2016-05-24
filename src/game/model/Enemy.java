package game.model;

import game.animation.FrameAnimation;
import game.main.Resources;
import game.utils.CollisionType;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Entity {
    private final int STEP = 1;
    private int currentStep;
    private int bounce;
    private FrameAnimation zombieRAnimation, zombieLAnimation;
    private Color playerColor;

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height, Resources.COLOR_RED);

        zombieRAnimation = FrameAnimation.getAnimationFromSpriteSheet(Resources.zombieRSpriteSheet, .3f, 0, 7, true);
        zombieLAnimation = FrameAnimation.getAnimationFromSpriteSheet(Resources.zombieLSpriteSheet, .3f, 0, 7, true);

        currentStep = 0;
        bounce = 0;
        MOVEMENT_STEP = 100;

        moveLeft();
    }


    @Override
    public void update(float delta, int offsetX, int offsetY) {
//        if (currentStep > 250 || currentStep < -250) {
//            lastDirection = -lastDirection;
//        }
        System.out.println("PRE: \t| LD: " + lastDirection + "\t| bounce" + bounce + "\t| VelX: " + velX);


        zombieRAnimation.update(delta);
        zombieLAnimation.update(delta);

        super.update(delta, offsetX, offsetY);

        if (bounce == -1) {
            moveRight();
            bounce = 0;
        } else if (bounce == 1) {
            moveLeft();
            bounce = 0;
        }

        System.out.println("POST: \t| LD: " + lastDirection + "\t| bounce" + bounce + "\t| VelX: " + velX);
    }

    @Override
    protected void updateRects() {
        rect.setBounds(x + offsetX + currentStep, y + offsetY, width, height);
    }

    @Override
    public void render(Graphics g) {
        if (lastDirection < 0) {
            zombieRAnimation.render(g, (int) rect.getX() - 20, (int) rect.getY(), (int) rect.getWidth() + 20, (int) rect.getHeight() + 20);
        } else {
            zombieLAnimation.render(g, (int) rect.getX() - 20, (int) rect.getY(), (int) rect.getWidth() + 20, (int) rect.getHeight() + 20);
        }
    }

    @Override
    public String getType() {
        return "Enemy";
    }


    @Override
    public CollisionType checkForCollisions(Entity entity) {
        if (color != playerColor && entity.getRect().intersects(rect)) {
            return CollisionType.DEATH;
        }

        return CollisionType.NULL;
    }

    @Override
    public void checkForCollisions(ArrayList<IObject> objects) {
        super.checkForCollisions(objects);

        for (IObject object : objects) {
            CollisionType collision = object.checkForCollisions(this);

            switch (collision) {
                case BLOCK_LEFT_SIDE:
                    bounce = 1;
                    break;

                case BLOCK_RIGHT_SIDE:
                    bounce = -1;
                    break;

                default:
                    break;
            }
        }
    }


    public void setPlayerColor(Color playerColor){
        this.playerColor = playerColor;
    }

    public Color getPlayerColor() {
        return playerColor;
    }
}
