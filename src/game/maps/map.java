package game.maps;

import game.Constants;
import game.entity.Entity;
import game.entity.Player;
import game.entity.Point;
import game.entity.Wall;
import game.entity.collisions.CollisionDetection;
import game.entity.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class map {
    public List<Entity> map=new ArrayList<Entity>();
    Random ra = new Random();
    public List<Entity> getMap(){
        return map;
    }
    public void initMap(int choice){
        if(choice==0){
            int num=ra.nextInt(20)+20;
            for(int i=0;i<num;i++) {
                while (true) {
                    Wall w;
                    double x=ra.nextInt((int) Constants.CANVAS_WIDTH/50)*50 ;
                    double y=ra.nextInt((int)Constants.CANVAS_HEIGHT/50)*50 ;
                    w = new Wall(50, 50, new Point((float)x, (float) y));
                    int l=checkColision(w);
                    //System.out.println(l);
                    if (l== 0) {
                        w.id = getSpareId();
                        map.add(w);
                        break;
                    }
                }
            }
        }
        else if (choice==1){
            map.add(new Wall(50, 50, new Point((float)50, (float) 50),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 100),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 150),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 200),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 250),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 300),getSpareId()));
//            map.add(new Wall(50, 50, new Point((float)50, (float) 350),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 400),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 450),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 500),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 550),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)50, (float) 600),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)200, (float) 100),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)200, (float) 200),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)200, (float) 300),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)200, (float) 400),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)200, (float) 500),getSpareId()));
            map.add(new Wall(50, 50, new Point((float)200, (float) 600),getSpareId()));
//            map.add(new Wall(50, 50, new Point((float)200, (float) 700),getSpareId()));
//            map.add(new Wall(50, 50, new Point((float)200, (float) 800),getSpareId()));

        }
    }
    public int checkColision(Wall e){
        Point[] p1= e.getCorners();
        Point[] p2;
        Entity e2;
        float x=e.getPosition().getX();
        float y=e.getPosition().getY();
        if((x-20<=0)||(x+20>=Constants.CANVAS_WIDTH)||(y-20<=0)||(y+20>=Constants.CANVAS_HEIGHT))
            return -1;
        for(int i=0;i<map.size();i++) {
            e2 = map.get(i);
            if(e2.equals(e))
                continue;
            p2=((Wall)e2).getCorners();
            if(CollisionDetection.isTouching(p1,p2)) {
                return e2.id;
            }
        }
        return 0;
    }
    public int getSpareId(){
        int id=ra.nextInt(9999)+1;
        if (map.size()!=0)
            while(true){
                int t=0;
                id=ra.nextInt(9999)+1;
                for(int i=0;i<map.size();i++) {
                    //System.out.println(i+" "+Entities.size()+" "+id+" "+Entities.get(i).getId());
                    if(map.get(i).getId()==id){
                        t=1;
                        break;
                    }
                }
                //System.out.println(t);
                if(t==0)
                    break;
            }
        return id;
    }
}
