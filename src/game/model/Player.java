package game.model;


import game.main.Main;
import game.utils.Resources;

import java.awt.*;

public class Player implements IEntity {
    private static final float GRAVITY_ACCEL = 1800;
    private static final int JUMP_VELOCITY = -600;
    private static final int MOVEMENT_STEP = 400;

    private int x;
    private int y;
    private int width;
    private int height;
    private int velX = 0;
    private int velY = 0;

    private Rectangle ground;
    private Rectangle rect;


    public Player(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        rect = new Rectangle();
        ground = new Rectangle(0, Main.GAME_HEIGHT - 51, Main.GAME_WIDTH, 51);

        updateRects();
    }

    @Override
    public void update(float delta) {
        if (!isGrounded()) {
            velY += GRAVITY_ACCEL * delta;
        } else {
            y = Main.GAME_HEIGHT - (height + 50);
            velY = 0;
        }

        y += velY * delta;
        x += velX * delta;
        updateRects();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Resources.COLOR_BLACK);
        g.fillRect((int) ground.getX(), (int) ground.getY(), (int) ground.getWidth(), (int) ground.getHeight());

        g.setColor(Color.WHITE);
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getHeight(), (int) rect.getHeight());
        g.setColor(Color.GREEN);
        g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getHeight(), (int) rect.getHeight());
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
        if (isGrounded()) {
            y -= 10;
            velY = JUMP_VELOCITY;
            updateRects();
        }
    }

    public boolean isGrounded() {
        return rect.intersects(ground);
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
