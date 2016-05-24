package game.utils;

import game.model.IObject;

import java.util.ArrayList;


public class LevelMap {
    private ArrayList<IObject> objects;
    private int height;
    private int width;

    public LevelMap(int width, int height) {
        this.width = width;
        this.height = height;

        objects = new ArrayList<IObject>();
    }

    public void addObject(IObject object) { objects.add(object);
    }

    public ArrayList<IObject> getObjects() {
        return objects;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
