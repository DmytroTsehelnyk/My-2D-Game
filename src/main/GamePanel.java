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
    Thread gameThread; // method to add "time" to the game (make it running after start)
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set game size
        this.setBackground(Color.black); // background of game map
        this.setDoubleBuffered(true); // all drawings will be done offscreen (for rendering)
    }
    public void startGameThread() {
        gameThread = new Thread(this); // automatically call Thread in GamePanel class
        gameThread.start(); // automatically call run method
    }
    @Override
    public void run() {
        while(gameThread != null) { // as long as game open it will be running
            // 1 UPDATE CHARACTER POSITION
            update(); // update character position every tic
            // 2 DRAW THE SCREEN WITH UPDATED INFORMATION (60 times at second if fps is 60)
            repaint(); // call paintComponent
        }
    }
    public void update() { // as long as game is running, it updates info and call repaint

    }
    public void paintComponent(Graphics g) { // will draw objects on the screen
        super.paintComponent(g); // call parent class (JPanel) to draw obj
        Graphics2D g2 = (Graphics2D) g; // changed graphics to 2D for better control over geometry
        g2.setColor(Color.white);
        g2.fillRect(100, 100, tileSize, tileSize);
        g2.dispose(); // release any system resources that it's using
    }
}
