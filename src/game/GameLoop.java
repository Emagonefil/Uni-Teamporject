package game;

import com.jfoenix.controls.JFXButton;
import game.controller.InputManager;
import game.controller.Login;
import game.dao.UserDao;
import game.entity.*;
import game.gui.Animation;
import game.gui.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;

import static game.Constants.CANVAS_HEIGHT;
import static game.Constants.CANVAS_WIDTH;

public class GameLoop {


    private static AnimationTimer timer = null;
    private static double currentGameTime;
    private static double oldGameTime;
    private static double deltaTime;
    private final static long startNanoTime = System.nanoTime();
    private static int count = 0;
    private static boolean btnAdded = false;
    private static boolean mallPanelShowFlag = false;
//    private static List<Entity> entities;
    public static double getCurrentGameTime() {
        return currentGameTime;
    }

    public static double getDeltaTime() {
        return deltaTime*100;
    }

    public static void start(GraphicsContext gc, Scene scene, ClientLogic client) {
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
    }

    public static void stop()  {
        timer.stop();
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
                return 0; //equare -> zero
            }
        }); // 按生命值排序);
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
                gc.setFill(Color.YELLOW);
            } else {
                gc.setFill(Color.DARKRED);
            }
            gc.fillText(p.name+ space.substring(0,12-p.name.length()) +p.getHealth() ,CANVAS_WIDTH-170 ,i*everyHeight+30);
            i++;
        }
        for(int n = client.diePlayer.size();n>0;n--){
            if(client.diePlayer.get(n-1).id == client.id) {
                gc.setFill(Color.YELLOW);
            } else {
                gc.setFill(Color.DARKRED);
            }
            gc.fillText(client.diePlayer.get(n-1).name+space.substring(0,12-client.diePlayer.get(n-1).name.length())+client.diePlayer.get(n-1).getHealth() ,CANVAS_WIDTH-170 ,i*everyHeight+30);
            i++;
        }

    }


    public static void render(ClientLogic client, GraphicsContext gc) {
        Player currentPlayer = (Player) client.getEntityByID(client.id);
        int playerCount = 0;

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
                        gc.setFill(Color.YELLOW);
                    }else {
                        gc.setFill(Color.DARKRED);
                    }
                } else {
                    gc.setFill(Color.DARKRED);
                }

                float xpos = e.getPosition().getX() - 38 + (80 - ((Player) e).name.length()*(((Player) e).name.length()))/2;
                gc.fillText("" + ((Player) e).name, xpos, e.getPosition().getY() - 50);

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

        if(currentPlayer == null) {
            count++;
            if (count > 100) {
                gc.setFill(Color.BLACK);
//            System.out.println(Font.getFontNames());
                gc.setFont(new Font("Press Start 2P", 80));
                gc.fillText("GAME OVER", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
                GameWindow.toggleBtn(true);
            }

        } else {
            gc.setFill(Color.BLACK);
            gc.fillText("x: " + currentPlayer.getPosition().getX(), 50,20);
            gc.fillText("y: " + currentPlayer.getPosition().getY(), 50,40);
            if (playerCount == 1) {
                gc.setFill(Color.BLACK);
//            System.out.println(Font.getFontNames());
                gc.setFont(new Font("Press Start 2P", 80));
                gc.fillText("You Won!", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
                //increase points
                if(client.addpointFlag){
                    client.user.setPoint(client.user.getPoint()+5);
                    UserDao ud = new UserDao();
                    ud.userUpdatePoint(client.user);
                    client.addpointFlag=false;
                }
                GameWindow.toggleBtn(true);
            } else {
                GameWindow.toggleBtn(false);
            }
        }

        if (client.mallShow){
            Renderer.drawMallPanel(client.user.getPoint());

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
