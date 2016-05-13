package game.model;

import game.utils.CollisionType;
import game.utils.IEntity;

import java.awt.*;

public class Block implements IEntity {
    private int x;
    private int y;
    private int width;
    private int height;

    private Color color;
    private Rectangle rect;


    public Block(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

        rect = new Rectangle(x, y, width, height);
    }


    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return rect;
    }

    @Override
    public CollisionType checkForCollisions(Player player) {
        Rectangle playerRect = player.getRect();

        if (player.getColor() == color) {
            return CollisionType.NULL;
        }

        if (!playerRect.intersects(rect)) {
            return CollisionType.NULL;
        }

        int playerBottomY = (int) (playerRect.getY() + playerRect.getHeight());

        if ((playerRect.getY() < y) &&
            ((playerRect.getY() + playerRect.getHeight()) >= y) &&
            (playerRect.getX() + playerRect.getWidth() >= x) &&
            (playerRect.getX() < x + width)
        ){
            return CollisionType.BLOCK_TOP;
        }

        // Side Hit
        if ((playerRect.getY() < y+ 1) &&
            (playerRect.getX() >= x || playerRect.getX() > x + width)) {
            return CollisionType.BLOCK_SIDE;
        }

        // Bottom Hit
        return CollisionType.BLOCK_BOTTOM;

    }
}
