package game;

import game.controllers.CollisionManager;
import game.models.GameRect;

public interface Collider {
    String getName();
    GameRect getGameRect();
    void onCollide(Collider other);
}

