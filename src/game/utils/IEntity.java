package game.utils;


import game.model.Player;

import java.awt.*;


public interface IEntity {
    public void update(float delta);
    public void render(Graphics g);

    public Color getColor();
    public Rectangle getRect();

    CollisionType checkForCollisions(Player player);
}
