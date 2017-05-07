package game;

import game.controllers.Collider;
import game.controllers.CollisionManager;
import game.controllers.ControllerManager;
import game.controllers.PlayerController;
import game.enemies.*;
import game.scenes.GameScene;
import game.scenes.Level1Scene;
import game.scenes.MenuScene;
import game.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameWindow extends Frame {
    BufferedImage backBufferImage;
    Graphics backbufferGraphics;

    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 700;

    GameScene currentScene;

    public static GameWindow instance;

    public void setCurrentScene(GameScene currentScene) {
        this.currentScene = currentScene;
    }

    //2 Draw
    public GameWindow() {
        setVisible(true);

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        instance = this;

        currentScene = new MenuScene();

        backBufferImage = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        backbufferGraphics = backBufferImage.getGraphics();

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                currentScene.keyPressed(e);
            }
            public void keyReleased(KeyEvent e) {
                currentScene.keyReleased(e);
                 }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    CollisionManager.instance.update();
                    currentScene.update();

                    // Draw
                    repaint();
                }
            }
        });

        thread.start();
    }


    @Override
    public void update(Graphics g) {
        currentScene.draw(backbufferGraphics);
        g.drawImage(backBufferImage, 0, 0, null);

    }
}
