package game.animation;

import java.awt.*;

public class Animation {
    private AnimationFrame[] frames;
    private double[] frameEndTimes;
    private int currentFrameIndex = 0;

    private double totalDuration = 0;
    private double currentTime = 0;

    public Animation(AnimationFrame... frames) {
        this.frames = frames;
        frameEndTimes = new double[frames.length];

        for (int idx = 0; idx < frames.length; idx++) {
            AnimationFrame frame = frames[idx];
            totalDuration += frame.getDuration();
            frameEndTimes[idx] = totalDuration;
        }
    }

    public synchronized void update(float increment) {
        currentTime += increment;

        if (currentTime > totalDuration) {
            wrapAnimation();
        }

        while (currentTime > frameEndTimes[currentFrameIndex]) {
            currentFrameIndex++;
        }
    }

    private synchronized void wrapAnimation() {
        currentFrameIndex = 0;
        currentTime %= totalDuration;
    }

    public synchronized void render(Graphics g, int x, int y) {
        g.drawImage(frames[currentFrameIndex].getImage(), x, y, null);
    }

    public synchronized void render(Graphics g, int x, int y, int width, int height) {
        g.drawImage(frames[currentFrameIndex].getImage(), x, y, width, height, null);
    }
}
