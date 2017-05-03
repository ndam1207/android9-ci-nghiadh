package game.enemies;

import game.controllers.Collider;
import game.PlayerBullet;
import game.controllers.CollisionManager;
import game.controllers.Controller;
import game.models.GameRect;
import game.utils.Utils;
import game.views.ImageRenderer;

import java.awt.*;
import java.util.ArrayList;

public class EnemyController extends Controller implements Collider{
    private MoveBehavior moveBehavior;

    private int dx;
    private int dy;

    private ArrayList<EnemyBullet> enemyBullets;
    ArrayList<Controller> controllers;
    boolean enableEnemyShoot = true;
    int coolDownTime;

    public EnemyController(int x, int y, Image image){
        super(new GameRect(x, y, 60, 60),new ImageRenderer(image));
        CollisionManager.instance.add(this);
    }

    public void setMoveBehavior(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    public void setEnemyBullets(ArrayList<EnemyBullet> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    public void setControllers(ArrayList<Controller> controllers) {
        this.controllers = controllers;
    }

    public void update(){
//        if(enableEnemyShoot){
//            enableEnemyShoot = false;
//            for(Controller enemyController : controllers){
//                if(enemyController!=null) {
//                    EnemyBullet enemyBullet = new EnemyBullet(enemyController.getGameRect().getX(),
//                            enemyController.getGameRect().getY(),
//                            Utils.loadImage("res/enemy_bullet.png"));
//                    enemyBullets.add(enemyBullet);
//                    coolDownTime = 30;
//                }
//            }
//        }
        if(moveBehavior!=null) {
            moveBehavior.move(gameRect);
        }
    }
    public void getHit(int damage){
        gameRect.setDead(true);
        CollisionManager.instance.remove(this);
    }

    @Override
    public String getName() {
        return "EnemyController";
    }

    @Override
    public void onCollide(Collider other) {
        if(other instanceof PlayerBullet){
            ((PlayerBullet) other).setDead(true);
        }
    }
}
