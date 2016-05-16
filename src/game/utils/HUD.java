package game.utils;

import game.main.Resources;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class HUD {
    private ArrayList<Color> colors;
    public int activeColor;


    public HUD(Color... initialColors) {
        colors = new ArrayList<Color>(Arrays.asList(initialColors));
        activeColor = 0;
    }

    public void update(int color) {
        if (color > -1 && color < colors.size()) {
            activeColor = color;
        }
    }

    public void render(Graphics g) {
        int spacing = 0;
        Color currentColor = colors.get(activeColor);

        for (Color color : colors) {
            // Render color square.
            g.setColor(color);
            g.fillRect(100 + spacing, 433, 20, 20);

            // Show border on active color.
            if (color == currentColor) {
                g.setColor(Resources.COLOR_WHITE);
                g.drawRect(100 + spacing, 433, 20, 20);
            }

            // 25 per square + 25 spacing.
            spacing += 50;
        }
    }

    public Color getActiveColor() {
        return colors.get(activeColor);
    }
}
