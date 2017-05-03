package game.scenes;

import game.PlayerBullet;
import game.controllers.Collider;
import game.controllers.ControllerManager;
import game.controllers.PlayerController;
import game.enemies.*;
import game.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Level1Scene implements GameScene{
    Image backgroundImage = Utils.loadImage("res/background.png");

    boolean isUpPressed;
    boolean isDownPressed;
    boolean isLeftPressed;
    boolean isRightPressed;
    boolean isSpacePressed;
    boolean isEnemyAllowed;

    private int count = 0;
    PlayerController playerController;
    Collider collider;
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 800;

    ArrayList<PlayerBullet> playerPlayerBullets;
    ArrayList<EnemyController> enemyControllers;
    ArrayList<EnemyBullet> enemyBullets;
    ControllerManager enemyManager;
    ArrayList<EnemyController2> enemyController2s;

    private boolean enableEnemyShoot = true;
    private int coolDownTime;

    public Level1Scene() {
        playerController = new PlayerController(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 100, Utils.loadImage("res/plane3.png"));
        playerPlayerBullets = new ArrayList<>();
        playerController.setPlayerPlayerBullets(playerPlayerBullets);
        enemyControllers = new ArrayList<>();
        enemyController2s = new ArrayList<>();
        enemyBullets = new ArrayList<>();

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

    @Override
    public void update(){
        Random rand = new Random();
        int randomX = rand.nextInt(SCREEN_WIDTH-20) + 10;
        count++;
        if(count>=200){
            isEnemyAllowed = true;
            count=0;
        }
        // CREATE ENEMIES
        if(isEnemyAllowed){
            for(int i=0; i<=300; i+=100) {
                EnemyController enemyController = new EnemyController(randomX, 10, Utils.loadImage("res/enemy_plane_white_1.png"));
                enemyController.setMoveBehavior(new MoveBehavior());
                if(i==200){
                    enemyController.setMoveBehavior(new HorizontalMoveBehavior(2,0));
                }

                if(i==300){
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
        setEnemyShoot();
        if(!enableEnemyShoot){
            coolDownTime--;
            if(coolDownTime <= 0){
                enableEnemyShoot = true;
            }
        }

        //PLAYER MOVES
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
            if(playerBullet.isDead()){
                playerBulletIterator.remove();
            }
        }

        Iterator<EnemyBullet> enemyBulletIterator = enemyBullets.iterator();
        while(enemyBulletIterator.hasNext()){
            EnemyBullet enemyBullet = enemyBulletIterator.next();
            if(enemyBullet.isDead()){
                enemyBulletIterator.remove();
            }
        }


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
        }
    }


    public void setEnemyShoot(){
        if(enableEnemyShoot){
            enableEnemyShoot = false;
            for(EnemyController enemyController : enemyControllers){
                EnemyBullet enemyBullet = new EnemyBullet(enemyController.getGameRect().getX(),
                        enemyController.getGameRect().getY(),
                        Utils.loadImage("res/enemy_bullet.png"));
                enemyBullets.add(enemyBullet);
                coolDownTime = 30;
            }
        }
    }

    @Override
    public void draw(Graphics graphics){
        graphics.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);

        playerController.draw(graphics);

        for (PlayerBullet playerBullet : playerPlayerBullets) {
            playerBullet.draw(graphics);
        }

        for (EnemyController enemyController : enemyControllers) {
            enemyController.draw(graphics);
        }

        for (EnemyController2 enemyController2 : enemyController2s){
            enemyController2.draw(graphics);
        }

        for(EnemyBullet enemyBullet : enemyBullets){
            enemyBullet.draw(graphics);
        }
    }}

