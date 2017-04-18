package enemies;

import models.GameRect;
import views.ImageRenderer;

import java.awt.*;
import java.util.ArrayList;

public class EnemyController {
    private GameRect gameRect;
    private ImageRenderer imageRenderer;
    private MoveBehavior moveBehavior;
    private boolean shootDisabled;

    private int dx;
    private int dy;

    private ArrayList<EnemyBullet> enemyBullets;
    int cooldownTime;

    public EnemyController(int x,int y, Image image){
        imageRenderer = new ImageRenderer(image);
        gameRect = new GameRect(x, y, 60, 60);
    }

    public void setMoveBehavior(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    public GameRect getGameRect() {
        return gameRect;
    }

    public ImageRenderer getImageRenderer() {
        return imageRenderer;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void draw(Graphics graphics){
        imageRenderer.render(graphics,gameRect);
    }

    public void update(){
        if(moveBehavior!=null){
            moveBehavior.move(gameRect);
        }
        if(shootDisabled) {
            cooldownTime++;
            if (cooldownTime > 5) {
                shootDisabled = false;
                cooldownTime = 0;
            }
        }
    }

}
