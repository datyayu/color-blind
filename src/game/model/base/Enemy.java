package game.model.base;

import game.animation.FrameAnimation;
import game.main.Resources;
import game.model.base.Entity;
import game.model.base.IObject;
import game.utils.CollisionType;

import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy extends Entity {
    protected int bounce;
    protected Color playerColor;

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height, Resources.COLOR_RED);

        MOVEMENT_STEP = 200;
        bounce = 0;
        moveRight();
    }


    @Override
    public void update(float delta, int offsetX, int offsetY) {
        if (bounce == 1) {
            moveLeft();
            bounce = 0;
        } else if (bounce == -1) {
            moveRight();
            bounce = 0;
        }

        super.update(delta, offsetX, offsetY);
    }

    @Override
    public abstract void render(Graphics g);

    @Override
    public String getType() {
        return "Enemy";
    }


    @Override
    public CollisionType checkForCollisions(Entity entity) {
        if (color != playerColor && entity.getRect().intersects(rect)) {
            return CollisionType.DEATH;
        }

        return CollisionType.NULL;
    }

    @Override
    public void checkForCollisions(ArrayList<IObject> objects) {
        for (IObject object : objects) {
            CollisionType collision = object.checkForCollisions(this);

            switch (collision) {
                case BLOCK_LEFT_SIDE:
                    bounce = 1;
                    break;

                case BLOCK_RIGHT_SIDE:
                    bounce = -1;
                    break;

                default:
                    break;
            }
        }

        super.checkForCollisions(objects);
    }


    public void setPlayerColor(Color playerColor){
        this.playerColor = playerColor;
    }

    public Color getPlayerColor() {
        return playerColor;
    }
}
