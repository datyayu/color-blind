package game.levels;

import game.model.HUD;
import game.model.Player;
import game.state.PlayState;
import game.utils.Resources;
import game.utils.MapManager;

import java.awt.*;


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

        entities = MapManager.loadMap("test");
    }
}
