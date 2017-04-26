import models.GameRect;
import utils.Utils;
import views.ImageRenderer;

import java.awt.*;
import java.util.ArrayList;


public class PlayerController {

    private GameRect gameRect;
    private ImageRenderer imageRenderer;
    private boolean shootDisabled;

    private int dx;
    private int dy;

    private ArrayList<PlayerBullet> playerPlayerBullets;
    int cooldownTime;

    public PlayerController(int x, int y, Image image) {
        imageRenderer = new ImageRenderer(image);
        gameRect = new GameRect(x, y, 70, 50);
    }

    public GameRect getGameRect() {
        return gameRect;
    }

    public void setPlayerPlayerBullets(ArrayList<PlayerBullet> playerPlayerBullets) {
        this.playerPlayerBullets = playerPlayerBullets;
    }

    public void draw(Graphics graphics) {
        imageRenderer.render(graphics,gameRect);
    }

    public void processInput(boolean isUpPressed,
                             boolean isDownPressed,
                             boolean isLeftPressed,
                             boolean isRightPressed,
                             boolean isSpacePressed) {
        dx =0;
        dy =0;
        if (isUpPressed) {
            dy -= 10;
        }

        if (isDownPressed) {
            dy += 10;
        }

        if (isLeftPressed) {
            dx -= 10;
        }

        if (isRightPressed) {
            dx += 10;
        }

        if(isSpacePressed && !shootDisabled) {
            shootDisabled = true;
            PlayerBullet playerBullet = new PlayerBullet(gameRect.getX() + 35, gameRect.getY(), Utils.loadImage("res/bullet.png"));
            playerPlayerBullets.add(playerBullet);
        }
    }



    public void update() {

        gameRect.move(dx, dy);

        if(shootDisabled) {
            // cooling down
            cooldownTime++;
            if (cooldownTime > 10) {
                shootDisabled = false;
                cooldownTime = 0;
            }
        }
    }
}
