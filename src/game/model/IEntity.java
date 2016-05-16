package game.model;


import game.utils.CollisionType;

import java.awt.*;


public interface IEntity {
    public void update(float delta, int offsetX);
    public void render(Graphics g);

    public Color getColor();
    public Rectangle getRect();

    CollisionType checkForCollisions(Player player);
}
