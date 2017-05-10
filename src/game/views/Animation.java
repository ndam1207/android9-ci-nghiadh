package game.views;

import game.models.GameRect;
import game.utils.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Animation {
    private List<Image> images;
    private int imageIndex = 0;
    private int interval;
    private int time;

    private boolean isOneTime;
    private boolean hasEnded;

    public Animation(List<Image> images, int interval, boolean isOneTime) {
        this.images = images;
        this.interval = interval;
        this.isOneTime = isOneTime;
    }

    public Animation(List<Image> images, boolean isOneTime) {
        this(images, 5, isOneTime);
    }

    public Animation(List<Image> images) {
        this(images,5, false);
    }

    public boolean isEnded() {
        return hasEnded;
    }

    public boolean isOneTime() {
        return isOneTime;
    }

    public void draw(Graphics g, GameRect rect){
        time++;
        if(time >= interval){
            time = 0;

            imageIndex++;
            if(imageIndex >= images.size()){
                if(isOneTime){
                    hasEnded = true;
                }
                imageIndex = 0;
            }
        }

        if(isOneTime && hasEnded){
            return;
        }
        g.drawImage(images.get(imageIndex),rect.getX(),rect.getY(),null);
    }
}
