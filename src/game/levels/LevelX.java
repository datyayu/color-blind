package game.levels;


import game.main.GameStateTree;
import game.main.Resources;
import game.state.GameOverState;
import game.state.HardPlayState;
import game.state.PlayState;
import game.utils.MapManager;

import java.awt.event.KeyEvent;

public class LevelX extends HardPlayState {
    @Override
    public void init(GameStateTree stateTree) {
        map = MapManager.loadMap("level2");

        stateTree.setColorsInventory(
                Resources.COLOR_GREEN,
                Resources.COLOR_BLUE
        );


        super.init(stateTree);
    }

    @Override
    public void onLevelComplete() {
        transitionToState(new GameOverState());
    }

    @Override
    public void onPlayerDeath() {
        transitionToState(new LevelX());
    }
}
