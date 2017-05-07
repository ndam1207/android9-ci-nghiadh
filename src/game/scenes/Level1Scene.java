package game.scenes;

import game.GameWindow;
import game.PlayerBullet;
import game.controllers.*;
import game.enemies.*;
import game.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Random;

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
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 800;


    private boolean enableEnemyShoot = true;
    private int coolDownTime;

    public Level1Scene() {
        playerController = new PlayerController(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 100, Utils.loadImage("res/plane3.png"));


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

                ControllerManager.instance.add(enemyController);

            }

            for(int i=0; i<=200; i+=100) {
                EnemyController2 enemyController2 = new EnemyController2(randomX+=150, 20, Utils.loadImage("res/enemy-green-3.png"));
                enemyController2.setMoveBehavior(new VerticalMoveBehavior(0,2));
                ControllerManager.instance.add(enemyController2);
            }
            isEnemyAllowed=false;
        }

        //CREATE ENEMY BULLETS
//        setEnemyShoot();
        if(!enableEnemyShoot){
            coolDownTime--;
            if(coolDownTime <= 0){
                enableEnemyShoot = true;
            }
        }

        //PLAYER MOVES
        playerController.processInput(isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isSpacePressed);

        //PLAYER MOVES
        playerController.update();

        ControllerManager.instance.update();


        //GAME OVER
        if(playerController.getGameRect().isDead()){
            System.out.println("GAME OVER!!");
//            GameWindow.instance.setCurrentScene(new GameOverScene());
        }
    }


//    public void setEnemyShoot(){
//        if(enableEnemyShoot){
//            enableEnemyShoot = false;
//            for(EnemyController enemyController : enemyControllers){
//                EnemyBullet enemyBullet = new EnemyBullet(enemyController.getGameRect().getX(),
//                        enemyController.getGameRect().getY(),
//                        Utils.loadImage("res/enemy_bullet.png"));
//                ControllerManager.instance.add(enemyBullet);
//                coolDownTime = 30;
//            }
//        }
//    }

    @Override
    public void draw(Graphics graphics){
        graphics.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);

        playerController.draw(graphics);

        ControllerManager.instance.draw(graphics);
    }}

