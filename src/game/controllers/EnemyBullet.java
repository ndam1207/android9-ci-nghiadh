package game.controllers;

import game.controllers.Collider;
import game.controllers.PlayerController;
import game.controllers.CollisionManager;
import game.controllers.Controller;
import game.models.GameRect;
import game.views.ImageRenderer;

import java.awt.*;

public class EnemyBullet extends Controller implements Collider {
    private ImageRenderer imageRenderer;
    int damage = 1;
    private boolean isDead;

    public EnemyBullet(int x, int y, Image image) {
        super(new GameRect(x +(60/2 - image.getWidth(null) / 2), y + 70, 45, 45),
                new ImageRenderer(image));
        CollisionManager.instance.add(this);

    }

    @Override
    public String getName() {
        return "EnemyBullet";
    }

    @Override
    public void onCollide(Collider other) {
        if(other instanceof PlayerController){
            ((PlayerController) other).getHit(damage);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean hasTouched) {
        this.isDead = isDead;
    }

    public void update() {
        gameRect.move(0,5);
    }
}
