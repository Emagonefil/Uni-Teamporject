package game.controller;

import game.ClientLogic;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;


public class InputManager {

    public static void handlePlayerMovements(Scene scene, ClientLogic sender) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case W:
                    case UP:    sender.sendCommands("Forward");

                                break;

                    case S:
                    case DOWN:  sender.sendCommands("Backward"); break;

                    case D:
                    case RIGHT: sender.sendCommands("RotateRight"); break;

                    case A:
                    case LEFT: sender.sendCommands("RotateLeft"); break;

                    case SPACE: sender.sendCommands("Shoot"); break;

                }
            }
        });
    }
}
