package game.main;

import game.animation.Frame;
import game.animation.FrameAnimation;
import sun.applet.AppletAudioClip;

import javax.imageio.ImageIO;
import javafx.scene.media.AudioClip;
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
            gameClearedImg,
            gameOverImg;

    public static Image
        mainMenuPlayImg,
        mainMenuHardModeImg,
        mainMenuSoundOffImg,
        mainMenuSoundOnImg,
        mainMenuExitImg;

    public static Image
        pauseMenuResumeImg,
        pauseMenuSoundOffImg,
        pauseMenuSoundOnImg,
        pauseMenuToMainMenu,
        pauseMenuExitImg;

    public static AudioClip
        jumpSound,
        changeColorSound;

    public static AudioClip
        menuSong,
        gameOverSong,
        level1Song,
        level2Song,
        level3Song;

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
        loadSounds();
        loadSongs();

        heartImg = loadImage("heart.png");
        timeFont = new Font("SansSerif", Font.BOLD, 25);
        gameOverTimeFont = new Font("SansSerif", Font.BOLD, 50);

        setGlobalVolume(.2d);
    }

    private static void loadScreenImages() {
        loadingImg = loadImage("screens/loading.png");
        mainMenuPlayImg = loadImage("screens/MainMenuPlay.png");
        mainMenuHardModeImg = loadImage("screens/MainMenuHardMode.png");
        mainMenuSoundOffImg = loadImage("screens/MainMenuSoundOff.png");
        mainMenuSoundOnImg = loadImage("screens/MainMenuSoundOn.png");
        mainMenuExitImg = loadImage("screens/MainMenuExit.png");
        pauseMenuResumeImg = loadImage("screens/PauseMenuResume.png");
        pauseMenuSoundOffImg = loadImage("screens/PauseMenuSoundOff.png");
        pauseMenuSoundOnImg = loadImage("screens/PauseMenuSoundOn.png");
        pauseMenuToMainMenu = loadImage("screens/PauseMenuToMainMenu.png");
        pauseMenuExitImg = loadImage("screens/PauseMenuExit.png");
        gameOverImg = loadImage("screens/GameOver.png");
        gameClearedImg = loadImage("screens/GameCleared.png");
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

    private static void loadSounds() {
        jumpSound = loadSound("effects/jump.wav");
        changeColorSound = loadSound("effects/colorChange.wav");
    }

    private static void loadSongs() {
        menuSong = loadSound("music/early-morning-may.mp3");
        gameOverSong = loadSound("music/7-times.mp3");
        level1Song = loadSound("music/piano-wire.mp3");
        level2Song = loadSound("music/magnetic-moment.mp3");
        level3Song = loadSound("music/sidetracked.mp3");

        menuSong.setCycleCount(AudioClip.INDEFINITE);
        gameOverSong.setCycleCount(AudioClip.INDEFINITE);
        level1Song.setCycleCount(AudioClip.INDEFINITE);
        level2Song.setCycleCount(AudioClip.INDEFINITE);
        level3Song.setCycleCount(AudioClip.INDEFINITE);
    }

    public static void setGlobalVolume(double volume) {
        jumpSound.setVolume(volume);
        changeColorSound.setVolume(volume);
        menuSong.setVolume(volume*.3);
        gameOverSong.setVolume(volume*.3);
        level1Song.setVolume(volume*.3);
        level2Song.setVolume(volume*.3);
        level3Song.setVolume(volume*.3);
    }

    public static void stopAllSounds() {
        jumpSound.stop();
        changeColorSound.stop();
        menuSong.stop();
        gameOverSong.stop();
        level1Song.stop();
        level2Song.stop();
        level3Song.stop();
    }



    private static AudioClip loadSound(String filename) {
        URL fileURL = Resources.class.getResource("/resources/audio/" + filename);
        return new AudioClip(fileURL.toString());
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
