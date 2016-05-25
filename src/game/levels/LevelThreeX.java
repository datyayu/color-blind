package game.levels;

import game.main.GameStateTree;
import game.main.Resources;
import game.state.GameCompletedState;
import game.state.HardPlayState;
import game.state.PlayState;
import game.utils.MapManager;


public class LevelThreeX extends HardPlayState {
    @Override
    public void init(GameStateTree stateTree) {
        map = MapManager.loadMap("level3");

        stateTree.setColorsInventory(
                Resources.COLOR_GREEN,
                Resources.COLOR_BLUE
        );

        super.init(stateTree);
    }


    @Override
    public void update(float delta, GameStateTree stateTree) {
        super.update(delta, stateTree);
        stateTree.setSong(Resources.level3Song);
    }
    @Override
    public void onLevelComplete() {
        transitionToState(new GameCompletedState());
    }

    @Override
    public void onPlayerDeath() {
        transitionToState(new LevelThreeX());
    }
}
