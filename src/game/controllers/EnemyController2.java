package game.controllers;

import game.PlayerBullet;
import game.enemies.MoveBehavior;
import game.models.GameRect;
import game.views.ImageRenderer;

import java.awt.*;

public class EnemyController2 extends Controller implements Collider{
    private MoveBehavior moveBehavior;

    private int dx;
    private int dy;

    public EnemyController2(int x, int y, Image image){
        super(new GameRect(x, y, 60, 60),new ImageRenderer(image));
        CollisionManager.instance.add(this);
    }

    public void setMoveBehavior(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    public void update(){
        if(moveBehavior!=null) {
            moveBehavior.move(gameRect);
        }
    }
    public void getHit(int damage){
        gameRect.setDead(true);
        GameRect explosionGameRect = new GameRect(gameRect.getX(),gameRect.getY(),0,0);
        ExplosionController explosionController = new ExplosionController(explosionGameRect);
        ControllerManager.instance.add(explosionController);
    }

    @Override
    public String getName() {
        return "EnemyController";
    }

    @Override
    public void onCollide(Collider other) {
        if(other instanceof PlayerBullet){
            ((PlayerBullet) other).gameRect.setDead(true);
        }
    }


}
