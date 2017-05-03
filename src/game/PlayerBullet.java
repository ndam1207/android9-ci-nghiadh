package game;

import game.controllers.Collider;
import game.controllers.CollisionManager;
import game.controllers.Controller;
import game.enemies.EnemyController;
import game.enemies.EnemyController2;
import game.models.GameRect;
import game.views.ImageRenderer;

import java.awt.*;


public class PlayerBullet extends Controller implements Collider {
    int damage = 1;
    private boolean isDead;
    public PlayerBullet(int x, int y, Image image) {
        super(new GameRect(x - image.getWidth(null) / 2,y - image.getHeight(null),20,50),
                new ImageRenderer(image));
        CollisionManager.instance.add(this);
    }



    public void draw(Graphics graphics) {
        imageRenderer.render(graphics,gameRect);
    }

    public void update() {
        gameRect.move(0,-15);
    }

    @Override
    public String getName() {
        return "PlayerBullet";
    }

    @Override
    public void onCollide(Collider other) {
        if(other instanceof EnemyController){
            ((EnemyController) other).getHit(damage);
        }
        if(other instanceof EnemyController2){
            ((EnemyController2) other).getHit(damage);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean hasTouched) {
        this.isDead = isDead;
    }
}
