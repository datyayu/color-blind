package game.levels;

import game.main.Resources;
import game.state.MenuState;
import game.state.PlayState;
import game.utils.HUD;
import game.utils.MapManager;


public class LevelTwo extends PlayState {
    @Override
    public void init() {
        super.init();

        hud = new HUD(
                Resources.COLOR_GREEN,
                Resources.COLOR_YELLOW
        );

        map = MapManager.loadMap("level2");
        entities = map.getEntities();
    }

    @Override
    public void onLevelComplete() {
        transitionToState(new MenuState());
    }
}