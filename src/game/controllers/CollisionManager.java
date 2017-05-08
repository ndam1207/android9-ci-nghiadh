package game.controllers;

import game.GameWindow;
import game.models.GameRect;

import java.util.ArrayList;
import java.util.Iterator;

public class CollisionManager {
    private ArrayList<Collider> colliders;

    public static final CollisionManager instance = new CollisionManager();

    private CollisionManager() {
        colliders = new ArrayList<>();
    }

    public void add(Collider collider){
        colliders.add(collider);
    }
    public void update(){
        for(int i=0; i<colliders.size()-1;i++){
            for(int j=i+1;j<colliders.size();j++){
                Collider ci = colliders.get(i);
                Collider cj = colliders.get(j);

                GameRect rectI = ci.getGameRect();
                GameRect rectJ = cj.getGameRect();

                if (rectI == null) {
                    System.out.println(String.format("rectI null: %s", ci.getName()));
                }

                if (rectJ == null) {
                    System.out.println(String.format("rectJ null: %s", cj.getName()));
                }

                if(rectI.intersects(rectJ)) {
                    ci.onCollide(cj);
                    cj.onCollide(ci);
                }
            }
        }
        Iterator<Collider> iterator = colliders.iterator();
        while(iterator.hasNext()){
            Collider collider = iterator.next();
            if((collider.getGameRect().isDead()) || (collider.getGameRect().getY() >= GameWindow.SCREEN_HEIGHT)){
                iterator.remove();
            }
        }
    }

    public void setColliders(ArrayList<Collider> colliders) {
        this.colliders = colliders;
    }

    public void remove(Collider collider){
        if(collider.getGameRect().isDead()){
            colliders.remove(collider);
        }
    }
}
