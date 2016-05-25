package game.model.base;


import java.awt.*;

public interface IToken {
    boolean isAvailable();
    void setAsObtained();
    boolean wasObtained();
    Color getColor();
}
