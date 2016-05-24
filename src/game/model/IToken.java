package game.model;


import java.awt.*;

public interface IToken {
    boolean isAvailable();
    void setAsObtained();
    boolean wasObtained();
    Color getColor();
}
