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
    public BufferedImage owl_idle, owl_walking, owl_running;
    public BufferedImage[] idleAnim;
    public BufferedImage[] walkingAnim;
    public BufferedImage[] runningAnim;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
    }
    public void setDefaultValues() {
        xDelta = 100; // start position X (vertical)
        yDelta = 100; // start position Y (horizontal)
    }

    // MOVEMENT CONTROL
    public void update() { // as long as game is running, it updates info and call repaint
        if (keyH.shiftPressed) {
            speed = 10; // if Shift pressed + key, character runs
        } else speed = 4;

        if (keyH.upPressed) {
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
        InputStream is1 = getClass().getResourceAsStream("/owl_idle.png");
        InputStream is2 = getClass().getResourceAsStream("/owl_walking.png");
        InputStream is3 = getClass().getResourceAsStream("/owl_running.png");
        try  {
            owl_idle = ImageIO.read(Objects.requireNonNull(is1));
            owl_walking = ImageIO.read(Objects.requireNonNull(is2));
            owl_running = ImageIO.read(Objects.requireNonNull(is3));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(is1).close();
                Objects.requireNonNull(is2).close();
                Objects.requireNonNull(is3).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // MAKE ANIMATION FOR IDLE POSITION
    public void loadAnimations() {

        idleAnim = new BufferedImage[4];

        for(int i = 0; i < idleAnim.length; i++) {
            idleAnim[i] = owl_idle.getSubimage(i * 32, 0,32,32);
        }

        walkingAnim = new BufferedImage[6];

        for(int i = 0; i < walkingAnim.length; i++) {
            walkingAnim[i] = owl_walking.getSubimage(i * 32, 0,32,32);
        }

        runningAnim = new BufferedImage[6];

        for(int i = 0; i < runningAnim.length; i++) {
            runningAnim[i] = owl_running.getSubimage(i * 32, 0,32,32);
        }
    }

    // CHARACTER GRAPHICS
    public void draw (Graphics2D g2D) {
        if (!keyH.shiftPressed && (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)) {
            g2D.drawImage(walkingAnim[gp.animIndex], xDelta, yDelta,128,128, null);
            gp.animSpeed = 20;
        } else if (keyH.shiftPressed && (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)) {
            g2D.drawImage(runningAnim[gp.animIndex], xDelta, yDelta,128,128, null);
            gp.animSpeed = 15;
        } else {
            g2D.drawImage(idleAnim[gp.animIndex], xDelta, yDelta,128,128, null);
            gp.animSpeed = 30;
        }
    }
}
