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

    /**
     * get initialized map
     * @return initialized map
     */
    public List<Entity> getMap(){
        return map;
    }

    /**
     * initial the map referring to the choice number
     * @param choice to choose which map to create, 0 means create randomly
     */
    public void initMap(int choice){
        if(choice==0){
            int num=ra.nextInt(20)+20;
            for(int i=0;i<num;i++) {
                while (true) {
                    Wall w;
                    double x=ra.nextInt((int) Constants.CANVAS_WIDTH/40)*40 ;
                    double y=ra.nextInt((int)Constants.CANVAS_HEIGHT/40)*40 ;
                    w = new Wall(40, 40, new Point((float)x, (float) y));
                    int l=checkCollision(w);
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
            map.add(new Wall(40, 40, new Point((float)120, (float)120),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)120, (float)160),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)120, (float)200),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)120, (float)240),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)160, (float)120),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)200, (float)120),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)240, (float)120),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1280, (float)120),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1280, (float)160),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1280, (float)200),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1280, (float)240),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1240, (float)120),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1200, (float)120),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1160, (float)120),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)120, (float)680),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)120, (float)640),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)120, (float)600),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)120, (float)560),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)160, (float)680),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)200, (float)680),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)240, (float)680),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)160, (float)680),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1280, (float)680),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1240, (float)680),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1200, (float)680),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1160, (float)680),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1280, (float)640),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1280, (float)600),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)1280, (float)560),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)680, (float)380),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)640, (float)380),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)600, (float)380),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)560, (float)380),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)520, (float)380),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)680, (float)420),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)680, (float)460),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)680, (float)500),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)720, (float)380),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)760, (float)380),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)800, (float)380),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)840, (float)380),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)680, (float)340),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)680, (float)300),getSpareId()));
            map.add(new Wall(40, 40, new Point((float)680, (float)260),getSpareId()));
            


        }
    }

    /**
     * used to check whether there's other walls touched with this wall
     * @param e the wall that's going to be checked
     * @return if there is, return the touched wall, else return null
     */
    public int checkCollision(Wall e){
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

    /**
     *
     * @return
     */
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
