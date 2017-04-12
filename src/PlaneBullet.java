import java.awt.*;

public class PlaneBullet {
    public static final int SPEED = 15;
    private int x;
    private int y;
    private Image image;
    private int width;
    private int height;

    public PlaneBullet(Image image,int x, int y, int width, int height) {
        this.x = x - image.getWidth(null)/2;
        this.y = y - image.getHeight(null);
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    public void draw(Graphics graphics){
        graphics.drawImage(image, x,y, width, height,null);
    }
    public void fly(){
        this.y -= SPEED;
    }
}
