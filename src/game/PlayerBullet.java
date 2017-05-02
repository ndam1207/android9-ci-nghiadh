package game;

import game.controllers.CollisionManager;
import game.controllers.Controller;
import game.enemies.EnemyController;
import game.models.GameRect;
import game.views.ImageRenderer;

import javax.imageio.ImageIO;
import javax.sound.sampled.Control;
import java.awt.*;


public class PlayerBullet extends Controller implements Collider{
    int damage = 1;
    private boolean hasTouched;
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
    }

    public boolean hasTouched() {
        return hasTouched;
    }

    public void setTouched(boolean hasTouched) {
        this.hasTouched = hasTouched;
    }
}
