package game.animation;

import java.awt.*;
import java.awt.image.BufferedImage;


public class FrameAnimation {
    private boolean animationShouldRepeat;
    private Frame[] frames;
    private double[] frameEndTimes;
    private int currentFrameIndex = 0;

    private double totalDuration = 0;
    private double currentTime = 0;

    public FrameAnimation(boolean repeat, Frame... frames) {
        this.frames = frames;
        animationShouldRepeat = repeat;
        frameEndTimes = new double[frames.length];

        for (int idx = 0; idx < frames.length; idx++) {
            Frame frame = frames[idx];
            totalDuration += frame.getDuration();
            frameEndTimes[idx] = totalDuration;
        }
    }

    public static FrameAnimation getAnimationFromSpriteSheet(
            BufferedImage spriteSheet, double totalDuration, int startFrame, int endFrame, boolean repeat) {
        int numberOfSprites = endFrame - startFrame;

        Frame[] animationFrames = Frame.getFramesBatch(spriteSheet, totalDuration / numberOfSprites, startFrame, endFrame);
        return new FrameAnimation(repeat, animationFrames);
    }

    public synchronized void update(float increment) {
        currentTime += increment;

        if (currentTime > totalDuration && !animationShouldRepeat) {
            currentFrameIndex = 0;
        } else if (currentTime > totalDuration) {
            wrapAnimation();
        }

        while (currentTime > frameEndTimes[currentFrameIndex]) {
            if (currentTime > totalDuration && !animationShouldRepeat) break;

            currentFrameIndex++;
        }
    }

    public synchronized void reset() {
        currentFrameIndex = 0;
        currentTime = 0;
    };

    private synchronized void wrapAnimation() {
        currentFrameIndex = 0;
        currentTime %= totalDuration;
    }

    public synchronized void render(Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(frames[currentFrameIndex].getImage(), x, y, null);
    }

    public synchronized void render(Graphics g, int x, int y, int width, int height) {
        g.drawImage(frames[currentFrameIndex].getImage(), x, y, width, height, null);
    }
}
