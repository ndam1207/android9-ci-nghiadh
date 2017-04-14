import java.awt.*;
import java.util.ArrayList;

/**
 * Created by PC on 4/14/2017.
 */
public class EnemyPlane {
    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed = 5;
    private boolean isCrossBordered;


    public EnemyPlane(Image image, int x, int y, int width, int height,int speed) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public Image getImage() {
        return image;
    }

    public boolean isCrossBordered() {
        return isCrossBordered;
    }

    public void setCrossBordered(boolean crossBordered) {
        isCrossBordered = crossBordered;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public void fly(){
        y+=speed;
        if(y>=GameWindow.SCREEN_HEIGHT){
            isCrossBordered=true;
        }
    }

    public void draw(Graphics graphics){
        graphics.drawImage(image, x,y, width, height,null);
    }
}
