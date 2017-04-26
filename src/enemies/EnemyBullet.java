package enemies;

import models.GameRect;
import views.ImageRenderer;

import java.awt.*;

public class EnemyBullet {
    private GameRect gameRect;
    private ImageRenderer imageRenderer;

    private boolean shootDisabled = true;

    int cooldownTime = 0;

    public EnemyBullet(int x, int y, Image image) {
        imageRenderer = new ImageRenderer(image);
        gameRect = new GameRect(x +(60/2 - image.getWidth(null) / 2), y + 70, 10, 10);
    }

    public GameRect getGameRect() {
        return gameRect;
    }

    public void draw(Graphics graphics) {
        imageRenderer.render(graphics,gameRect);
    }

    public void update() {
        if(shootDisabled) {
            cooldownTime++;
            if (cooldownTime > 50) {
                shootDisabled = false;
                cooldownTime = 0;
            }
        }
        if(!shootDisabled)
            gameRect.move(0,15);
    }
}
