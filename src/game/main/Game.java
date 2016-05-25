package game.main;

import game.state.LoadState;
import game.state.State;
import game.utils.InputHandler;

import javax.swing.*;
import java.awt.*;



public class Game extends JPanel implements Runnable {
    private int gameHeight;
    private int gameWidth;

    private InputHandler inputHandler;
    private volatile GameStateTree gameStateTree;

    private Thread gameThread;
    private volatile boolean running;
    private volatile State currentState;
    private Image gameImage;


    public Game(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        setPreferredSize(new Dimension(gameWidth, gameHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();
    }


    /* Initialize game */
    @Override
    public void addNotify() {
        super.addNotify();

        initInput();
        gameStateTree = new GameStateTree();
        setCurrentState(new LoadState());
        initGame();
    }

    /* Run game */
    @Override
    public void run() {
        long updateDurationMillis = 0;
        long sleepDurationMillis = 0;

        while (running) {
            long millisBeforeUpdateRender = System.nanoTime();
            long deltaMillis = updateDurationMillis + sleepDurationMillis;

            updateAndRender(deltaMillis);

            updateDurationMillis = (System.nanoTime() - millisBeforeUpdateRender) / 1_000_000L;
            sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);

            try {
                Thread.sleep(sleepDurationMillis);
            } catch (InterruptedException e) {
                System.out.println("Error on rendering");
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameImage == null) {
            return;
        }

        g.drawImage(gameImage, 0, 0, null);
    }


    /* Update game state */
    public void setCurrentState(State newState) {
        newState.init(gameStateTree);
        currentState = newState;
        inputHandler.setCurrentState(newState);

        System.gc();
    }

    /* End game thread */
    public void exitGame() {
        running = false;
        System.exit(0);
    }


    /* Action Handlers Setups */
    private void initInput() {
        inputHandler = new InputHandler();
        addKeyListener(inputHandler);
    }

    /* Game setup */
    private void initGame() {
        running = true;

        gameThread = new Thread(this, "GameThread");
        gameThread.start();
    }

    /* Game loop */
    private void updateAndRender(long delta) {
        currentState.update(delta / 1000f, gameStateTree);
        prepareGameImage();
        currentState.render(gameImage.getGraphics());
        renderGameImage(getGraphics());
    }

    /* Prepare image before painting */
    private void prepareGameImage() {
        if (gameImage == null) {
            gameImage = createImage(gameWidth, gameHeight);
        }

        Graphics g = gameImage.getGraphics();
        g.clearRect(0, 0, gameWidth, gameHeight);
    }

    /* Render image to screen */
    private void renderGameImage(Graphics g) {
        if (gameImage != null) {
            g.drawImage(gameImage, 0, 0, null);
        }

        g.dispose();
    }
}
