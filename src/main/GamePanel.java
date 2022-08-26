package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTitleSize = 16; // default size of game characters (16x16px)
    final int scale = 3; // make characters 48 pixels to fit modern screens
    final int tileSize = originalTitleSize * scale; // make map size 48x48 pixels
    final int maxScreenCol = 16; // 16 tiles horizontal
    final int maxScreenRow = 12; // 16 tiles vertical - 4x3 game map (showed in screen)
    final int screenWidth = tileSize * maxScreenCol; // 16*3 (768px)
    final int screenHeight = tileSize * maxScreenRow; // 12*3 (576px)

    // FPS
    double FPS = 60; // 60 Frames Per Second

    // VARIABLES
    KeyHandler keyH = new KeyHandler(); // add keys to move character
    Thread gameThread; // method to add "time" to the game (make it running after start)
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
    @Override // DELTA FPS Restriction
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
    // MOVEMENT CONTROL
    public void update() { // as long as game is running, it updates info and call repaint
        if(keyH.upPressed) {
            playerY -= playerSpeed; // if Up pressed, move character up for 4 pixels
        } else if (keyH.downPressed) {
            playerY += playerSpeed; // if Down pressed, move character down for 4 pixels
        } else if (keyH.leftPressed) {
            playerX -= playerSpeed; // if Left pressed, move character left for 4 pixels
        } else if (keyH.rightPressed) {
            playerX += playerSpeed; // if Right pressed, move character right for 4 pixels
        }
    }
    // GRAPHICS
    public void paintComponent(Graphics g) { // will draw objects on the screen
        super.paintComponent(g); // call parent class (JPanel) to draw obj
        Graphics2D g2 = (Graphics2D) g; // changed graphics to 2D for better control over geometry
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose(); // dispose graphic and release system resources that it's using
    }
}
