package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity {
    GamePanel gp; // activate GamePanel class
    KeyHandler keyH; // activate KeyHandler class
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
    }
    public void setDefaultValues() {
        x = 100; // start position X (vertical)
        y = 100; // start position Y (horizontal)
        speed = 4;
    }

    // MOVEMENT CONTROL
    public void update() { // as long as game is running, it updates info and call repaint
        if(keyH.upPressed) {
            y -= speed; // if Up pressed, move character up for 4 pixels
        } else if (keyH.downPressed) {
            y += speed; // if Down pressed, move character down for 4 pixels
        } else if (keyH.leftPressed) {
            x -= speed; // if Left pressed, move character left for 4 pixels
        } else if (keyH.rightPressed) {
            x += speed; // if Right pressed, move character right for 4 pixels
        }
    }

    // CHARACTER GRAPHICS
    public void draw (Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}
