package game.levels;

import game.state.PlayState;
import game.utils.Resources;


public class LevelOne extends PlayState {
    @Override
    public void init() {
        super.init();

        hud = new HUD(
                Resources.COLOR_GREEN,
                Resources.COLOR_YELLOW,
                Resources.COLOR_BLUE,
                Resources.COLOR_ORANGE
        );

        map = MapManager.loadMap("test");
        entities = map.getEntities();
    }
}
