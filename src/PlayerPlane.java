import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.util.ArrayList;

public class PlayerPlane {
    public static final int SPEED = 10;
    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;
    PlaneBullet planeBullet;
    ArrayList<PlaneBullet> planeBullets = new ArrayList<>();
    boolean shootEnabled = true;

    public PlayerPlane(Image image, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
        shootEnabled = true;
    }

    public ArrayList<PlaneBullet> getPlaneBullets() {
        return planeBullets;
    }

    public void setPlaneBullets(ArrayList<PlaneBullet> planeBullets) {
        this.planeBullets = planeBullets;
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

    public void createBullets(Image image){
        PlaneBullet planeBullet = new PlaneBullet(image,x+35,y-10,20,40);
        planeBullets.add(planeBullet);
    }

    public void shoot(){
        for (PlaneBullet planeBullet : planeBullets){
            planeBullet.fly();
        }
    }

    public void drawBullets(Graphics graphics){
        for(PlaneBullet planeBullet : planeBullets){
            planeBullet.draw(graphics);
        }
    }

    int coolDownTime = 10;

    public void coolDown(){
        if(!shootEnabled){
                coolDownTime--;
                if (coolDownTime <= 0) {
                    coolDownTime = 10;
                    shootEnabled = true;
                }
            }
    }

}

