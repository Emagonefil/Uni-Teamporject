package game;

import game.controller.InputManager;
import game.controller.Login;
import game.entity.*;
import game.gui.Animation;
import game.gui.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;

import static game.Constants.CANVAS_HEIGHT;
import static game.Constants.CANVAS_WIDTH;

public class GameLoop {
    public static final List<Color> constColor = new ArrayList<Color>() {
        private static final long serialVersionUID = 1L;
        {
            add(Color.WHITE);
            add(Color.YELLOW);
            add(Color.DARKBLUE);
            add(Color.GREEN);
            add(Color.DARKORANGE);
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
                if(client.getEntities().isEmpty()) {
                    System.out.println("THERE ARE NO ENTITIES LOADED IN client.getEntities()");
                    gc.setFill(Color.BLACK);

                    gc.setFont(new Font("Press Start 2P", 40));
                    gc.fillText("Loading...", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.23);
                    gc.fillText("No entities loaded", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2);
                } else {
                    render(client, gc);
                }

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
        Sprite currentSprite = null;
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
                gc.fillRoundRect(e.getPosition().getX() - 38, e.getPosition().getY() - 45, health, 6, 3, 4);
                gc.setStroke(Color.BLACK);
                gc.strokeRoundRect(e.getPosition().getX() - 38, e.getPosition().getY() - 45, 80, 6, 3, 4);
            }
        }


        gc.setFill(Color.BLACK);
        if(currentPlayer!=null) {
            gc.fillText("x: " + currentPlayer.getPosition().getX(), 50,20);
            gc.fillText("y: " + currentPlayer.getPosition().getY(), 50,40);
            if (playerCount == 1) {
                gc.setFill(Color.BLACK);
//            System.out.println(Font.getFontNames());
                gc.setFont(new Font("Press Start 2P", 80));
                gc.fillText("You Won!", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
                backToMenu(gc);
            }
        } else {
            gc.setFill(Color.BLACK);
//            System.out.println(Font.getFontNames());
            gc.setFont(new Font("Press Start 2P", 80));
            gc.fillText("GAME OVER", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
            backToMenu(gc);
//            gc.drawImage(Renderer.gameOver,CANVAS_WIDTH/3.2, CANVAS_HEIGHT/2.2);
        }
        
    }

    public static void backToMenu(GraphicsContext gc){
        gc.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Main.isRunning = false;
                try {
                    Main.mainStage.getScene().setRoot(getMenuScene());
                } catch(Exception e) {
                    System.out.println("Exception when going back to menu");
                    e.printStackTrace();
                }
            }
        });
    }

    public static Parent getMenuScene() throws java.io.IOException {
        String sceneFile = "gui/menu3.fxml";
        Parent root = null;
        URL url  = null;
        try
        {
            url  = Main.class.getResource( sceneFile );
            root = FXMLLoader.load( url );
//			System.out.println( "  fxmlResource = " + sceneFile );
        }
        catch ( Exception ex )
        {
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + url );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            throw ex;
        }
        return root;
    }

    private static void drawCorners(GraphicsContext gc,Point[] corners, Color colour) {
    	gc.setFill(colour);
    	gc.fillRect(corners[0].getX(), corners[0].getY(), 3, 3);
        gc.fillRect(corners[1].getX(), corners[1].getY(), 3, 3);
        gc.fillRect(corners[2].getX(), corners[2].getY(), 3, 3);
        gc.fillRect(corners[3].getX(), corners[3].getY(), 3, 3);
    }


}
