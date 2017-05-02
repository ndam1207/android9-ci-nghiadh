package game.enemies;

import game.models.GameRect;

public class MoveBehavior {
    public void move(GameRect gameRect){
        gameRect.move(0,2);
    }
}
