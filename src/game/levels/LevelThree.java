package game.levels;

import game.main.GameStateTree;
import game.state.MenuState;
import game.state.PlayState;
import game.main.Resources;
import game.utils.MapManager;


public class LevelThree extends PlayState {
    @Override
    public void init(GameStateTree stateTree) {
        super.init(stateTree);

        stateTree.setColorsInventory(
                Resources.COLOR_GREEN,
                Resources.COLOR_BLUE
        );

        map = MapManager.loadMap("level3");
        entities = map.getEntities();
    }

    @Override
    public void onLevelComplete() {
        transitionToState(new MenuState());
    }

    @Override
    public void onPlayerDeath() {
        transitionToState(new LevelThree());
    }
}
