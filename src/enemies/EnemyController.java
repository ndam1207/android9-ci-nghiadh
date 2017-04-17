package enemies;

import models.GameRect;
import views.ImageRenderer;

import java.awt.*;
import java.util.ArrayList;

public class EnemyController {
    private GameRect gameRect;
    private ImageRenderer imageRenderer;

    private int dx;
    private int dy;

    private ArrayList<EnemyController> enemies = new ArrayList<>();

    public EnemyController(int x,int y, Image image){
        imageRenderer = new ImageRenderer(image);
        gameRect = new GameRect(x, y, 32, 32);
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

    public ArrayList<EnemyController> getEnemies() {
        return enemies;
    }

    public void draw(Graphics graphics){
        imageRenderer.render(graphics,gameRect);
    }

    public void update(){
        gameRect.move(0,5);
    }
}
