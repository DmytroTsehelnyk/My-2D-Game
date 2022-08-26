package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTitleSize = 32; // default size of game characters (32x32px)
    final int scale = 2; // make characters 64 pixels to fit modern screens
    public final int tileSize = originalTitleSize * scale; // make map size 48x48 pixels
    final int maxScreenCol = 14; // 14 tiles horizontal
    final int maxScreenRow = 10; // 10 tiles vertical - 4x3 game map (showed in screen)
    final int screenWidth = tileSize * maxScreenCol; // 64*14 (768px)
    final int screenHeight = tileSize * maxScreenRow; // 64*10 (640px)

    // FPS
    double FPS = 60; // 60 Frames Per Second

    // VARIABLES
    KeyHandler keyH = new KeyHandler(); // add keys to move character
    Thread gameThread; // method to add "time" to the game (make it running after start)
    Player player = new Player(this, keyH);
    int playerX = 100; // player start position on the game map on X horizontal
    int playerY = 100; // player start position on the game map on Y vertical
    int playerSpeed = 4; // player speed on the game map

    // GAME MAP CONNECTING
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set game size
        this.setBackground(Color.black); // background of game map
        this.setDoubleBuffered(true); // all drawings will be done offscreen (for rendering)
        this.addKeyListener(keyH); // GamePanel will recognise the key input on keyboard
        this.setFocusable(true); // GamePanel can be "focused" to receive key input
    }

    // GAME PROCESS UPDATING
    public void startGameThread() {
        gameThread = new Thread(this); // automatically call Thread in GamePanel class
        gameThread.start(); // automatically call run method
    }

    // FPS LOCK (DELTA METHOD)
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; // screen will update every 0.016 seconds
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

    // UPDATING
    public void update() { // as long as game is running, it updates info and call repaint
        player.update();
    }

    // GRAPHICS
    public void paintComponent(Graphics g) { // will draw objects on the screen
        super.paintComponent(g); // call parent class (JPanel) to draw obj
        Graphics2D g2 = (Graphics2D) g; // changed graphics to 2D for better control over geometry
        player.draw(g2);
        g2.dispose(); // dispose graphic and release system resources that it's using
    }
}
