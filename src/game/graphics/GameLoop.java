package game.graphics;

import game.ClientLogic;
import game.Main;
import game.entity.*;
import game.maps.Map;
import javafx.animation.AnimationTimer;
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

    /** Boolean value that tracks when the game loop is active */
    public static boolean isRunning = false;
    /** The animation timer that runs constantly and renders the game */
    private static AnimationTimer timer = null;
    /** The current game time */
    private static double currentGameTime;
    /** The old game time */
    private static double oldGameTime;
    /** The delta time */
    private static double deltaTime;
    /** The start time */
    private final static long startNanoTime = System.nanoTime();
    /** The counter that tracks the time passed while your player is null */
    private static int count = 0;
    /** The game Map */
    private static Map map=new Map();
    /** Global graphics context to make it easily */
    private static GraphicsContext gc2;

    /**
     * Starts the game loop and renders the game constantly using an animation timer
     * @param gc The graphics context the rendering happens in
     * @param client The client that holds information about the game
     */
    public static void start(GraphicsContext gc, ClientLogic client) {
        // Initialise the Map
        map.initMap(Main.c1.mapID);
        gc2 = gc;


        // Define what happens when the timer is running
        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                oldGameTime = currentGameTime;
                currentGameTime = (currentNanoTime - startNanoTime) / 1000000000.0;
                deltaTime = currentGameTime - oldGameTime;
                gc.clearRect(0,0,CANVAS_WIDTH,CANVAS_HEIGHT);
                // If the entities list is empty in the game loop it means the game is not loaded
                if(client.getEntities().isEmpty()) {
                    // Display a loading message while the user waits for the game to load
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font("Press Start 2P", 40));
                    gc.fillText("LOADING...", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.3);
                    // Display the return to menu button in case the user wants to quit
                    GameWindow.toggleBtn(true);

                } else {
                    // If the entities have loaded, hide the return to menu button
                    GameWindow.toggleBtn(false);
                    // Start rendering the game
                    render(client, gc);
                    // Draw the player score board on the screen
                    drawScoreboard(client, gc);
                }
            }
        };
        // Start the timer that runs all throughout the game
        timer.start();
        // Set the flag that tracks if the game loop is running to true
        isRunning = true;
    }

    /**
     * Stops the game loop from running
     */
    public static void stop()  {
        // Stop the timer
        timer.stop();
        // Set the flag to false
        isRunning = false;
    }

    /**
     * Draws the score board in the game window
     * @param client The client object that hold the information
     * @param gc The graphics context the game window is drawn in
     */
    public static void drawScoreboard(ClientLogic client,GraphicsContext gc) {
        // Add all players to the player list
        List<Player> players = new ArrayList<>();
        for (Entity e : client.getEntities()) {
            if(e.type.equals("Player")){
                if(((Player) e).getHealth()>0){
                    players.add((Player)e);
                }
            }
        }
        // Sort the players list by their HP
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
        });

        // Draw the board itself
        int everyHeight = 20;
        int height = (players.size() + client.diePlayer.size() + 1) * everyHeight + 40;
        int i = 1;
        gc.setFill(Color.rgb(150,150,150,0.3));
        gc.setStroke(Color.rgb(0,0,0,0.5));
        gc.fillRoundRect(CANVAS_WIDTH-200, 0, 200, height, 10, 10);
        gc.strokeRoundRect(CANVAS_WIDTH-200, 0, 200, height, 10, 10);
        gc.setFont(new Font("Press Start 2P", 9));

        // Draw the content on the board
        gc.setFill(Color.BLACK);
        gc.fillText(" name      HEALTH",CANVAS_WIDTH-170 ,30);
        // Spaces string to use for nice printing
        String space = "                       ";

        // For all players, draw their name and health
        for(Player p : players){
            if(p.id == client.id) {
                gc.setFill(Color.GREEN);
            } else {
                gc.setFill(Color.DARKRED);
            }

            // If the name is composed, only draw the second name
            String name;
            if(p.name.split(" ").length == 2) {
                String[] temp = p.name.split(" ");
                name = temp[1];
            } else {
                name = p.name;
            }
            // Print the player name, 12 minus the length of the name spaces and the health
            // This way, all names and HPs are drawn neatly
            gc.fillText(name + space.substring(0,12-name.length()) + p.getHealth() ,CANVAS_WIDTH-170 ,i*everyHeight+30);
            i++;
        }
        // For all players, draw their name and health
        for(int n = client.diePlayer.size(); n > 0; n--){
            if(client.diePlayer.get(n-1).id == client.id) {
                gc.setFill(Color.GREEN);
            } else {
                gc.setFill(Color.DARKRED);
            }

            // If the name is composed, only draw the second name
            String name;
            if(client.diePlayer.get(n-1).name.split(" ").length == 2) {
                String[] temp = client.diePlayer.get(n-1).name.split(" ");
                name = temp[1];
            } else {
                name = client.diePlayer.get(n-1).name;
            }
            // Print the player name, 12 minus the length of the name spaces and the health
            // This way, all names and HPs are drawn neatly
            gc.fillText(name + space.substring(0,12 - name.length()) + client.diePlayer.get(n-1).getHealth() ,CANVAS_WIDTH-170 ,i*everyHeight+30);
            i++;
        }
    }


    /**
     * Draws all the entities on the screen as they move
     * @param client The client that holds the information
     * @param gc The graphics context that the game window is drawn in
     */
    public static void render(ClientLogic client, GraphicsContext gc) {
        // Get the player of the current user
        Player currentPlayer = (Player) client.getEntityByID(client.id);
        // Create a count that track the number of players alive
        int playerCount = 0;

        // Draw the map
        for(Entity e: map.getMap()) {
            e.draw();
        }

        // Draw the moving entities
        for (Entity e : client.getEntities()) {
            // Draw all entities that are not tanks
            if(!e.type.equals("Player")) {
                e.draw();

            // For the tanks, draw the tank itself
            // but also the player life bar and name
            } else {
                e.draw();

                // Count the players
                playerCount++;

                // Draw the player names
                // Draw your player name with green
                // and opponents names with red
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

                // If the player name is composed, only draw the second name
                String name;
                if(((Player)e).name.split(" ").length == 2) {
                    String[] temp = ((Player)e).name.split(" ");
                    name = temp[1];
                } else {
                    name = ((Player)e).name;
                }

                // Compute the x coordinate the name should be drawn at
                // It should be drawn slightly above and in the middle of the health bar that has length 80
                float xpos = e.getPosition().getX() - 38 + (80 - name.length()*name.length())/2;
                if(name.equals("YOU")) {
                    gc.fillText("" + name, e.getPosition().getX() - 11, e.getPosition().getY() - 50);
                } else {
                    gc.fillText("" + name, xpos, e.getPosition().getY() - 50);
                }

                // Draw the health bar
                double health = (((Player) e).getHealth() / 1.25);
                // Change the health bar color according to the amount of health of the player
                if (health < 30) {
                    gc.setFill(Color.RED);
                } else if (health < 60) {
                    gc.setFill(Color.YELLOW);
                } else if (health > 60) {
                    gc.setFill(Color.LIMEGREEN);
                }
                // The health bar should be drawn above the tank, centered the same way as the tank
                if(health > 80) {
                    gc.fillRoundRect(e.getPosition().getX() - 38, e.getPosition().getY() - 45, 80, 6, 3, 4);
                } else {
                    gc.fillRoundRect(e.getPosition().getX() - 38, e.getPosition().getY() - 45, health, 6, 3, 4);
                }

                // Draw the edge of the health bar
                gc.setStroke(Color.BLACK);
                gc.strokeRoundRect(e.getPosition().getX() - 38, e.getPosition().getY() - 45, 80, 6, 3, 4);
            }
        }

        // If the user disconnects from the server in the middle of the game
        if (!GameWindow.connection) {
            gc2.setFill(Color.BLACK);
            gc2.setFont(new Font("Press Start 2P", 50));
            // Display a message on the screen
            gc2.fillText("GAME DISCONNECTED...", CANVAS_WIDTH/6, CANVAS_HEIGHT/2.7);
            gc2.fillText("WAITING TO RECONNECT", CANVAS_WIDTH/6, CANVAS_HEIGHT/2.2);
            // Display the return to menu button in case the player wants to quit
            GameWindow.toggleBtn(true);
        // If the user is connected
        } else {
            // If your player becomes null
            if(currentPlayer == null) {
                // Increase the count that tracks how long your player has been null
                count++;
                // If you player remains null for 5 seconds, it's considered dead
                if (count > 50) {
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font("Press Start 2P", 80));
                    // Display the game over message on screen
                    gc.fillText("GAME OVER", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
                    // Display the return to menu button so the player can quit the game
                    GameWindow.toggleBtn(true);
                    // Play the game over sound effect just once
                    if(GameWindow.audioCount <= 0) {
                        Main.audioPlayer.playLoseSound();
                        GameWindow.audioCount++;
                    }
                }

            // If your player is still alive
            } else {
                // Draw the x and y coordinates of your player in the upper left hand corner of the screen
                gc.setFill(Color.BLACK);
                gc.fillText("x: " + currentPlayer.getPosition().getX(), 110,25);
                gc.fillText("y: " + currentPlayer.getPosition().getY(), 110,45);
                // If your player is still alive and the number of players left alive in the game is 1
                // It means you won
                if (playerCount == 1) {
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font("Press Start 2P", 80));
                    // Display the winning message on screen
                    gc.fillText("YOU WON!", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
                    // Display the return to menu button so the player can quit
                    GameWindow.toggleBtn(true);
                    // Play the won game sound effect
                    if(GameWindow.audioCount <= 0) {
                        Main.audioPlayer.playWinSound();
                        GameWindow.audioCount++;
                    }
                    // Because you won
                    // Increase the number of player points in the database
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
                // If you are still alive and the number of players alive is more than 1
                // The game is still going, so do not display the return to menu button
                } else {
                    GameWindow.toggleBtn(false);
                }
            }
        }

        // Draw the shop panel
        // If the game is a singleplayer game
        if(client.singleFlag){
            // And if the client requests to open the shop
            if (client.mallShow){
                // Draw the shop image
                Renderer.drawMallPanel(client.user.getPoint());
            }else{
                // Otherwise, display the message that lets the user know how to open the shop
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

    /**
     * Draws the corners of the tank sprite
     * @param gc
     * @param corners
     * @param colour
     */
    private static void drawCorners(GraphicsContext gc,Point[] corners, Color colour) {
    	gc.setFill(colour);
    	gc.fillRect(corners[0].getX(), corners[0].getY(), 3, 3);
        gc.fillRect(corners[1].getX(), corners[1].getY(), 3, 3);
        gc.fillRect(corners[2].getX(), corners[2].getY(), 3, 3);
        gc.fillRect(corners[3].getX(), corners[3].getY(), 3, 3);
    }


    /**
     * Sets the connection flag to false
     */
    public static void disconnected() {
        GameWindow.connection  = false;
    }

    /**
     * Sets the connection flag to true
     */
    public static void connected(){
        GameWindow.connection = true;
    }
}
