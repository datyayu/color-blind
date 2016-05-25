package game.model;


import game.animation.FrameAnimation;
import game.main.Resources;
import game.utils.CollisionType;
import game.utils.MapManager;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {
    private boolean isAlive;

    private Color previousColor;
    private int mapHeight;
    private boolean justSwitched;

    private FrameAnimation jumpRAnimation, jumpLAnimation, runRAnimation, runLAnimation;


    public Player(int x, int y, int width, int height, Color color, int mapHeight) {
        super(x, y, width, height, color);

        this.mapHeight = mapHeight*MapManager.TILE_SIZE;

        isAlive = true;

        jumpRAnimation = Resources.jumpRAnimation;
        jumpLAnimation = Resources.jumpLAnimation;
        runRAnimation = Resources.runRAnimation;
        runLAnimation = Resources.runLAnimation;
    }

    @Override
    public void update(float delta, int offsetX, int offsetY) {
        if (y > mapHeight) {
            isAlive = false;
            return;
        }

        if (!isGrounded) {
            jumpRAnimation.update(delta);
            jumpLAnimation.update(delta);
        } else {
            jumpRAnimation.reset();
            jumpLAnimation.reset();
        }

        if (!isGrounded) {
            runRAnimation.reset();
            runLAnimation.reset();
        } else if (velX < 0) {
            runLAnimation.update(delta);
            runRAnimation.reset();
        } else if (velX > 0){
            runRAnimation.update(delta);
            runLAnimation.reset();
        } else {
            runRAnimation.reset();
            runLAnimation.reset();
        }

        justSwitched = previousColor != color;
        previousColor = color;

        super.update(delta, offsetX, offsetY);
    }

    @Override
    public void jump() {
        if (isGrounded) {
            Resources.jumpSound.stop();
            Resources.jumpSound.play();
        }

        super.jump();
    }

    @Override
    public void render(Graphics g) {
        if (!isGrounded && lastDirection < 0) {
            jumpLAnimation.render(g, (int) rect.getX() - 20, (int) rect.getY(), (int) rect.getWidth() + 40, (int) rect.getHeight() + 5);
        } else if (!isGrounded && lastDirection > 0) {
            jumpRAnimation.render(g, (int) rect.getX() - 20, (int) rect.getY(), (int) rect.getWidth() + 40, (int) rect.getHeight() + 5);
        } else if (lastDirection < 0) {
            runLAnimation.render(g, (int) rect.getX() - 20, (int) rect.getY(), (int) rect.getWidth() + 40, (int) rect.getHeight() + 5);
        } else {
            runRAnimation.render(g, (int) rect.getX() - 20, (int) rect.getY(), (int) rect.getWidth() + 40, (int) rect.getHeight() + 5);
        }
    }

    @Override
    public String getType() {
        return "Player";
    }

    @Override
    public void checkForCollisions(ArrayList<IObject> objects) {
        super.checkForCollisions(objects);

        for (IObject object : objects) {
            CollisionType collision = object.checkForCollisions(this);

            if (justSwitched &&
                    collision != CollisionType.BLOCK_TOP &&
                    collision != CollisionType.NULL) {
                isAlive = false;
                return;
            }

           if (collision == CollisionType.DEATH) {
               isAlive = false;
           }
        }
    }

    public boolean isAlive() {
        return isAlive;
    }
}
