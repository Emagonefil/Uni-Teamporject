package game.graphics;

import com.sun.media.jfxmedia.events.PlayerEvent;
import game.ClientLogic;
import game.Main;
import game.audio.AudioPlayer;
import game.dao.UserDao;
import game.entity.*;
import game.maps.map;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import java.util.*;

import static game.Constants.CANVAS_HEIGHT;
import static game.Constants.CANVAS_WIDTH;
import static game.Main.ud;

/**
 * The Game Loop runs constantly during a game. It continuously redraws the game entities
 * to display their movements and actions.
 */
public class GameLoop {

    public static boolean isRunning = false;
//    public static final List<Color> constColor = new ArrayList<Color>() {
//        private static final long serialVersionUID = 1L;
//        {
//            add(Color.WHITE);
//            add(Color.YELLOW);
//            add(Color.DARKBLUE);
//            add(Color.GREEN);
//            add(Color.DARKORANGE);
//            add(Color.BLACK);
//            add(Color.RED);
//        }
//    };
    private static AnimationTimer timer = null;
    private static double currentGameTime;
    private static double oldGameTime;
    private static double deltaTime;
    private final static long startNanoTime = System.nanoTime();
    private static int count = 0;
    private static map map=new map();
    public static double getCurrentGameTime() {
        return currentGameTime;
    }
    private static GraphicsContext gc2;

    public static double getDeltaTime() {
        return deltaTime*100;
    }

