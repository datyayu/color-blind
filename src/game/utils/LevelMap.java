package game.utils;

import game.model.IEntity;

import java.util.ArrayList;

/**
 * Created by datyayu on 16.05.16.
 */
public class LevelMap {
    private ArrayList<IEntity> entities;
    private int height;
    private int width;

    public LevelMap(int width, int height) {
        this.width = width;
        this.height = height;

        entities = new ArrayList<IEntity>();
    }

    public void addEntity(IEntity entity) {
        entities.add(entity);
    }

    public ArrayList<IEntity> getEntities() {
        return entities;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
