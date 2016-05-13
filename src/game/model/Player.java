package game.model;


import java.awt.*;
import java.util.ArrayList;

public class Player {
    private static final float GRAVITY_ACCEL = 1800;
private static final int JUMP_VELOCITY = -600;
    private static final int MOVEMENT_STEP = 200;

    private int x;
    private int y;
    private int width;
    private int height;
    private int velX = 0;
    private int velY = 0;

    private boolean isGrounded = true;
    private boolean canJump = true;

    private Rectangle ground;
    private Rectangle rect;
    private Color color;


    public Player(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

        rect = new Rectangle();

        updateRects();
    }


    public void update(float delta, ArrayList<IEntity> entities) {
        // Jump
        if (!isGrounded) {
            velY += GRAVITY_ACCEL * delta;
        } else {
            velY = 0;
        }


        y += velY * delta;
        x += velX * delta;

        isGrounded = false;
        checkCollisions(entities);
        updateRects();
    }


    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        g.setColor(Color.GREEN);
        g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    public void setColor(Color color) { this.color = color; }

    public Color getColor() {
        return color;
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

    private void checkCollisions(ArrayList<IEntity> entities) {
        for (IEntity entity : entities) {
            switch (entity.checkForCollisions(this)) {
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
                    break;

                case BLOCK_SIDE:
                    velX = 0;
                    break;

                default:
                    break;
            }
        }
    }

    private void updateRects() {
        rect.setBounds(x, y, width, height);
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

    public Rectangle getGround() {
        return ground;
    }

    public Rectangle getRect() {
        return rect;
    }
}
