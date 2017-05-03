package game.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface GameScene {
    public void keyPressed(KeyEvent e);

    public void keyReleased(KeyEvent e);

    public void draw(Graphics graphics);

    public void update();
}
