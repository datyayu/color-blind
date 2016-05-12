package game.state;

import game.utils.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class LoadState extends State {
    @Override
    public void init() {
        Resources.load();
    }

    @Override
    public void update(float delta) {
        transitionToState(new MenuState());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Resources.loadingImg, 0, 0, null);
    }

    @Override
    public void onKeyPress(KeyEvent e) {}
    @Override
    public void onKeyRelease(KeyEvent e) {}
    @Override
    public void onClick(MouseEvent e) {}
}
