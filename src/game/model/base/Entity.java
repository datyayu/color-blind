package game.model.base;

import game.utils.CollisionType;

import java.awt.*;
import java.util.ArrayList;


public abstract class Entity implements IObject {
    protected static final float GRAVITY_ACCEL = 1800;
    protected static final int JUMP_VELOCITY = -800;
    protected static final int MAX_STEP = 10;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int MOVEMENT_STEP = 300;

    protected Rectangle rect;
    protected Color color;

    protected boolean isGrounded;
    protected boolean canJump;
    protected int lastDirection;

    protected int offsetX;
    protected int offsetY;
    protected int velY;
    protected int velX;

    public Entity(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

        this.offsetX = 0;
        this.offsetY = 0;

        this.isGrounded = false;

        rect = new Rectangle();
        updateRects();
    }

    @Override
    public void update(float delta, int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        lastDirection = velX != 0 ? velX : lastDirection;

        // Jump
        if (!isGrounded) {
            velY += GRAVITY_ACCEL * delta;
            canJump = false;
        } else {
            velY = 0;
        }

        float yStep = (velY * delta > MAX_STEP) ? MAX_STEP : velY * delta;
        float xStep = (velX * delta > MAX_STEP) ? MAX_STEP : velX * delta;

        y += yStep;
        x += xStep;

        isGrounded = false;
        updateRects();
    }


    protected void updateRects() {
        rect.setBounds(x + offsetX, y + offsetY, width, height);
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

    public int getVelX() { return velX; }
    public int getVelY() { return velY; }

    public void setColor(Color color) { this.color = color; }
    public Color getColor() {
        return color;
    }

    public Rectangle getRect() {
        return rect;
    }
    public abstract String getType();


    @Override
    public CollisionType checkForCollisions(Entity entity) {
        return CollisionType.NULL;
    }

    public void checkForCollisions(ArrayList<IObject> objects) {
        for (IObject object : objects) {
            CollisionType collision = object.checkForCollisions(this);

            switch (collision) {
                case BLOCK_TOP:
                    velY = 0;
                    isGrounded = true;
                    canJump = true;
                    y = (int) (object.getRect().getY() - offsetY - height + 2);
                    break;

                case BLOCK_BOTTOM:
                    velY = 0;
                    y = (int) (object.getRect().getY() - offsetY + object.getRect().getHeight());
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

                default:
                    break;
            }
        }
    }
}
