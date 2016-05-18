package game.main;


import javafx.util.converter.TimeStringConverter;

import java.awt.*;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

public class GameStateTree {
    // Sound is allowed
    private boolean sound;
    // Game is over
    private boolean gameOver;
    // Number of lives remaining.
    private int livesRemaining;

    // List of colors available to the player
    private ArrayList<Color> colorsInventory;
    // Currently active color index
    private int activeColor;

    // Game is paused
    private boolean gamePaused;
    // Time in the current run.
    private long time;


    public GameStateTree() {
        sound = true;
        resetState();
    }

    // Reset all game state to default, except for the sound.
    public void resetState() {
        gameOver = false;
        colorsInventory = new ArrayList<>();
        livesRemaining = 3;
        activeColor = 0;
        gamePaused = false;
        time = 0;
    }


    /********************************
     *         Sound methods        *
     ********************************/

    public boolean hasSound() {
        return sound;
    }

    public void setHasSound(boolean hasSound) {
        this.sound = hasSound;
    }


    /********************************
     *   Colors Inventory methods   *
     ********************************/

    public Color getActiveColor() { return colorsInventory.get(activeColor); }

    public ArrayList<Color> getAllColors() {
        return colorsInventory;
    }

    public void setActiveColor(int index) {
        if (index > -1 && index < colorsInventory.size()) {
            activeColor = index;
        }
    }

    public void addColor(Color color) {
        colorsInventory.add(color);
    }

    public void setColorsInventory(Color... colors) {
        colorsInventory = new ArrayList<>(Arrays.asList(colors));
        activeColor = 0;
    }

    public void resetColorInventory() {
        colorsInventory = new ArrayList<>();
    }


    /********************************
     *         Lives methods        *
     ********************************/

    public int getRemainingLives() {
        return livesRemaining;
    }

    public void resetLives() {
        livesRemaining = 3;
    }

    public void addOneLife() {
        livesRemaining += 1;
    }

    public void removeOneLife() {
        livesRemaining -= 1;

        if (livesRemaining == 0) {
            gameOver = true;
        }
    }


    /********************************
     *     Game paused methods      *
     ********************************/

    public boolean isGamePaused() {
        return  gamePaused;
    }

    public void pauseGame() {
        gamePaused = true;
    }

    public void resumeGame() {
        gamePaused = false;
    }

    public void togglePause() {
        gamePaused = !gamePaused;
    }


    /********************************
     *       Game Over Methods      *
     ********************************/

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGameOver() {
        gameOver = false;
    }


    /********************************
     *         Time Methods         *
     ********************************/
    public long getTime() {
        return time;
    }

    public String getTimeString() {
        Time t = new Time(time);

        int minutes = t.getMinutes();
        int seconds = t.getSeconds();

        String minutesStr = minutes < 10 ? "0" + minutes : "" + minutes;
        String secondsStr = seconds < 10 ? "0" + seconds : "" + seconds;


        return minutesStr + ":" + secondsStr;
    }

    public void addTime(float delta) {
        time += delta * 1000L;
    }
}
