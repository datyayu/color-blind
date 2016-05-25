package game.model;


import game.model.base.Entity;
import game.model.base.IObject;
import game.utils.CollisionType;

import java.awt.*;

public class Spike implements IObject {
    private int x;
    private int y;
    private int width;
    private int height;

    private Color color;
    private Rectangle rect;


    public Spike(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

        rect = new Rectangle();
    }


    @Override
    public void update(float delta, int offsetX, int offsetY) {
        rect.setBounds(x + offsetX, y + offsetY, width, height);
    }

    @Override
    public void render(Graphics g) {
        int[] xPoints = {(int) rect.getX(), (int) rect.getX() + width, (int) rect.getX() + (width/2)};
        int[] yPoints = {(int) rect.getY() + height, (int) rect.getY()+ height, (int) rect.getY()};

        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, 3);
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
        return "Spike";
    }

    @Override
    public CollisionType checkForCollisions(Entity entity) {
        Rectangle playerRect = entity.getRect();

        if (entity.getColor() == color) {
            return CollisionType.NULL;
        }

        if (entity.getType() == "Player" &&
                playerRect.intersects(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight())) {
            return CollisionType.DEATH;
        }

        return CollisionType.NULL;
    }
}
