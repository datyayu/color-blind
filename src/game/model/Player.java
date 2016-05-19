package game.model;


import game.main.Main;
import game.utils.CollisionType;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private static final float GRAVITY_ACCEL = 1800;
    private static final int JUMP_VELOCITY = -600;
    private static final int MOVEMENT_STEP = 200;
    private static final int MAX_STEP = 10;

    private int x;
    private int y;
    private int width;
    private int height;
    private int velX = 0;
    private int velY = 0;

    private boolean isGrounded = true;
    private boolean canJump = true;
    private boolean isAlive = false;

    private Rectangle rect;
    private Color color;
    private Color previousColor;
    private int offsetX;
    private boolean justSwitched;


    public Player(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.offsetX = 0;

        rect = new Rectangle();
        isAlive = true;

        updateRects();
    }


    public void update(float delta, ArrayList<IEntity> entities, int offsetX) {
        this.offsetX = offsetX;

        if (y > Main.GAME_HEIGHT) {
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
        g.setColor(Color.WHITE);
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        g.setColor(Color.GREEN);
        g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
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
        rect.setBounds(x + offsetX, y, width, height);
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
                    y = (int) (entity.getRect().getY() - height + 1);
                    velY = 0;
                    break;

                case BLOCK_BOTTOM:
                    Rectangle entRect = entity.getRect();
                    velY = 0;
                    y = (int) (entRect.getY() + entRect.getHeight());
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
