package game.utils;

import game.state.base.State;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class InputHandler implements KeyListener {
    private State currentState;

    public void setCurrentState(State newState) {
        currentState = newState;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentState.onKeyPress(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentState.onKeyRelease(e);
    }

    /* Unused methods */
    @Override
    public void keyTyped(KeyEvent e) {}
}
