package game.model;


import game.utils.CollisionType;

import java.awt.*;


public interface IEntity {
    public void update(float delta, int offsetX);
    public void render(Graphics g);

    public Color getColor();
    public Rectangle getRect();
    public String getType();

    CollisionType checkForCollisions(Player player);
}
