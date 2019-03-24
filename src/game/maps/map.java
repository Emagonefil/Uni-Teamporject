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
        map.clear();
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
            map.add(new Wall(40, 40, new Point(120f, 120f),getSpareId()));
            map.add(new Wall(40, 40, new Point(120f, 160f),getSpareId()));
            map.add(new Wall(40, 40, new Point(120f, 200f),getSpareId()));
            map.add(new Wall(40, 40, new Point(120f, 240f),getSpareId()));
            map.add(new Wall(40, 40, new Point(160f, 120f),getSpareId()));
            map.add(new Wall(40, 40, new Point(200f, 120f),getSpareId()));
            map.add(new Wall(40, 40, new Point(240f, 120f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1280f, 120f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1280f, 160f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1280f, 200f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1280f, 240f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1240f, 120f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1200f, 120f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1160f, 120f),getSpareId()));
            map.add(new Wall(40, 40, new Point(120f, 680f),getSpareId()));
            map.add(new Wall(40, 40, new Point(120f, 640f),getSpareId()));
            map.add(new Wall(40, 40, new Point(120f, 600f),getSpareId()));
            map.add(new Wall(40, 40, new Point(120f, 560f),getSpareId()));
            map.add(new Wall(40, 40, new Point(160f, 680f),getSpareId()));
            map.add(new Wall(40, 40, new Point(200f, 680f),getSpareId()));
            map.add(new Wall(40, 40, new Point(240f, 680f),getSpareId()));
            map.add(new Wall(40, 40, new Point(160f, 680f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1280f, 680f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1240f, 680f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1200f, 680f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1160f, 680f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1280f, 640f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1280f, 600f),getSpareId()));
            map.add(new Wall(40, 40, new Point(1280f, 560f),getSpareId()));
            map.add(new Wall(40, 40, new Point(680f, 380f),getSpareId()));
            map.add(new Wall(40, 40, new Point(640f, 380f),getSpareId()));
            map.add(new Wall(40, 40, new Point(600f, 380f),getSpareId()));
            map.add(new Wall(40, 40, new Point(560f, 380f),getSpareId()));
            map.add(new Wall(40, 40, new Point(520f, 380f),getSpareId()));
            map.add(new Wall(40, 40, new Point(680f, 420f),getSpareId()));
            map.add(new Wall(40, 40, new Point(680f, 460f),getSpareId()));
            map.add(new Wall(40, 40, new Point(680f, 500f),getSpareId()));
            map.add(new Wall(40, 40, new Point(720f, 380f),getSpareId()));
            map.add(new Wall(40, 40, new Point(760f, 380f),getSpareId()));
            map.add(new Wall(40, 40, new Point(800f, 380f),getSpareId()));
            map.add(new Wall(40, 40, new Point(840f, 380f),getSpareId()));
            map.add(new Wall(40, 40, new Point(680f, 340f),getSpareId()));
            map.add(new Wall(40, 40, new Point(680f, 300f),getSpareId()));
            map.add(new Wall(40, 40, new Point(680f, 260f),getSpareId()));
            

        }
        else if(choice==2){
            float width = 1500;
            float height = 950;
            float height_dif = ((height-20)-(height/2))/4;
            float width_dif = ((width-20)-(width/2))/4;

            //center top
            map.add(new Wall(40,40,new Point(width/2,20f),getSpareId()));
            //center bottom
            map.add(new Wall(40,40,new Point(width/2,height-20),getSpareId()));
            //center left
            map.add(new Wall(40,40,new Point(20f,height/2),getSpareId()));
            //center right
            map.add(new Wall(40,40,new Point(width-20,height/2),getSpareId()));

            for(int i=1;i<4;i++){
                //top left
                map.add(new Wall(40,40,new Point(20+(i*width_dif),(height/2)-(i*height_dif)),getSpareId()));
                //top right
                map.add(new Wall(40,40,new Point((width/2)+(i*width_dif),20+(i*height_dif)),getSpareId()));
                //bottom left
                map.add(new Wall(40,40,new Point(20+(i*width_dif),(height/2)+(i*height_dif)),getSpareId()));
                //bottom right
                map.add(new Wall(40,40,new Point((width/2)+(i*width_dif),height-(i*height_dif)),getSpareId()));
            }

            //inner center top
            map.add(new Wall(40,40,new Point(width/2,20+(1.5f*height_dif)),getSpareId()));
            //inner center bottom
            map.add(new Wall(40,40,new Point(width/2,height-20-(1.5f*height_dif)),getSpareId()));
            //inner center left
            map.add(new Wall(40,40,new Point(20f+(1.5f*width_dif),height/2),getSpareId()));
            //inner center right
            map.add(new Wall(40,40,new Point(width-20-(1.5f*width_dif),height/2),getSpareId()));

            map.add(new Wall(40,40,new Point((width/2)-width_dif,(height/2)-height_dif),getSpareId()));
            map.add(new Wall(40,40,new Point((width/2)+width_dif,(height/2)-height_dif),getSpareId()));
            map.add(new Wall(40,40,new Point((width/2)-width_dif,(height/2)+height_dif),getSpareId()));
            map.add(new Wall(40,40,new Point((width/2)+width_dif,(height/2)+height_dif),getSpareId()));

            //center
            map.add(new Wall(40,40,new Point(width/2,height/2),getSpareId()));
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
