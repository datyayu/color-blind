package game.state;

import game.levels.LevelOne;
import game.levels.LevelOneX;
import game.main.GameStateTree;
import game.main.Main;
import game.state.base.IdleBGState;
import game.state.base.State;
import game.utils.RNG;
import game.main.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;


public class MenuState extends IdleBGState {
    private int selectedOption;
    public GameStateTree stateTree;


    @Override
    public void init(GameStateTree stateTree) {
        super.init(stateTree);
        this.stateTree = stateTree;
        selectedOption = 0;
    }


    @Override
    public void update(float delta, GameStateTree stateTree) {
        super.update(delta, stateTree);

        this.stateTree = stateTree;
        stateTree.setSong(Resources.menuSong);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        drawMenu(g);
    }

    private void drawMenu(Graphics g) {
        if (selectedOption == 0) {
            g.drawImage(Resources.mainMenuPlayImg, 0, 0, null);
        } else if (selectedOption == 1) {
            g.drawImage(Resources.mainMenuHardModeImg, 0, 0, null);
        } else if (selectedOption == 2 && stateTree.hasSound()) {
            g.drawImage(Resources.mainMenuSoundOnImg, 0, 0, null);
        } else if (selectedOption == 2) {
            g.drawImage(Resources.mainMenuSoundOffImg, 0, 0, null);
        } else {
            g.drawImage(Resources.mainMenuExitImg, 0, 0, null);
        }
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        int key = e.getKeyCode();

        switch(key) {
            case KeyEvent.VK_DOWN:
                selectedOption = selectedOption == 3 ? 0 : selectedOption + 1;
                break;

            case KeyEvent.VK_UP:
                selectedOption = selectedOption == 0 ? 3 : selectedOption - 1;
                break;

            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                if (selectedOption == 0) {
                    transitionToState(new LevelOne());
                } else if (selectedOption == 1) {
                    transitionToState(new LevelOneX());
                } else if (selectedOption == 2) {
                    stateTree.setHasSound(!stateTree.hasSound());
                } else {
                    Main.game.exitGame();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onKeyRelease(KeyEvent e) {}
}
