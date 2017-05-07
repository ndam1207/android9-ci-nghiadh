package game.controllers;

import game.PlayerBullet;
import game.controllers.Collider;
import game.controllers.CollisionManager;
import game.controllers.Controller;
import game.enemies.EnemyBullet;
import game.models.GameRect;
import game.utils.Utils;
import game.views.ImageRenderer;

import java.awt.*;
import java.util.ArrayList;


public class PlayerController extends Controller implements Collider {
    private boolean shootDisabled;

    private int dx;
    private int dy;

    int hp = 5;

//    private ArrayList<PlayerBullet> playerPlayerBullets;
    int cooldownTime;

    public PlayerController(int x, int y, Image image) {
        super(new GameRect(x, y, 70, 50), new ImageRenderer(image));
        CollisionManager.instance.add(this);
    }

    @Override
    public String getName() {
        return null;
    }

    public GameRect getGameRect() {
        return gameRect;
    }

    @Override
    public void onCollide(Collider other) {
        if(other instanceof EnemyBullet){
            ((EnemyBullet) other).setDead(true);
        }
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
            ControllerManager.instance.add(playerBullet);
        }
    }


    public void getHit(int damage){
        hp -= damage;
        System.out.println(hp);
        if(hp <=0){
            gameRect.setDead(true);
        }
    }
    public void update() {

        gameRect.move(dx, dy);

        if(shootDisabled) {
            // cooling down
            cooldownTime++;
            if (cooldownTime > 10) {
                System.out.println(cooldownTime);
                shootDisabled = false;
                cooldownTime = 0;
            }
        }
    }
}
