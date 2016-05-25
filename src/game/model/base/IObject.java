package game.model.base;


import game.utils.CollisionType;

import java.awt.*;


public interface IObject {
    public void update(float delta, int offsetX, int offsetY);
    public void render(Graphics g);

    public Color getColor();
    public Rectangle getRect();
    public String getType();

    CollisionType checkForCollisions(Entity entity);
}
