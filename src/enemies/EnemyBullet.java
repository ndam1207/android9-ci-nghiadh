package enemies;

import models.GameRect;
import views.ImageRenderer;

import java.awt.*;

public class EnemyBullet {
    private int x;
    private int y;
    private Image image;
    private GameRect gameRect;
    private ImageRenderer imageRenderer;

    private boolean shootDisabled = true;

    int cooldownTime = 0;

    public EnemyBullet(int x, int y, Image image) {
        imageRenderer = new ImageRenderer(image);
        this.x = x +(60/2 - image.getWidth(null) / 2);
        this.y = y + 70;
        gameRect = new GameRect(this.x, this.y, 10, 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(this.image, this.x, this.y,null);
    }

    public void update() {
        if(shootDisabled) {
            cooldownTime++;
            if (cooldownTime > 5) {
                shootDisabled = false;
                cooldownTime = 0;
            }
        }

        if(!shootDisabled)
            gameRect.move(0,15);
    }
}
