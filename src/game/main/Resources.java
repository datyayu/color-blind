package game.main;

import game.animation.Frame;
import game.animation.FrameAnimation;
import sun.applet.AppletAudioClip;

import javax.imageio.ImageIO;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Image;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;


public class Resources {
    public static Color
            COLOR_RED,
            COLOR_ORANGE,
            COLOR_YELLOW,
            COLOR_GREEN,
            COLOR_BLUE,
            COLOR_PURPLE,
            COLOR_BLACK,
            COLOR_WHITE,
            COLOR_PAUSE_OVERLAY;

    public static Image
            loadingImg,
            mainMenuPlayImg,
            mainMenuExitImg,
            mainMenuSoundOffImg,
            mainMenuSoundOnImg,
            pauseMenuImg,
            gameOverImg;

    public static Image heartImg;

    public static Font timeFont, gameOverTimeFont;

    public static FrameAnimation jumpRAnimation, jumpLAnimation, runRAnimation, runLAnimation;
    public static BufferedImage zombieRSpriteSheet, zombieLSpriteSheet;


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
        loadScreenImages();
        loadColors();
        loadAnimations();

        heartImg = loadImage("heart.png");
        timeFont = new Font("SansSerif", Font.BOLD, 25);
        gameOverTimeFont = new Font("SansSerif", Font.BOLD, 50);
    }

    private static void loadScreenImages() {
        loadingImg = loadImage("screens/loading.png");
        mainMenuPlayImg = loadImage("screens/MainMenuPlay.png");
        mainMenuExitImg = loadImage("screens/MainMenuExit.png");
        mainMenuSoundOffImg = loadImage("screens/MainMenuSoundOff.png");
        mainMenuSoundOnImg = loadImage("screens/MainMenuSoundOn.png");
        pauseMenuImg = loadImage("screens/PauseMenu.png");
        gameOverImg = loadImage("screens/GameOver.png");
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
        COLOR_PAUSE_OVERLAY = new Color(34, 34, 34, 100);
    }

    private static void loadAnimations() {
        jumpRAnimation = FrameAnimation.getAnimationFromSpriteSheet(loadImage("animations/jumpR.png"), .4f, 0, 7, false);
        jumpLAnimation = FrameAnimation.getAnimationFromSpriteSheet(loadImage("animations/jumpL.png"), .4f, 0, 7, false);
        runRAnimation = FrameAnimation.getAnimationFromSpriteSheet(loadImage("animations/runR.png"), .3f, 0, 7, true);
        runLAnimation = FrameAnimation.getAnimationFromSpriteSheet(loadImage("animations/runL.png"), .3f, 0, 7, true);

        zombieLSpriteSheet = loadImage("animations/zombieR.png");
        zombieRSpriteSheet = loadImage("animations/zombieL.png");
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
