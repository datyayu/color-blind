package game.utils;

import game.main.Main;
import game.model.Block;
import game.model.IEntity;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class MapManager {
    public static final int TILE_SIZE = Main.GAME_HEIGHT / 20;

    public static ArrayList<IEntity> loadMap(String filename) {
        ArrayList<String> lines = new ArrayList<String>();
        int width = 0;
        int height = 0;

        BufferedReader reader = Resources.loadMap(filename);

        // Read map file.
        while (true) {
            String line = null;
            try {
                line = reader.readLine();

                if (line == null) {
                    reader.close();
                    break;
                }

                // Ignore comment lines.
                if (!line.startsWith("#")) {
                    lines.add(line);
                    width = Math.max(width, line.length()); // Map width
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        // Map height
        height = lines.size();

        return parseTileMap(lines, width, height);
    }

    public static ArrayList<IEntity> parseTileMap(ArrayList<String> lines, int width, int height) {
        ArrayList<IEntity> entities = new ArrayList<IEntity>();

        for (int y = 0; y < height; y++) {
            String line = lines.get(y);

            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);

                switch (ch) {
                    case 'X':
                        entities.add(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_WHITE));
                    case '0':
                    case '|':
                        entities.add(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_BLACK));
                        break;
                    case '1':
                        entities.add(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_PURPLE));
                        break;
                    case '2':
                        entities.add(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_BLUE));
                        break;
                    case '3':
                        entities.add(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_GREEN));
                        break;
                    case '4':
                        entities.add(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_YELLOW));
                        break;
                    case '5':
                        entities.add(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_ORANGE));
                        break;
                    case '6':
                        entities.add(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_RED));
                        break;
                    default:
                        break;
                }
            }
        }

        return entities;
    }
}
