package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // FPS
    //final double FPS_120 = 120; // 120 Frames Per Second
    final double FPS_60 = 60; // 60 Frames Per Second

    // VARIABLES
    KeyHandler keyH = new KeyHandler(); // add keys to move character
    Thread gameThread; // method to add "time" to the game (make it running after start)
    Player player = new Player(this, keyH);
    public int animTick = 0, animIndex = 0, animSpeed = 24;

    // GAME MAP CONNECTING
    public GamePanel() {
        setPanelSize();
        player.importImg();
        player.loadAnimations();
        this.setBackground(Color.GRAY); // background of game map
        this.setDoubleBuffered(true); // all drawings will be done offscreen (for rendering)
        this.addKeyListener(keyH); // GamePanel will recognise the key input on keyboard
        this.setFocusable(true); // GamePanel can be "focused" to receive key input
    }

    // SCREEN SETTINGS
    private void setPanelSize () {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }

    // GAME PROCESS UPDATING
    public void startGameThread() {
        gameThread = new Thread(this); // automatically call Thread in GamePanel class
        gameThread.start(); // automatically call run method
    }

    // FPS LOCK (DELTA METHOD)
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS_60; // screen will update every 0.016 seconds
        double delta = 0; // Delta or Accumulator FPS Restriction method
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) { // as long as game open it will be running

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update(); // update character position every tic
                repaint(); // call paintComponent and draw new screen with updated information
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) { // show FPS log
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

//    @Override
//    public void run() {
//
//        double timesPerFrame = 1000000000.0/FPS; // screen will update every 0.016 seconds
//        long lastTime = System.nanoTime();
//        long currentTime = System.nanoTime();
//        int frames = 0;
//        long lastCheck = System.currentTimeMillis();
//
//        while (gameThread != null) { // as long as game open it will be running
//            if (currentTime - lastTime >= timesPerFrame) {
//                update();
//                repaint();
//                lastTime = currentTime;
//                frames++;
//            }
//            if (System.currentTimeMillis() - lastCheck >= 1000) {
//                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS " + frames);
//                frames = 0;
//            }
//        }
//    }

    // UPDATING
    public void update() { // as long as game is running, it updates info and call repaint
        player.update();
    }
    private void updateAnimationTick() {
        animTick++;
        if(animTick >= animSpeed) {
            animTick = 0;
            animIndex++;
            if(animIndex >= player.idleAnim.length){
                animIndex = 0;
            }
        }
    }

    // GRAPHICS
    public void paintComponent(Graphics g) { // will draw objects on the screen
        super.paintComponent(g); // call parent class (JPanel) to draw obj
        Graphics2D g2D = (Graphics2D) g;
        updateAnimationTick();
        player.draw(g2D);
    }
}
