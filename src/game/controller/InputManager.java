package game.controller;

import game.ClientLogic;
import game.entity.Player;
import goldenaxe.network.client.ClientSender;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager {

    public static void handlePlayerMovements(Scene s, ClientLogic c) {
        s.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                switch(e.getCode()) {
                    case W:
                    case UP:
                                c.sendCommands("Forward");
                                break;
                    case S:
                    case DOWN:
                                c.sendCommands("Backward");
                                break;
                    case D:
                    case RIGHT:
                                c.sendCommands("RotateRight");
                                break;
                    case A:
                    case LEFT:
                                c.sendCommands("RotateLeft");
                                break;
                    case SPACE:
                                c.sendCommands("Shoot");
                                break;

                }

            }
        });
    }
}
