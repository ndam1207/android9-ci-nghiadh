import java.awt.*;

public class PlayerPlane {
    public static final int SPEED = 10;
    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;
    public PlayerPlane(Image image, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
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
    public void draw(Graphics graphics){
        graphics.drawImage(image, x,y, width, height,null);
    }
    public void moveUp(){
        y -= SPEED;
    }
    public void moveDown(){
        y += SPEED;
    }
    public void moveLeft(){
        x -= SPEED;
    }
    public void moveRight(){
        x += SPEED;
    }
    public void coolDown(){
        int count = 0;
        while(count!=10){
            count++;
        }
    }

}

