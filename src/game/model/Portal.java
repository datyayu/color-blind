package game.model;


import game.main.Resources;
import game.utils.CollisionType;

import java.awt.*;

public class Portal implements IEntity {
    private boolean reached;
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle rect;
    private int offsetX;

    public Portal(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        reached = false;
        rect = new Rectangle(x, y, width, height);
    }

    @Override
    public void update(float delta, int offsetX) {
        this.offsetX = offsetX;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Resources.COLOR_WHITE);
        g.fillRect(x + offsetX, y, width, height);
        g.setColor(Resources.COLOR_BLACK);
        g.drawRect(x + offsetX, y, width, height);
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
        return "Portal";
    }

    @Override
    public CollisionType checkForCollisions(Player player) {
        if (player.getRect().intersects(rect.getX() + offsetX, rect.getY(), rect.getWidth(), rect.getHeight())) {
            reached = true;
            return CollisionType.LEVEL_COMPLETE;
        }

        return CollisionType.NULL;
    }

    public boolean hasWon() {
        return reached;
    }
}
