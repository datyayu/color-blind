package game.model;


import game.model.Player;
import game.utils.CollisionType;

import java.awt.*;


public interface IEntity {
    public void update(float delta);
    public void render(Graphics g);

    public Color getColor();
    public Rectangle getRect();

    CollisionType checkForCollisions(Player player);
}
