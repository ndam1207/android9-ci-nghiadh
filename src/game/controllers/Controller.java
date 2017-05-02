package game.controllers;

import game.models.GameRect;
import game.views.ImageRenderer;

import java.awt.*;

public class Controller {
    public Controller(GameRect gameRect, ImageRenderer imageRenderer) {
        this.gameRect = gameRect;
        this.imageRenderer = imageRenderer;
    }

    protected GameRect gameRect;
    protected ImageRenderer imageRenderer;

    public GameRect getGameRect() {
        return gameRect;
    }

    public ImageRenderer getImageRenderer() {
        return imageRenderer;
    }

    public void draw(Graphics graphics){
        imageRenderer.render(graphics,gameRect);
    }

    public void update(){}
}

