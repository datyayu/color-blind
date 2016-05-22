package game.levels;

import game.main.GameStateTree;
import game.state.PlayState;
import game.main.Resources;
import game.utils.MapManager;


public class LevelTwo extends PlayState {
    @Override
    public void init(GameStateTree stateTree) {
        map = MapManager.loadMap("level2");

        stateTree.setColorsInventory(
                Resources.COLOR_GREEN,
                Resources.COLOR_BLUE,
                Resources.COLOR_RED
        );

        super.init(stateTree);
    }

    @Override
    public void onLevelComplete() {
        transitionToState(new LevelThree());
    }

    @Override
    public void onPlayerDeath() {
        transitionToState(new LevelTwo());
    }
}