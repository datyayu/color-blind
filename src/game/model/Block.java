package game.model;

import game.utils.CollisionType;

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
    public void update(float delta, int offsetX, int offsetY) {
        rect.setBounds(x + offsetX, y + offsetY, width, height);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    @Override
    public Color getColor() {
        return color;
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

        if (!playerRect.intersects(rect)) {
            return CollisionType.NULL;
        }


        // Left hit
        if ((playerRect.getY() + player.getHeight() > rect.getY() + 15) &&
                (playerRect.getX() + player.getWidth() < rect.getX() + (width/2))) {
            return CollisionType.BLOCK_LEFT_SIDE;
        }

        // Right Hit
        if ((playerRect.getY() + player.getHeight() > rect.getY() + 15) &&
                (playerRect.getX() > rect.getX() + (width/2))) {
            return CollisionType.BLOCK_RIGHT_SIDE;
        }

        // Top hit
        if ((playerRect.getY() < rect.getY()) &&
                (playerRect.getY() + playerRect.getHeight() <= rect.getY() + rect.getWidth()/2) &&
                (playerRect.getX() + playerRect.getWidth() > rect.getX()) &&
                (playerRect.getX() < rect.getX() + width)
                ){
            return CollisionType.BLOCK_TOP;
        }

        // Bottom Hit
        return CollisionType.BLOCK_BOTTOM;

    }
}
