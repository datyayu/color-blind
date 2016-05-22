package game.animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame {
    private int spriteId;
    private Image image;
    private double duration;

    public Frame(BufferedImage spriteSheet, double duration, int spriteId) {
        this.image = Sprite.getSpriteFromSpriteSheet(spriteSheet, spriteId);;
        this.duration = duration;
    }

    public Image getImage() {
        return image;
    }

    public double getDuration() {
        return duration;
    }

    public static Frame[] getFramesBatch(BufferedImage spriteSheet, double frameDuration, int firstSprite, int lastSprite) {
        int numberOfSprites = lastSprite - firstSprite;
        Frame[] frames = new Frame[numberOfSprites];

        for (int i = 0; i < numberOfSprites; i++) {
            frames[i] = new Frame(spriteSheet, frameDuration, firstSprite + i);
        }

        return frames;
    }
}
