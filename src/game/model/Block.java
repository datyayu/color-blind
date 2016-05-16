package game.model;

import game.utils.CollisionType;
import game.utils.Resources;

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
            (playerRect.getY() + playerRect.getHeight() <= y + 10) &&
            (playerRect.getX() + playerRect.getWidth() > x) &&
            (playerRect.getX() < x + width)
        ){
            return CollisionType.BLOCK_TOP;
        }

        // LEFT HIT
        if ((playerRect.getY() + player.getHeight() > y) &&
                (playerRect.getX() + player.getWidth() < x + 7)) {
            return CollisionType.BLOCK_LEFT_SIDE;
        }

        // Right Hit
        if ((playerRect.getY() + player.getHeight() > y) &&
                (playerRect.getX() > x + width - 7)) {
            return CollisionType.BLOCK_RIGHT_SIDE;
        }

        // Bottom Hit
        return CollisionType.BLOCK_BOTTOM;

    }
}
