package game.model;

import game.main.Resources;
import game.utils.CollisionType;

import java.awt.*;

/**
 * Created by datyayu on 16.05.23.
 */
public class ColorToken implements IObject, IToken {

    private final Rectangle rect;
    private int x;
    private int y;
    private int width;
    private int height;

    private Color color;
    private boolean isAvailable;
    private boolean obtained;


    public ColorToken(int x, int y, int width, int height, Color color) {
        this.x = x + width/4;
        this.y = y + height/4;
        this.width = width/2;
        this.height = height/2;
        this.color = color;

        isAvailable = true;
        rect = new Rectangle();
    }

    @Override
    public void update(float delta, int offsetX, int offsetY) {
        if (!isAvailable) return;

        rect.setBounds(x + offsetX, y + offsetY, width, height);
    }

    @Override
    public void render(Graphics g) {
        if (!isAvailable) return;

        g.setColor(color);
        g.fillOval((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        g.setColor(Resources.COLOR_WHITE);
        g.drawOval((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
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
        return "ColorToken";
    }

    @Override
    public CollisionType checkForCollisions(Entity entity) {
        if (!isAvailable) return CollisionType.NULL;

        if (entity.getType() == "Player" && entity.getRect().intersects(rect)) {
            isAvailable = false;
            return CollisionType.NEW_COLOR;
        }

        return CollisionType.NULL;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAsObtained() {
        obtained = true;
    }

    public boolean wasObtained() {
        return obtained;
    }
}
