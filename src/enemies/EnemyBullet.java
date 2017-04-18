package enemies;

import java.awt.*;

public class EnemyBullet {
    private int x;
    private int y;
    private Image image;

    public EnemyBullet(int x, int y, Image image) {
        this.x = x - image.getWidth(null) / 2;
        this.y = y - image.getHeight(null);
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(this.image, this.x, this.y,null);
    }

    public void update() {
        this.y -= 15;
    }
}
