import sun.awt.image.PixelConverter;

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

public class GameWindow extends Frame{
    Image backgroundImage;
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 800;

    private BufferedImage backBufferImage;
    private Graphics backbufferGraphics;

    boolean isUpPressed;
    boolean isDownPressed;
    boolean isLeftPressed;
    boolean isRightPressed;
    boolean isSpacePressed;

    PlayerPlane playerPlane;
    EnemyPlane enemyPlane;

    private int coolDownTime;

    ArrayList<EnemyPlane> enemyPlanes = new ArrayList<>();

    public GameWindow(){
        setVisible(true);
        setSize(600,800);

        backBufferImage = new BufferedImage(SCREEN_WIDTH,SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        backbufferGraphics = backBufferImage.getGraphics();
        playerPlane = new PlayerPlane(loadImage("plane3.png"),200,700,70,51);
        enemyPlane = new EnemyPlane(loadImage("enemy_plane_white_1.png"),50,100,60,60,5);
        enemyPlanes.add(enemyPlane);

        //add Listener
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

        //add KeyListener
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch(keyCode){
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
                int keyCode = e.getKeyCode();
                switch(keyCode){
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

        //Load Images Here
        try {
            backgroundImage = ImageIO.read(new File("resources/background.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Xu ly Logic
                    if(isUpPressed){
                        playerPlane.moveUp();
                    }
                    if(isDownPressed){
                        playerPlane.moveDown();
                    }
                    if(isLeftPressed){
                        playerPlane.moveLeft();
                    }
                    if(isRightPressed){
                        playerPlane.moveRight();
                    }

                    if(isSpacePressed){
                        if(playerPlane.shootEnabled) {
                            playerPlane.createBullets(loadImage("bullet.png"));
                            playerPlane.shootEnabled = false;
                            coolDownTime = 20;
                        }
                        playerPlane.coolDown(coolDownTime);
                    }
                    if(enemyPlane.isCrossBordered()){
                        createEnemyPlane();
                        enemyPlane.setCrossBordered(false); 

                    }
                    playerPlane.shoot();
                    for (EnemyPlane enemyPlane : enemyPlanes){
                        enemyPlane.fly();
                    }

                    //Draw
                    repaint();
                }
            }
        });
        thread.start();
    }

    public Image loadImage(String pathName){
        try {
            return ImageIO.read(new File("resources/"+pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createEnemyPlane(){
            EnemyPlane enemyPlane = new EnemyPlane(loadImage("enemy_plane_white_1.png"), 50, 100, 60, 60, 5);
            enemyPlanes.add(enemyPlane);
    }

    @Override
    public void update(Graphics g) {
        backbufferGraphics.drawImage(backgroundImage,0,0,600,800,null);
        playerPlane.draw(backbufferGraphics);
        playerPlane.drawBullets(backbufferGraphics);
        for(EnemyPlane enemyPlane: enemyPlanes){
            enemyPlane.draw(backbufferGraphics);
        }
        g.drawImage(backBufferImage,0,0,null);
    }
}
