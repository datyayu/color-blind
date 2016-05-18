package game.model;

import game.main.Resources;
import game.utils.CollisionType;

import java.awt.*;

public class Block implements IEntity {
    private int x;
    private int y;
    private int width;
    private int height;

    private Color color;
    private Rectangle rect;
    private int offsetX;


    public Block(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

        rect = new Rectangle(x, y, width, height);
    }


    @Override
    public void update(float delta, int offsetX) {
        this.offsetX = offsetX;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) rect.getX() + offsetX, (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
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
    public String getType() {
        return "Block";
    }

    @Override
    public CollisionType checkForCollisions(Player player) {
        Rectangle playerRect = player.getRect();

        if (player.getColor() == color) {
            return CollisionType.NULL;
        }

        if (!playerRect.intersects(rect.getX() + offsetX, rect.getY(), rect.getWidth(), rect.getHeight())) {
            return CollisionType.NULL;
        }


        // Left hit
        if ((playerRect.getY() + player.getHeight() > y + 10) &&
                (playerRect.getX() + player.getWidth() < x + offsetX + 10)) {
            return CollisionType.BLOCK_LEFT_SIDE;
        }

        // Right Hit
        if ((playerRect.getY() + player.getHeight() > y + 10) &&
                (playerRect.getX() > x + offsetX + (width/2))) {
            return CollisionType.BLOCK_RIGHT_SIDE;
        }

        // Top hit
        if ((playerRect.getY() < y) &&
                (playerRect.getY() + playerRect.getHeight() <= y + 10) &&
                (playerRect.getX() + playerRect.getWidth() > x + offsetX) &&
                (playerRect.getX() < x + offsetX + width)
                ){
            return CollisionType.BLOCK_TOP;
        }

        // Bottom Hit
        return CollisionType.BLOCK_BOTTOM;

    }
}
