package game.levels;

import game.main.Main;
import game.model.Block;
import game.model.HUD;
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

        entities.add(new Block(0, Main.GAME_HEIGHT - 51, Main.GAME_WIDTH, 51, Resources.COLOR_BLACK));
        entities.add(new Block(100, 350, 150, 20, Resources.COLOR_GREEN));
        entities.add(new Block(300, 300, 150, 20, Resources.COLOR_YELLOW));
        entities.add(new Block(500, 250, 150, 20, Resources.COLOR_BLUE));
    }
}