    public static void start(GraphicsContext gc, Scene scene, ClientLogic client) {
        map.initMap(Main.c1.mapID);
        gc2 = gc;
        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                oldGameTime = currentGameTime;
                currentGameTime = (currentNanoTime - startNanoTime) / 1000000000.0;
                deltaTime = currentGameTime - oldGameTime;
                gc.clearRect(0,0,CANVAS_WIDTH,CANVAS_HEIGHT);
                Player currentPlayer = (Player) client.getEntityByID(client.id);
                if(client.getEntities().isEmpty()) {
//                    System.out.println("THERE ARE NO ENTITIES LOADED IN client.getEntities()");
                    gc.setFill(Color.BLACK);

                    gc.setFont(new Font("Press Start 2P", 40));
                    gc.fillText("Loading...", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.5);
                    gc.fillText("No entities loaded", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
                    GameWindow.toggleBtn(true);

                } else {
                    GameWindow.toggleBtn(false);
                    render(client, gc);
                    drawScoreboard(client, gc);
                }
            }
        };
        timer.start();
        isRunning = true;
    }

    public static void stop()  {
        timer.stop();
        isRunning = false;
    }

    public static void drawScoreboard(ClientLogic client,GraphicsContext gc) {
        List<Player> players = new ArrayList<>();
        for (Entity e : client.getEntities()) {
            if(e.type.equals("Player")){
                if(((Player) e).getHealth()>0){
                    players.add((Player)e);
                }
            }
        }
        Collections.sort(players,new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int diff = p2.getHealth() - p1.getHealth();
                if (diff > 0) {
                    return 1;
                }else if (diff < 0) {
                    return -1;
                }
                return 0;
            }
        }); // Sort by life value;
        int everyHeight = 20;
        int height = (players.size()+client.diePlayer.size()+1)*everyHeight+40;
        int i = 1;
        gc.setFill(Color.rgb(150,150,150,0.3));
        gc.setStroke(Color.rgb(0,0,0,0.5));
        gc.fillRoundRect(CANVAS_WIDTH-200, 0, 200, height, 10, 10);
        gc.strokeRoundRect(CANVAS_WIDTH-200, 0, 200, height, 10, 10);
        gc.setFont(new Font("Press Start 2P", 9));
        gc.setFill(Color.BLACK);
        gc.fillText(" name      HEALTH",CANVAS_WIDTH-170 ,30);
        String space = "                       ";
        for(Player p :players){
            if(p.id == client.id) {
                gc.setFill(Color.GREEN);
            } else {
                gc.setFill(Color.DARKRED);
            }
            String name;
            if(p.name.split(" ").length == 2) {
                String[] temp = p.name.split(" ");
                name = temp[1];
            } else {
                name = p.name;
            }
            gc.fillText(name + space.substring(0,12-name.length()) + p.getHealth() ,CANVAS_WIDTH-170 ,i*everyHeight+30);
            i++;
        }
        for(int n = client.diePlayer.size();n>0;n--){
            if(client.diePlayer.get(n-1).id == client.id) {
                gc.setFill(Color.GREEN);
            } else {
                gc.setFill(Color.DARKRED);
            }
            String name;
            if(client.diePlayer.get(n-1).name.split(" ").length == 2) {
                String[] temp = client.diePlayer.get(n-1).name.split(" ");
                name = temp[1];
            } else {
                name = client.diePlayer.get(n-1).name;
            }
            gc.fillText(name + space.substring(0,12 - name.length()) + client.diePlayer.get(n-1).getHealth() ,CANVAS_WIDTH-170 ,i*everyHeight+30);
            i++;
        }

    }


    public static void render(ClientLogic client, GraphicsContext gc) {
        Player currentPlayer = (Player) client.getEntityByID(client.id);
        int playerCount = 0;
        for(Entity e: map.getMap()) {
            e.draw();
        }

        for (Entity e : client.getEntities()) {
            if(!e.type.equals("Player")) {
                e.draw();
            } else {
                e.draw();
                playerCount++;
                //drawCorners(gc, ((IRectangularEntity) e).getCorners(), Color.GREEN);
//                gc.setFont(new Font("SERIF", 12));
                gc.setFont(new Font("Press Start 2P", 8));
                if(currentPlayer != null) {
                    if(e.id == currentPlayer.id) {
                        gc.setFill(Color.GREEN);
                    }else {
                        gc.setFill(Color.DARKRED);
                    }
                } else {
                    gc.setFill(Color.DARKRED);
                }

                String name;
                if(((Player)e).name.split(" ").length == 2) {
                    String[] temp = ((Player)e).name.split(" ");
                    name = temp[1];
                } else {
                    name = ((Player)e).name;
                }

                float xpos = e.getPosition().getX() - 38 + (80 - name.length()*name.length())/2;
                if(name.equals("YOU")) {
                    gc.fillText("" + name, e.getPosition().getX() - 11, e.getPosition().getY() - 50);
                } else {
                    gc.fillText("" + name, xpos, e.getPosition().getY() - 50);
                }


//                System.out.println(((Player)e).name);
                double health = (((Player) e).getHealth() / 1.25);
                if (health < 30) {
                    gc.setFill(Color.RED);
                } else if (health < 60) {
                    gc.setFill(Color.YELLOW);
                } else if (health > 60) {
                    gc.setFill(Color.LIMEGREEN);
                }
                if(health > 80) {
                    gc.fillRoundRect(e.getPosition().getX() - 38, e.getPosition().getY() - 45, 80, 6, 3, 4);
                } else {
                    gc.fillRoundRect(e.getPosition().getX() - 38, e.getPosition().getY() - 45, health, 6, 3, 4);
                }

                gc.setStroke(Color.BLACK);
                gc.strokeRoundRect(e.getPosition().getX() - 38, e.getPosition().getY() - 45, 80, 6, 3, 4);
            }
        }

        if (!GameWindow.connection) {
            gc2.setFill(Color.BLACK);
            gc2.setFont(new Font("Press Start 2P", 80));
            gc2.fillText("GAME DISCONNECTED...", CANVAS_WIDTH/4, CANVAS_HEIGHT/1.6);
            gc2.fillText("WAITING TO RECONNECT", CANVAS_WIDTH/4, CANVAS_HEIGHT/1.9);
            GameWindow.toggleBtn(true);
        } else {
            if(currentPlayer == null) {
                count++;
                if (count > 50) {
                    gc.setFill(Color.BLACK);
//            System.out.println(Font.getFontNames());
                    gc.setFont(new Font("Press Start 2P", 80));
                    gc.fillText("GAME OVER", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
                    GameWindow.toggleBtn(true);
                    if(GameWindow.audioCount <= 0) {
                        Main.audioPlayer.playLoseSound();
                        GameWindow.audioCount++;
                    }
                }

            } else {
                gc.setFill(Color.BLACK);
                gc.fillText("x: " + currentPlayer.getPosition().getX(), 110,25);
                gc.fillText("y: " + currentPlayer.getPosition().getY(), 110,45);
                if (playerCount == 1) {
                    gc.setFill(Color.BLACK);
//            System.out.println(Font.getFontNames());
                    gc.setFont(new Font("Press Start 2P", 80));
                    gc.fillText("You Won!", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
                    GameWindow.toggleBtn(true);
                    if(GameWindow.audioCount <= 0) {
                        Main.audioPlayer.playWinSound();
                        GameWindow.audioCount++;
                    }
                    //increase points
                    if(client.singleFlag){
                        if(client.addpointFlag){
                            client.user.setPoint(client.user.getPoint()+5);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ud.userUpdatePoint(client.user);
                                }
                            }).start();

                            client.addpointFlag=false;
                        }
                    }
                } else {
                    GameWindow.toggleBtn(false);
                }
            }
        }




        //draw shop panel
        if(client.singleFlag){
            if (client.mallShow){
                Renderer.drawMallPanel(client.user.getPoint());
            }else{
                gc.setFont(new Font("Press Start 2P", 20));
                gc.setFill(Color.BLACK);
                gc.fillText("Press ", (CANVAS_WIDTH/2)-250, 50);
                gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                gc.setFont(new Font("Press Start 2P", 30));
                gc.fillText("'K'", (CANVAS_WIDTH/2)-150, 50);
                gc.setFont(new Font("Press Start 2P", 20));
                gc.setFill(Color.BLACK);
                gc.fillText("to access the shop", (CANVAS_WIDTH/2)-70, 50);
            }
        }

    }

    private static void drawCorners(GraphicsContext gc,Point[] corners, Color colour) {
    	gc.setFill(colour);
    	gc.fillRect(corners[0].getX(), corners[0].getY(), 3, 3);
        gc.fillRect(corners[1].getX(), corners[1].getY(), 3, 3);
        gc.fillRect(corners[2].getX(), corners[2].getY(), 3, 3);
        gc.fillRect(corners[3].getX(), corners[3].getY(), 3, 3);
    }


    public static void disconnected() {
        GameWindow.connection  = false;
    }

    public static void connected(){
        GameWindow.connection = true;
    }

//    private static reconnect() {
//        gc2.
//    }


}
