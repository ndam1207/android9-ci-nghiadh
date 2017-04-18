import enemies.DiagonalMoveBehavior;
import enemies.EnemyController;
import enemies.HorizontalMoveBehavior;
import enemies.MoveBehavior;
import utils.Utils;

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

public class GameWindow extends Frame {

    Image backgroundImage;

    BufferedImage backBufferImage;
    Graphics backbufferGraphics;

    boolean isUpPressed;
    boolean isDownPressed;
    boolean isLeftPressed;
    boolean isRightPressed;
    boolean isSpacePressed;
    boolean isEnemyAllowed;

    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 800;

    private int count = 0;

    ArrayList<PlayerBullet> playerPlayerBullets;
    PlayerController playerController;
    ArrayList<EnemyController> enemyControllers;

    //2 Draw
    public GameWindow() {
        setVisible(true);

        playerPlayerBullets = new ArrayList<>();

        playerController = new PlayerController(200 - 17, 500 - 25, Utils.loadImage("res/plane3.png"));

        playerController.setPlayerPlayerBullets(playerPlayerBullets);

        enemyControllers = new ArrayList<>();

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

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
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        isRightPressed = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        isLeftPressed = true;
                        break;
                    case KeyEvent.VK_UP:
                        isUpPressed = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        isDownPressed = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        isSpacePressed = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        isRightPressed = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        isLeftPressed = false;
                        break;
                    case KeyEvent.VK_UP:
                        isUpPressed = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        isDownPressed = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        isSpacePressed = false;
                        break;
                }
            }
        });

        //1 Load image
        try {
            backgroundImage = ImageIO.read(new File("res/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    if(count>=200){
                        isEnemyAllowed = true;
                        count=0;
                    }

                    if(isEnemyAllowed){
                        for(int i=0; i<=SCREEN_HEIGHT; i+=100) {
                            EnemyController enemyController = new EnemyController(300, 10, Utils.loadImage("res/enemy_plane_white_1.png"));
                            enemyController.setMoveBehavior(new MoveBehavior());
                            if(i%200==0){
                                enemyController.setMoveBehavior(new HorizontalMoveBehavior(1,0));
//                    if(enemyController.getGameRect().getX() >= SCREEN_WIDTH)
//                        enemyController.setMoveBehavior(new HorizontalMoveBehavior(-1,0));
                            }

                            if(i%300==0){
                                enemyController.setMoveBehavior(new DiagonalMoveBehavior(1,2,-2,-1));
                            }

                            enemyControllers.add(enemyController);
                        }
                        isEnemyAllowed=false;
                    }
                    playerController.processInput(isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isSpacePressed);

                    for (PlayerBullet bullet : playerPlayerBullets) {
                        bullet.update();
                    }

                    playerController.update();

                    for(EnemyController enemyController : enemyControllers) {
                        enemyController.update();
                    }

                    // Draw
                    repaint();
                }
            }
        });

        thread.start();
    }



    @Override
    public void update(Graphics g) {
        backbufferGraphics.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);

        playerController.draw(backbufferGraphics);

        for (PlayerBullet playerBullet : playerPlayerBullets) {
            playerBullet.draw(backbufferGraphics);
        }

        for (EnemyController enemyController : enemyControllers) {
            enemyController.draw(backbufferGraphics);
        }
        g.drawImage(backBufferImage, 0, 0, null);

    }
}
