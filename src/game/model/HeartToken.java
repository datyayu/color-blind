package game.model;

import game.main.Resources;
import game.model.base.Entity;
import game.model.base.IObject;
import game.model.base.IToken;
import game.utils.CollisionType;

import java.awt.*;


public class HeartToken implements IObject, IToken {
    private final Rectangle rect;
    private int x;
    private int y;
    private int width;
    private int height;

    private boolean isAvailable;
    private boolean obtained;


    public HeartToken(int x, int y, int width, int height) {
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

        g.drawImage(Resources.heartImg, (int) rect.getX(), (int) rect.getY(), null);
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
        return "HeartToken";
    }

    @Override
    public CollisionType checkForCollisions(Entity entity) {
        if (!isAvailable) return CollisionType.NULL;

        if (entity.getType() == "Player" && entity.getRect().intersects(rect)) {
            isAvailable = false;
            return CollisionType.ADD_LIFE;
        }

        return CollisionType.NULL;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void setAsObtained() {
        obtained = true;
    }

    @Override
    public boolean wasObtained() {
        return obtained;
    }
}
