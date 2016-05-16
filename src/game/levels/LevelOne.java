package game.levels;

import game.state.PlayState;
import game.main.Resources;
import game.utils.HUD;
import game.utils.MapManager;


public class LevelOne extends PlayState {
    @Override
    public void init() {
        super.init();

        hud = new HUD(
                Resources.COLOR_GREEN,
                Resources.COLOR_BLUE
        );

        map = MapManager.loadMap("level1");
        entities = map.getEntities();
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
