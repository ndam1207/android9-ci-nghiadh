package game.controllers;

import game.controllers.Collider;
import game.PlayerBullet;
import game.controllers.CollisionManager;
import game.controllers.Controller;
import game.enemies.EnemyBullet;
import game.enemies.MoveBehavior;
import game.models.GameRect;
import game.views.ImageRenderer;

import java.awt.*;
import java.util.ArrayList;

public class EnemyController2 extends Controller implements Collider{
    private MoveBehavior moveBehavior;

    private int dx;
    private int dy;
    private boolean shootDisabled;

    public EnemyController2(int x, int y, Image image){
        super(new GameRect(x, y, 60, 60),new ImageRenderer(image));
        CollisionManager.instance.add(this);
        shootDisabled = true;
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
