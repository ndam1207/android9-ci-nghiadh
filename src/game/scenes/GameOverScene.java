package game.scenes;

import game.GameWindow;
import game.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverScene implements GameScene{
    Image menuBackGround = Utils.loadImage("res/F15.jpg");
    Image startButton = Utils.loadImage("res/start.png");

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            GameWindow.instance.setCurrentScene(new Level1Scene());
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(menuBackGround,0,0, GameWindow.SCREEN_WIDTH,GameWindow.SCREEN_HEIGHT,null);
        graphics.drawImage(startButton,240,600,250,20,null);
    }

    @Override
    public void update() {

    }
}

