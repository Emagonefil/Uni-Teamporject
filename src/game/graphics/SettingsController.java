package game.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import game.Constants;
import game.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SettingsController {


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
        Label title = new Label("CLASH OF TANKS");
        title.getStyleClass().add("title");
        title.setFont(new Font("Press Start 2P", 36));

        JFXButton backBtn = new JFXButton("BACK");

        // When the back button is pressed
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    Parent root1 = FXMLLoader.load(getClass().getResource("fxml/menu3.fxml"));
                    primaryStage.getScene().setRoot(root1);
                    primaryStage.setTitle(Constants.GAME_NAME);

                    primaryStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        box.getChildren().addAll(title,backBtn);

        primaryStage.getScene().setRoot(box);
        primaryStage.setTitle(Constants.GAME_NAME);

        primaryStage.show();
    }
}
