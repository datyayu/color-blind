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

    public static Image loadingImg, mainMenuPlayImg, mainMenuExitImg, mainMenuSoundOffImg, mainMenuSoundOnImg;

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
        mainMenuPlayImg = loadImage("MainMenuPlay.png");
        mainMenuExitImg = loadImage("MainMenuExit.png");
        mainMenuSoundOffImg = loadImage("MainMenuSoundOff.png");
        mainMenuSoundOnImg = loadImage("MainMenuSoundOn.png");

        loadColors();
    }

    private static void loadColors() {
        COLOR_RED = new Color(173, 33, 26);
        COLOR_ORANGE = new Color(168, 89, 2);
        COLOR_YELLOW = new Color(203, 183, 11);
        COLOR_GREEN = new Color(59, 134, 44);
        COLOR_BLUE = new Color(21, 58, 127);
        COLOR_PURPLE = new Color(102, 40, 106);
        COLOR_BLACK = new Color(34, 34, 34);
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
