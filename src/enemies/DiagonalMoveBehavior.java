package enemies;

import models.GameRect;

/**
 * Created by valky on 4/18/2017.
 */
public class DiagonalMoveBehavior extends MoveBehavior {
    private int dx1;
    private int dy1;
    private int dx2;
    private int dy2;

    public DiagonalMoveBehavior(int dx1, int dy1, int dx2, int dy2) {
        this.dx1 = dx1;
        this.dy1 = dy1;
        this.dx2 = dx2;
        this.dy2 = dy2;
    }

    public void move(GameRect gameRect){
        gameRect.move(dx1,dy1);
        gameRect.move(dx2,dy2);
    }
}
