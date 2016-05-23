package game.model;

import game.main.Resources;
import game.utils.CollisionType;

import java.awt.*;


public class HeartToken implements IEntity {

    private final Rectangle rect;
    private int x;
    private int y;
    private int width;
    private int height;

    private Color color;
    private boolean isAvailable;
    private boolean obtained;


    public HeartToken(int x, int y, int width, int height, Color color) {
        this.x = x + width/4;
        this.y = y + height/4;
        this.width = width/2;
        this.height = height/2;

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
        g.drawImage(Resources.heartImg, (int) rect.getX(), (int) rect.getY(), null);
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
        return "HeartToken";
    }

    @Override
    public CollisionType checkForCollisions(Player player) {
        if (!isAvailable) return CollisionType.NULL;

        if (player.getRect().intersects(rect)) {
            isAvailable = false;
            return CollisionType.ADD_LIFE;
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
