package game.animation;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Sprite {
    private static int SPRITE_TILE_SIZE = 150;

    public static Image getSpriteFromSpriteSheet(BufferedImage spriteSheet, int spriteId) {
        if (spriteSheet == null) {
            return null;
        }

        int xPos = (spriteId % 4) * SPRITE_TILE_SIZE;
        int yPos = (spriteId / 4) * SPRITE_TILE_SIZE;

        return spriteSheet.getSubimage(xPos, yPos, SPRITE_TILE_SIZE, SPRITE_TILE_SIZE);
    }
}
