package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp; // activate GamePanel class
    KeyHandler keyH; // activate KeyHandler class
    public BufferedImage img;
    public BufferedImage[] idleAnim;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
    }
    public void setDefaultValues() {
        xDelta = 100; // start position X (vertical)
        yDelta = 100; // start position Y (horizontal)
        speed = 6;
    }

    // MOVEMENT CONTROL
    public void update() { // as long as game is running, it updates info and call repaint
        if(keyH.upPressed) {
            yDelta -= speed; // if Up pressed, move character up for 4 pixels
        } else if (keyH.downPressed) {
            yDelta += speed; // if Down pressed, move character down for 4 pixels
        } else if (keyH.leftPressed) {
            xDelta -= speed; // if Left pressed, move character left for 4 pixels
        } else if (keyH.rightPressed) {
            xDelta += speed; // if Right pressed, move character right for 4 pixels
        }
    }

    // LOAD PLAYER CHARACTER IMAGE
    public void importImg() {
        InputStream is = getClass().getResourceAsStream("/player.png");
        try  {
            img = ImageIO.read(Objects.requireNonNull(is));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(is).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // MAKE ANIMATION FOR IDLE POSITION
    public void loadAnimations() {
        idleAnim = new BufferedImage[6];

        for(int i = 0; i < idleAnim.length; i++) {
            idleAnim[i] = img.getSubimage(i * 32, 0,64,64);
        }
    }

    // CHARACTER GRAPHICS
    public void draw (Graphics2D g2D) {
        g2D.drawImage(idleAnim[gp.animIndex], xDelta, yDelta,256,256, null);
    }
}
