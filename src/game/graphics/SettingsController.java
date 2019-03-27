package game.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import game.Constants;
import game.Main;
import game.entity.Entity;
import game.entity.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SettingsController {

    int model;
    BorderPane[] panes2;

    @FXML
    private JFXSlider musicSlider;
    @FXML
    private JFXSlider soundSlider;
    @FXML
    private ToggleButton music;
    @FXML
    private ToggleButton sound;

    public void initialize() {
        Main.soundButtons(music,sound);
        System.out.println("MUSIC VOLUME WAS: " + Main.audioPlayer.getMusicVolume());
        musicSlider.setValue((5.0f*Main.audioPlayer.getMusicVolume())/4);
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Main.audioPlayer.setMusicVolume((newValue.floatValue()*4)/5);
            System.out.println("MUSIC VOLUME IS NOW: " + Main.audioPlayer.getMusicVolume());
            if(music.isSelected() && Main.audioPlayer.getMusicVolume() != 0) {
                music.setSelected(false);
            } else if (!music.isSelected() && Main.audioPlayer.getMusicVolume() == 0) {
                music.setSelected(true);
            }

        });

        System.out.println("SOUND EFFECTS VOLUME WAS: " + Main.audioPlayer.getSoundEffectVolume());
        soundSlider.setValue((5.0f*Main.audioPlayer.getSoundEffectVolume())/4);
        soundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Main.audioPlayer.setSoundEffectVolume((newValue.floatValue()*4)/5);
            System.out.println("SOND EFFECTS VOLUME IS NOW: " + Main.audioPlayer.getSoundEffectVolume());
            if(sound.isSelected() && Main.audioPlayer.getSoundEffectVolume() != 0) {
                sound.setSelected(false);
            } else if (!sound.isSelected() && Main.audioPlayer.getSoundEffectVolume() == 0) {
                sound.setSelected(true);
            }
        });

    }

    @FXML
    protected void back(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("fxml/menu3.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle(Constants.GAME_NAME);

        primaryStage.show();
    }

    @FXML
    protected void model(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

//        Parent root1 = FXMLLoader.load(getClass().getResource("fxml/model.fxml"));
        VBox box = new VBox();
        box.setSpacing(50);
        box.setPadding(new Insets(50));
//        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("vbox2");
        Label title = new Label("CLASH OF TANKS");
//        title.setPrefHeight(150);
        title.getStyleClass().add("title");
        title.setFont(new Font("Press Start 2P", 36));
        Image t1 = Renderer.tank1;
        Image t2 = Renderer.tank2;
        Image t3 = Renderer.tank3;
        Image t4 = Renderer.tank4;
        Image t5 = Renderer.tank5;
        Image t6 = Renderer.tank6;
        Image t7 = Renderer.tank7;
        ImageView v1 = new ImageView(t1);
        ImageView v2 = new ImageView(t2);
        ImageView v3 = new ImageView(t3);
        ImageView v4 = new ImageView(t4);
        ImageView v5 = new ImageView(t5);
        ImageView v6 = new ImageView(t6);
        ImageView v7 = new ImageView(t7);
        ImageView[] imgs = {v1,v2,v3,v4,v5,v6,v7};
        BorderPane b1 = new BorderPane(v1);
        BorderPane b2 = new BorderPane(v2);
        BorderPane b3 = new BorderPane(v3);
        BorderPane b4 = new BorderPane(v4);
        BorderPane b5 = new BorderPane(v5);
        BorderPane b6 = new BorderPane(v6);
        BorderPane b7 = new BorderPane(v7);
        BorderPane[] panes = {b1,b2,b3,b4,b5,b6,b7};
        panes2 = panes;

        for(int i = 0; i < imgs.length; i++) {
            if(Main.user.getTankModel() == i) {
                panes[i].getStyleClass().add("selectedTank");
            } else {
                if(panes[i].getStyleClass().contains("selectedTanks")) {
                    panes[i].getStyleClass().remove("selectedTanks");
                }
            }
            panes[i].getStyleClass().add("pane");
            model = i;
            imgs[i].setRotate(imgs[i].getRotate() + 270);
            imgs[i].setFitWidth(200);
            imgs[i].setFitHeight(100);
            imgEvent(imgs[i],model);
        }

        // SOUND BUTTONS
        final ToggleButton toggleMusic = new ToggleButton();
        toggleMusic.setLayoutX(10);
        toggleMusic.setLayoutY(10);
        final ToggleButton toggleSound = new ToggleButton();
        toggleSound.setLayoutX(60);
        toggleSound.setLayoutY(10);
        toggleSound.setId("toggleSound");
        toggleMusic.setId("toggleMusic");

        Main.soundButtons(toggleMusic, toggleSound);

        HBox soundHolder = new HBox();
        soundHolder.setSpacing(10);
        soundHolder.getChildren().addAll(toggleMusic,toggleSound);

        // TANK IMAGES
        HBox images = new HBox();
        images.setAlignment(Pos.CENTER);
        images.setMaxHeight(350);
        images.setSpacing(30);
        images.setPadding(new Insets(30));


        images.getChildren().addAll(b1,b2,b3,b4,b5,b6,b7);

        JFXButton backBtn = new JFXButton("BACK");

        images.setMinHeight(200);
        images.setPrefHeight(200);
        images.setMaxHeight(200);
//        images.setFillHeight(300);
        box.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // When the back button is pressed
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    Parent root1 = FXMLLoader.load(getClass().getResource("fxml/settings.fxml"));
                    primaryStage.getScene().setRoot(root1);
                    primaryStage.setTitle(Constants.GAME_NAME);

                    primaryStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        box.getChildren().addAll(soundHolder,title,images,backBtn);

        primaryStage.getScene().setRoot(box);
        primaryStage.setTitle(Constants.GAME_NAME);

        primaryStage.show();
    }

    public void imgEvent(ImageView view, int model) {
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.user.setTankModel(model);
                for(BorderPane pane: panes2) {
                    if(pane.getStyleClass().contains("selectedTank")) {
                        pane.getStyleClass().remove("selectedTank");
                    }
                }
                panes2[model].getStyleClass().add("selectedTank");

//                    System.out.println("This is a " + e.type + " with the id " + e.id);
                System.out.println("the user " + Main.user.getUsername() + " has the tank model " + Main.user.getTankModel());
            }
        });
    }

}
