package game.state;

import game.main.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public abstract class State {

    /* Game loop actions */
    public abstract void init();
    public abstract void update(float delta);
    public abstract void render(Graphics g);


    /* Action handlers */
    public abstract void onKeyPress(KeyEvent e);
    public abstract void onKeyRelease(KeyEvent e);
    public abstract void onClick(MouseEvent e);


    /* Utils */
    public void transitionToState(State nextState) {
        Main.game.setCurrentState(nextState);
    }
}
