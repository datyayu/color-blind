package game.model;

import game.main.Resources;
import game.model.base.Enemy;
import game.model.base.Entity;
import game.model.base.IObject;
import game.utils.CollisionType;

import java.awt.*;

public class Block implements IObject {
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

        g.setColor(Color.white);
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
    public CollisionType checkForCollisions(Entity entity) {
        Rectangle entityRect = entity.getRect();
        Color entityColor = entity.getType() == "Enemy" ? ((Enemy) entity).getPlayerColor() : entity.getColor();

        if (entityColor == color) {
            return CollisionType.NULL;
        }

        if (!entityRect.intersects(rect)) {
            return CollisionType.NULL;
        }

        // Left hit12
        if ((entityRect.getY() + entityRect.getHeight() > rect.getY() + 15) &&
                (entityRect.getX() + entityRect.getWidth() < rect.getX() + (rect.getWidth() * 2/4))) {
            return CollisionType.BLOCK_LEFT_SIDE;
        }

        // Right Hit
        if ((entityRect.getY() + entityRect.getHeight() > rect.getY() + 10) &&
                (entityRect.getX() > rect.getX() + (rect.getWidth() * 2/4))) {
            return CollisionType.BLOCK_RIGHT_SIDE;
        }

        // Top hit
        if ((entityRect.getY() < rect.getY() + 15) &&
                (entityRect.getY() + entityRect.getHeight() <= rect.getY() + rect.getWidth()/2) &&
                (entityRect.getX() + entityRect.getWidth() > rect.getX()) &&
                (entityRect.getX() < rect.getX() + width)
                ){
            return CollisionType.BLOCK_TOP;
        }

        // Bottom Hit
        return CollisionType.BLOCK_BOTTOM;

    }
}
