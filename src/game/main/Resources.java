package game.main;

import sun.applet.AppletAudioClip;

import javax.imageio.ImageIO;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;


public class Resources {
    public static Color COLOR_RED, COLOR_ORANGE, COLOR_YELLOW, COLOR_GREEN, COLOR_BLUE, COLOR_PURPLE, COLOR_BLACK, COLOR_WHITE;

    public static Image loadingImg, menuPlayImg, menuExitImg;

    public static BufferedReader loadMap(String filename) {
        try {
            String filePath = System.getProperty("user.dir") + "/src/resources/maps/" + filename + ".map";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            return reader;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void load() {
        loadingImg = loadImage("loading.png");
        menuPlayImg = loadImage("menu_play.png");
        menuExitImg = loadImage("menu_exit.png");

        loadColors();
    }

    private static void loadColors() {
        COLOR_RED = new Color(225, 44, 35);
        COLOR_ORANGE = new Color(198, 105, 2);
        COLOR_YELLOW = new Color(215, 192, 13);
        COLOR_GREEN = new Color(102, 179, 44);
        COLOR_BLUE = new Color(16, 67, 214);
        COLOR_PURPLE = new Color(164, 60, 169);
        COLOR_BLACK = new Color(0,0,0);
        COLOR_WHITE = new Color(255, 255, 255);
    }

    private static AudioClip loadSound(String filename) {
        URL fileURL = Resources.class.getResource("/resources/audio/" + filename);
        return new AppletAudioClip(fileURL);
    }

    private static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        InputStream imageStream = Resources.class.getResourceAsStream("/resources/images/" + filename);

        try{
            img = ImageIO.read(imageStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return img;
    }
}
