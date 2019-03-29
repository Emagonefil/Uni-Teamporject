package game.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import game.Constants;
import game.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The controller for the settings page.
 * It dictates how elements on the settings page should act.
 *
 * @author Andreea
 */
public class SettingsController {

    /** The tank model number */
    int model;
    /** The array of panes of images */
    BorderPane[] panes2;

    /** The music volume slider */
    @FXML
    private JFXSlider musicSlider;
    /** The sound effects volume slider */
    @FXML
    private JFXSlider soundSlider;
    /** The mute music button */
    @FXML
    private ToggleButton music;
    /** The mute sound effects button */
    @FXML
    private ToggleButton sound;

    /**
     * Runs once when the controller is initialisez
     */
    public void initialize() {
        // Add event handlers to the mute buttons
        Main.soundButtons(music,sound);

        // Set the value of the music slider when you first enter the page
        musicSlider.setValue((5.0f*Main.audioPlayer.getMusicVolume())/4);
        // Add event handler for the music slider
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Set the system music volume to the value selected on the slider
            Main.audioPlayer.setMusicVolume((newValue.floatValue()*4)/5);
            // If the mute music button is selected but the music volume is not 0
            if(music.isSelected() && Main.audioPlayer.getMusicVolume() != 0) {
                // Unselect the mute music button
                music.setSelected(false);
            // If the mute music button is not selected, but the music volume is 0
            } else if (!music.isSelected() && Main.audioPlayer.getMusicVolume() == 0) {
                // Select the mute music button
                music.setSelected(true);
            }

        });

        // Set the value of the sound effects slider when you first enter the page
        soundSlider.setValue((5.0f*Main.audioPlayer.getSoundEffectVolume())/4);
        // Add event handler for the sound effects slider
        soundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Set the system sound effects volume to the value selected on the slider
            Main.audioPlayer.setSoundEffectVolume((newValue.floatValue()*4)/5);
            // If the mute sound effects button is selected but the sound effects volume is not 0
            if(sound.isSelected() && Main.audioPlayer.getSoundEffectVolume() != 0) {
                // Unselect the mute sound effects button
                sound.setSelected(false);
            // If the mute sound effects button is not selected, but the sound effects volume is 0
            } else if (!sound.isSelected() && Main.audioPlayer.getSoundEffectVolume() == 0) {
                // Select the mute sound effects button
                sound.setSelected(true);
            }
        });

    }

    /**
     * Changes from the settings screen to the main menu screen
     * @param event the event that triggers this action
     */
    @FXML
    protected void back(ActionEvent event) throws Exception {
        // Load the path to the file containing the screen you want to change to
        String path = "fxml/menu3.fxml";
        // Change from your current screen to the new screen using the helper method
        Helper.changeScreen(event,SettingsController.class,path);
    }

    /**
     * Changes from the settings screen to the tank model selection screen
     * @param event The event that triggers this action
     */
    @FXML
    protected void model(ActionEvent event) throws Exception {
        // Get the primary stage the screens are displayed in
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        // Create the root node that holds the page content
        VBox box = new VBox();
        // Add styling to the page
        box.setSpacing(50);
        box.setPadding(new Insets(50));
        box.getStyleClass().add("vbox2");
        // Create the title
        Label title = new Label("CLASH OF TANKS");
        title.getStyleClass().add("title");
        title.setFont(new Font("Press Start 2P", 36));

        // Put each tank image into an image view
        ImageView v1 = new ImageView(Renderer.tank1);
        ImageView v2 = new ImageView(Renderer.tank2);
        ImageView v3 = new ImageView(Renderer.tank3);
        ImageView v4 = new ImageView(Renderer.tank4);
        ImageView v5 = new ImageView(Renderer.tank5);
        ImageView v6 = new ImageView(Renderer.tank6);
        ImageView v7 = new ImageView(Renderer.tank7);

        // Create an array of all tank image views
        ImageView[] imgs = {v1,v2,v3,v4,v5,v6,v7};

        // Wrap each tank image view into a border pane
        BorderPane b1 = new BorderPane(v1);
        BorderPane b2 = new BorderPane(v2);
        BorderPane b3 = new BorderPane(v3);
        BorderPane b4 = new BorderPane(v4);
        BorderPane b5 = new BorderPane(v5);
        BorderPane b6 = new BorderPane(v6);
        BorderPane b7 = new BorderPane(v7);

        // Create an array of all border panes
        BorderPane[] panes = {b1,b2,b3,b4,b5,b6,b7};
        panes2 = panes;

        // For all images
        for(int i = 0; i < imgs.length; i++) {
            // If the current tank is selected
            if(Main.user.getTankModel() == i) {
                // Add the styling for a selected tank
                panes[i].getStyleClass().add("selectedTank");
            } else {
                // If the current tank is not selected but has the styling of a selected tank
                if(panes[i].getStyleClass().contains("selectedTanks")) {
                    // Remove the styling
                    panes[i].getStyleClass().remove("selectedTanks");
                }
            }
            // Add the pane styling to all panes
            panes[i].getStyleClass().add("pane");

            model = i;
            imgs[i].setRotate(imgs[i].getRotate() + 270);
            imgs[i].setFitWidth(200);
            imgs[i].setFitHeight(100);

            // Add event handlers to all image views
            imgEvent(imgs[i],model);
        }

        // Create a holder for sound buttons
        HBox soundHolder = new HBox();
        soundHolder.setSpacing(10);
        soundHolder.getChildren().addAll(Main.toggleMusic,Main.toggleSound);

        // Create a holder for the images
        HBox images = new HBox();
        images.setAlignment(Pos.CENTER);
        images.setMinHeight(200);
        images.setPrefHeight(200);
        images.setMaxHeight(200);
        images.setSpacing(30);
        images.setPadding(new Insets(30));

        // Add the images to the holder
        images.getChildren().addAll(b1,b2,b3,b4,b5,b6,b7);

        // Create a back button
        JFXButton backBtn = new JFXButton("BACK");

        // Add style to the page
        box.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // When the back button is pressed
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    // Switch from the model selection screen to the settings screen
                    Helper.changeScreen(t,this.getClass(),"fxml/settings.fxml");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Add all the content to the page
        box.getChildren().addAll(soundHolder,title,images,backBtn);
        primaryStage.getScene().setRoot(box);
        primaryStage.setTitle(Constants.GAME_NAME);
        // Show the screen
        primaryStage.show();
    }

    /**
     * Adds event handlers to a given image view that selects a tank when its image is pressed
     * @param view The image to add the handler to
     * @param model The model of that tank
     */
    public void imgEvent(ImageView view, int model) {
        // When an image is pressed
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Set the user's tank model to that model
                Main.user.setTankModel(model);
                // Remove selected image styling from all panes
                for(BorderPane pane: panes2) {
                    if(pane.getStyleClass().contains("selectedTank")) {
                        pane.getStyleClass().remove("selectedTank");
                    }
                }
                // Add the styling to the image that was pressed only
                panes2[model].getStyleClass().add("selectedTank");
            }
        });
    }

}
