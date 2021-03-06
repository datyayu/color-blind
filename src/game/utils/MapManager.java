package game.utils;

import game.main.Main;
import game.model.*;
import game.main.Resources;
import game.model.ColorToken;
import game.model.base.Enemy;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class MapManager {
    public static final int TILE_SIZE = (Main.GAME_HEIGHT * 2 / 20);

    public static LevelMap loadMap(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        int width = 0;
        int height = 0;

        BufferedReader reader = Resources.loadMap(filename);

        // Read map file.
        while (true) {
            String line;

            try {
                line = reader.readLine();

                if (line == null) {
                    reader.close();
                    break;
                }

                // Ignore comment lines.
                if (!line.startsWith("#")) {
                    lines.add(line);
                    width = Math.max(width, line.length()); // LevelMap width
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        // LevelMap height
        height = lines.size();

        return parseTileMap(lines, width, height);
    }

    public static LevelMap parseTileMap(ArrayList<String> lines, int width, int height) {
        LevelMap map = new LevelMap(width, height);

        for (int y = 0; y < height; y++) {
            String line = lines.get(y);

            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);

                switch (ch) {
                    case 'X':
                        map.addObject(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_WHITE));
                    case '|':
                    case '0':
                        map.addObject(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_BLACK));
                        break;
                    case '1':
                        map.addObject(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_GREEN));
                        break;
                    case '2':
                        map.addObject(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_BLUE));
                        break;
                    case '3':
                        map.addObject(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_RED));
                        break;
                    case '4':
                        map.addObject(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_ORANGE));
                        break;
                    case '5':
                        map.addObject(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_YELLOW));
                        break;
                    case '6':
                        map.addObject(new Block(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_PURPLE));
                        break;
                    case 'W':
                        map.addObject(
                                new Portal(
                                        x*TILE_SIZE + (TILE_SIZE / 4),
                                        y*TILE_SIZE + (TILE_SIZE / 4),
                                        TILE_SIZE / 2,
                                        TILE_SIZE / 2
                                )
                        );
                        break;
                    case 'A':
                        map.addObject(new Spike(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_RED));
                        break;
                    case 'V':
                        map.addObject(new Spike(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_BLUE));
                        break;

                    /* COLORS */
                    case 'R':
                        map.addObject(new ColorToken(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, Resources.COLOR_RED));
                        break;

                    case 'H':
                        map.addObject(new HeartToken(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE));
                        break;

                    case 'Z':
                        map.addObject(new Zombie(x*TILE_SIZE, y*TILE_SIZE - TILE_SIZE, 60, 80));
                        break;

                    case 'J':
                        map.addObject(new JumpingZombie(x*TILE_SIZE, y*TILE_SIZE - TILE_SIZE, 60, 80));
                        break;

                    case 'F':
                        map.addObject(new FlyingZombie(x*TILE_SIZE, y*TILE_SIZE - TILE_SIZE, 60, 80));
                        break;

                    default:
                        break;
                }
            }
        }

        return map;
    }
}
