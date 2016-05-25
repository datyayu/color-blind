package game.model;


import game.main.Resources;
import game.model.base.Entity;
import game.model.base.IObject;
import game.utils.CollisionType;

import java.awt.*;

public class Portal implements IObject {
    private boolean reached;
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle rect;

    public Portal(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        reached = false;
        rect = new Rectangle(x, y, width, height);
    }

    @Override
    public void update(float delta, int offsetX, int offsetY) {
        rect.setBounds(x + offsetX, y + offsetY, width, height);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Resources.COLOR_WHITE);
        g.fillRect((int) rect.getX(), (int) rect.getY(), width, height);
        g.setColor(Resources.COLOR_BLACK);
        g.drawRect((int) rect.getX(), (int) rect.getY(), width, height);
    }

    @Override
    public Color getColor() {
        return Resources.COLOR_WHITE;
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
    public CollisionType checkForCollisions(Entity entity) {
       if (entity.getType() == "Player" && entity.getRect().intersects(rect)) {
            reached = true;
            return CollisionType.LEVEL_COMPLETE;
        }

        return CollisionType.NULL;
    }

    public boolean hasWon() {
        return reached;
    }
}
