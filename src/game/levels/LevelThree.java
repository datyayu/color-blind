package game.levels;

import game.main.GameStateTree;
import game.state.GameOverState;
import game.state.MenuState;
import game.state.PlayState;
import game.main.Resources;
import game.utils.MapManager;


public class LevelThree extends PlayState {
    @Override
    public void init(GameStateTree stateTree) {
        stateTree.setColorsInventory(
                Resources.COLOR_GREEN,
                Resources.COLOR_BLUE
        );
        map = MapManager.loadMap("level3");

        super.init(stateTree);

        entities = map.getEntities();
    }

    @Override
    public void onLevelComplete() {
        transitionToState(new LevelX());
    }

    @Override
    public void onPlayerDeath() {
        transitionToState(new LevelThree());
    }
}
