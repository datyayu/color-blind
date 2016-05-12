package game.animation;

import java.awt.*;


public class AnimationFrame {
    private Image image;
    private double duration;

    public AnimationFrame(Image image, double duration) {
        this.image = image;
        this.duration = duration;
    }

    public Image getImage() {
        return image;
    }

    public double getDuration() {
        return duration;
    }
}
