package game;

import game.controller.InputManager;
import game.entity.*;
import game.gui.Animation;
import game.gui.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.image.BufferedImage;
import java.util.*;

import static game.Constants.CANVAS_HEIGHT;
import static game.Constants.CANVAS_WIDTH;

public class GameLoop {
    public static final List<Color> constColor = new ArrayList<Color>() {
        private static final long serialVersionUID = 1L;
        {
            add(Color.WHITE);
            add(Color.YELLOW);
            add(Color.BLUE);
            add(Color.GREEN);
            add(Color.GREY);
            add(Color.BLACK);
            add(Color.RED);
        }
    };
    private static double currentGameTime;
    private static double oldGameTime;
    private static double deltaTime;
    private final static long startNanoTime = System.nanoTime();
//    private static List<Entity> entities;
    public static double getCurrentGameTime() {
        return currentGameTime;
    }

    public static double getDeltaTime() {
        return deltaTime*100;
    }

    public static void start(GraphicsContext gc, Scene scene, ClientLogic client) {
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                oldGameTime = currentGameTime;
                currentGameTime = (currentNanoTime - startNanoTime) / 1000000000.0;
                deltaTime = currentGameTime - oldGameTime;
                gc.clearRect(0,0,CANVAS_WIDTH,CANVAS_HEIGHT);
                render(client, gc);

                drawScoreboard(client, gc);

            }
        }.start();
    }

//    public static void update(Scene scene, ClientLogic client) {
//        InputManager.handlePlayerMovements(scene,client);
//    }
/**/
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
                return 0; //相等为0
            }
        }); // 按生命值排序);
        int everyHeight = 20;
        int height = (players.size()+client.diePlayer.size()+1)*everyHeight+40;
        int i = 1;
        gc.setFill(Color.rgb(242,93,142,0.5));
        gc.setStroke(Color.rgb(242,93,142,0.9));
        gc.fillRoundRect(CANVAS_WIDTH-200, 0, 200, height, 10, 10);
        gc.strokeRoundRect(CANVAS_WIDTH-200, 0, 200, height, 10, 10);
        gc.setFont(new Font("SERIF", 16));
        gc.strokeText("name       HEALTH",CANVAS_WIDTH-170 ,30);
        for(Player p :players){
            gc.setStroke(constColor.get( p.id%constColor.size()));
            gc.strokeText(p.name+"       "+p.getHealth() ,CANVAS_WIDTH-170 ,i*everyHeight+30);
            i++;
        }
        for(int n = client.diePlayer.size();n>0;n--){
            gc.setStroke(constColor.get( client.diePlayer.get(n-1).id%constColor.size()));
            gc.strokeText(client.diePlayer.get(n-1).name+"       "+client.diePlayer.get(n-1).getHealth() ,CANVAS_WIDTH-170 ,i*everyHeight+30);
            i++;
        }

    }


    public static void render(ClientLogic client, GraphicsContext gc) {
        Sprite currentSprite = null;

        for (Entity e : client.getEntities()) {

            switch (e.type) {

                case "Player":
                    currentSprite = new Sprite(e, Renderer.tank, ((Player) e).getWidth(), ((Player) e).getHeight(), 1);
                    Renderer.playAnimation(currentSprite, (IRectangularEntity) e);

                    //drawCorners(gc, ((IRectangularEntity) e).getCorners(), Color.GREEN);
                    gc.setFont(new Font("SERIF", 12));
                    gc.strokeText(""+((Player) e).name,e.getPosition().getX() -14 ,e.getPosition().getY() - 42);

                    double health = (((Player) e).getHealth()/1.6);
                    if(health < 20) {
                        gc.setFill(Color.RED);
                    } else if(health < 40) {
                        gc.setFill(Color.YELLOW);
                    } else if(health > 40){
                        gc.setFill(Color.DARKGREEN);
                    }
                    gc.fillRoundRect(e.getPosition().getX()-30 ,e.getPosition().getY() -37 ,health, 6,3,4);
                    break;
                case "Wall":
                    currentSprite = new Sprite(e, Renderer.wall, ((Wall) e).getWidth(), ((Wall) e).getHeight(), 1);
                    Renderer.playAnimation(currentSprite, (IRectangularEntity) e);
                    //drawCorners(gc, ((IRectangularEntity) e).getCorners(), Color.RED);
                    gc.setFill(Color.BLACK);
                    gc.getPixelWriter();

                    //gc.fillRect(e.getPosition().getX(),e.getPosition().getY(),((Wall) e).getWidth(), ((Wall) e).getHeight());

                    break;
                case "Bullet":
                    currentSprite = new Sprite(e, Renderer.bullet, ((Bullet) e).getWidth(), ((Bullet) e).getHeight(), 2);
                    Renderer.playAnimation(currentSprite, (IRectangularEntity) e);
                    break;
            }


        }

        Player currentPlayer = (Player) client.getEntityByID(client.id);
        gc.setFill(Color.BLACK);
        if(currentPlayer!=null) {
            gc.fillText("x: " + currentPlayer.getPosition().getX(), 50,20);
            gc.fillText("y: " + currentPlayer.getPosition().getY(), 50,40);
        } else {
            gc.setFill(Color.BLACK);
//            System.out.println(Font.getFontNames());
            gc.setFont(new Font("Press Start 2P", 80));
            gc.fillText("GAME OVER", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
//            gc.drawImage(Renderer.gameOver,CANVAS_WIDTH/3.2, CANVAS_HEIGHT/2.2);
        }
        
    }
    
    private static void drawCorners(GraphicsContext gc,Point[] corners, Color colour) {
    	gc.setFill(colour);
    	gc.fillRect(corners[0].getX(), corners[0].getY(), 3, 3);
        gc.fillRect(corners[1].getX(), corners[1].getY(), 3, 3);
        gc.fillRect(corners[2].getX(), corners[2].getY(), 3, 3);
        gc.fillRect(corners[3].getX(), corners[3].getY(), 3, 3);
    }


}
