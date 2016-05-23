package game.model;


import game.animation.FrameAnimation;
import game.main.Main;
import game.main.Resources;
import game.utils.CollisionType;
import game.utils.MapManager;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private static final float GRAVITY_ACCEL = 1800;
    private static final int JUMP_VELOCITY = -800;
    private static final int MOVEMENT_STEP = 300;
    private static final int MAX_STEP = 10;

    private int x;
    private int y;
    private int width;
    private int height;
    private int velX = 0;
    private int velY = 0;

    private boolean isGrounded;
    private boolean canJump;
    private boolean isAlive;

    private Rectangle rect;
    private Color color;
    private Color previousColor;
    private int offsetX;
    private int offsetY;
    private int lastDirection;
    private int mapHeight;
    private boolean justSwitched;

    private FrameAnimation jumpRAnimation, jumpLAnimation, runRAnimation, runLAnimation;


    public Player(int x, int y, int width, int height, Color color, int mapHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.offsetX = 0;
        this.offsetY = 0;
        this.lastDirection = 1;
        this.mapHeight = mapHeight* MapManager.TILE_SIZE;

        rect = new Rectangle();
        isAlive = true;
        isGrounded = false;
        canJump = false;

        jumpRAnimation = Resources.jumpRAnimation;
        jumpLAnimation = Resources.jumpLAnimation;
        runRAnimation = Resources.runRAnimation;
        runLAnimation = Resources.runLAnimation;
        updateRects();
    }


    public void update(float delta, ArrayList<IEntity> entities, int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        lastDirection = velX != 0 ? velX : lastDirection;

        if (y > mapHeight) {
            isAlive = false;
            return;
        }

        justSwitched = previousColor == color ? false : true;

        // Jump
        if (!isGrounded) {
            velY += GRAVITY_ACCEL * delta;
        } else {
            velY = 0;
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


        float yStep = (velY * delta > MAX_STEP) ? MAX_STEP : velY * delta;
        float xStep = (velX * delta > MAX_STEP) ? MAX_STEP : velX * delta;

        y += yStep;
        x += xStep;

        isGrounded = false;
        checkCollisions(entities);
        updateRects();

        previousColor = color;
    }


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

          /* Player's hit box, used for debugging */
//        g.setColor(Resources.COLOR_WHITE);
//        g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int )rect.getHeight()) ;
    }


    public void moveLeft() {
        velX = -MOVEMENT_STEP;
    }

    public void moveRight() {
        velX = MOVEMENT_STEP;
    }

    public void stopMovement() {
        velX = 0;
    }

    public void jump() {
        if (isGrounded && canJump) {
            isGrounded = false;
            canJump = false;
            y -= 10;
            velY = JUMP_VELOCITY;
            updateRects();
        }
    }


    private void updateRects() {
        rect.setBounds(x + offsetX, y + offsetY, width, height);
    }

    private void checkCollisions(ArrayList<IEntity> entities) {
        for (IEntity entity : entities) {
            CollisionType collision = entity.checkForCollisions(this);

            if (justSwitched &&
                    collision != CollisionType.BLOCK_TOP &&
                    collision != CollisionType.NULL) {
                isAlive = false;
                return;
            }

            switch (collision) {
                case BLOCK_TOP:
                    isGrounded = true;
                    canJump = true;
                    y = (int) (entity.getRect().getY() - offsetY - height + 1);
                    velY = 0;
                    break;

                case BLOCK_BOTTOM:
                    Rectangle entRect = entity.getRect();
                    velY = 0;
                    y = (int) (entRect.getY() - offsetY + entRect.getHeight());
                    isGrounded = false;
                    break;

                case BLOCK_RIGHT_SIDE:
                    velX = 0;
                    x = x + 3;
                    isGrounded = false;
                    break;

                case BLOCK_LEFT_SIDE:
                    velX = 0;
                    x = x - 3;
                    isGrounded = false;
                    break;

                case DEATH:
                    isAlive = false;

                default:
                    break;
            }
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setColor(Color color) { this.color = color; }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVelY() { return velY; }

    public Rectangle getRect() {
        return rect;
    }
}
