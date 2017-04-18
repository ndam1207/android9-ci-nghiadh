package enemies;

import models.GameRect;

/**
 * Created by valky on 4/18/2017.
 */
public class HorizontalMoveBehavior extends MoveBehavior {
    private int dx;
    private int dy;

    public HorizontalMoveBehavior(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void move(GameRect gameRect){
        gameRect.move(dx,dy);
    }
}
