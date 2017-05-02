package game;

import game.controllers.CollisionManager;
import game.enemies.*;
import game.models.GameRect;
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
    PlayerController playerController;
    Collider collider;

    ArrayList<PlayerBullet> playerPlayerBullets;
    ArrayList<EnemyController> enemyControllers;
    ArrayList<EnemyBullet> enemyBullets;
//    ArrayList<Collider> colliders;
    ArrayList<EnemyController2> enemyController2s;


    //2 Draw
    public GameWindow() {
        setVisible(true);

        playerPlayerBullets = new ArrayList<>();

        playerController = new PlayerController(200 - 17, 500 - 25, Utils.loadImage("res/plane3.png"));

        playerController.setPlayerPlayerBullets(playerPlayerBullets);

        enemyControllers = new ArrayList<>();

        enemyController2s = new ArrayList<>();

        enemyBullets = new ArrayList<>();

//        CollisionManager.instance.setColliders(colliders);

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
                    Random rand = new Random();
                    int randomX = rand.nextInt(SCREEN_WIDTH-20) + 10;
                    count++;
                    if(count>=200){
                        isEnemyAllowed = true;
                        count=0;
                    }
                    // CREATE ENEMIES
                    if(isEnemyAllowed){
                        for(int i=0; i<=SCREEN_HEIGHT; i+=100) {
                            EnemyController enemyController = new EnemyController(randomX, 10, Utils.loadImage("res/enemy_plane_white_1.png"));
                            enemyController.setMoveBehavior(new MoveBehavior());
                            if(i%200==0){
                                enemyController.setMoveBehavior(new HorizontalMoveBehavior(2,0));
                            }

                            if(i%300==0){
                                enemyController.setMoveBehavior(new DiagonalMoveBehavior(1,2,-2,-1));
                            }

                            enemyControllers.add(enemyController);
                            enemyController.setEnemyBullets(enemyBullets);

                        }

                        for(int i=0; i<=200; i+=100) {
                            EnemyController2 enemyController2 = new EnemyController2(randomX+=150, 20, Utils.loadImage("res/enemy-green-3.png"));
                            enemyController2.setMoveBehavior(new VerticalMoveBehavior(0,2));
                            enemyController2s.add(enemyController2);
                            enemyController2.setEnemyBullets(enemyBullets);
                        }
                        isEnemyAllowed=false;
                    }


                    //CREATE ENEMY BULLETS
                    for(int i=0;i<enemyControllers.size();i++){
                        if(enemyControllers.get(i) != null) {
                            enemyControllers.get(i).coolDown();
                            if(enemyControllers.get(i).getCooldownTime() >=5) {
                                EnemyBullet enemyBullet = new EnemyBullet(enemyControllers.get(i).getGameRect().getX(), enemyControllers.get(i).getGameRect().getY(), Utils.loadImage("res/bullet-round.png"));
                                enemyControllers.get(i).setEnemyBullets(enemyBullets);
                                enemyBullets.add(enemyBullet);
                                enemyControllers.get(i).setShootDisabled(false);
                                enemyControllers.get(i).setCooldownTime(0);
                            }
                       }
                    }



                    playerController.processInput(isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isSpacePressed);

                    //PLAYER BULLETS MOVE
                    for (PlayerBullet bullet : playerPlayerBullets) {
                        bullet.update();
                    }

                    //PLAYER MOVES
                    playerController.update();

                    //ENEMY BULLETS MOVE
                    for(EnemyBullet enemyBullet : enemyBullets){
                        if(enemyBullet != null) {
                            enemyBullet.update();
                        }
                    }

                    //ENEMIES MOVE
                    for(EnemyController enemyController : enemyControllers) {
                        enemyController.update();
                    }
                    for(EnemyController2 enemyController2 : enemyController2s) {
                        enemyController2.update();
                    }

                    //REMOVE DEAD OBJECTS
                    Iterator<EnemyController> enemyIterator = enemyControllers.iterator();
                    while(enemyIterator.hasNext()){
                        EnemyController enemyController = enemyIterator.next();
                        if(enemyController.getGameRect().isDead()){
                            enemyIterator.remove();
                        }
                    }

                    Iterator<EnemyController2> enemyController2Iterator = enemyController2s.iterator();
                    while(enemyController2Iterator.hasNext()){
                        EnemyController2 enemyController2 = enemyController2Iterator.next();
                        if(enemyController2.getGameRect().isDead()){
                            enemyController2Iterator.remove();
                        }
                    }

                    Iterator<PlayerBullet> playerBulletIterator = playerPlayerBullets.iterator();
                    while(playerBulletIterator.hasNext()){
                        PlayerBullet playerBullet = playerBulletIterator.next();
                        if(playerBullet.hasTouched()){
                            playerBulletIterator.remove();
                        }
                    }

                    Iterator<EnemyBullet> enemyBulletIterator = enemyBullets.iterator();
                    while(enemyBulletIterator.hasNext()){
                        EnemyBullet enemyBullet = enemyBulletIterator.next();
                        if(enemyBullet.hasTouched()){
                            enemyBulletIterator.remove();
                        }
                    }

//                    Iterator<Collider> colliderIterator = colliders.iterator();
//                    while(colliderIterator.hasNext()){
//                        Collider collider = colliderIterator.next();
//                        CollisionManager.instance.remove(collider);
//                    }

                    //REMOVE EXTRA ENEMIES
                    while(enemyIterator.hasNext()){
                        EnemyController enemyController = enemyIterator.next();
                        if(enemyController.getGameRect().getY() >= SCREEN_HEIGHT){
                            enemyIterator.remove();
                        }
                    }

                    //REMOVE EXTRA ENEMY BULLETS
                    while(enemyBulletIterator.hasNext()){
                        EnemyBullet enemyBullet = enemyBulletIterator.next();
                        if(enemyBullet.getGameRect().getY() >= SCREEN_HEIGHT){
                            enemyBulletIterator.remove();
                        }
                    }

                    //GAME OVER
                    if(playerController.getGameRect().isDead()){
                        System.out.println("GAME OVER!!");
                        break;
                    }

                    CollisionManager.instance.update();

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

        for (EnemyController2 enemyController2 : enemyController2s){
            enemyController2.draw(backbufferGraphics);
        }

        for(EnemyBullet enemyBullet : enemyBullets){
            enemyBullet.draw(backbufferGraphics);
        }


        g.drawImage(backBufferImage, 0, 0, null);

    }
}
