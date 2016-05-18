package game.state;

import game.main.GameStateTree;
import game.main.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class LoadState extends State {
    @Override
    public void init(GameStateTree gst) {
        Resources.load();
    }

    @Override
    public void update(float delta, GameStateTree gst) {
        transitionToState(new MenuState());
    }

    @Override
    public void render(Graphics g) {}

    @Override
    public void onKeyPress(KeyEvent e) {}
    @Override
    public void onKeyRelease(KeyEvent e) {}
}
