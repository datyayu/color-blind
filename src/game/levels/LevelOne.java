package game.levels;

import game.main.GameStateTree;
import game.state.PlayState;
import game.main.Resources;
import game.utils.MapManager;


public class LevelOne extends PlayState {
    @Override
    public void init(GameStateTree stateTree) {
        map = MapManager.loadMap("level1");

        stateTree.setColorsInventory(
                Resources.COLOR_GREEN,
                Resources.COLOR_BLUE
        );

        super.init(stateTree);
    }

    @Override
    public void onLevelComplete() {
        transitionToState(new LevelTwo());
    }

    @Override
    public void onPlayerDeath() {
        transitionToState(new LevelOne());
    }
}
