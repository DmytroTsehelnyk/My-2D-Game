package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // GAME WINDOW CREATION
        JFrame window = new JFrame(); // creating new frame (window)
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // window close on 'x' button
        window.setResizable(false); // can't resize the window
        window.setTitle("Test"); // name of window (game name)

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // add game panel to window
        window.pack(); // window will be sized to size of subcomponents (GP)

        window.setLocationRelativeTo(null); // no specific location (display at the center)
        window.setVisible(true); // can see the game window

        gamePanel.startGameThread(); // will start the game after it opened
    }
}