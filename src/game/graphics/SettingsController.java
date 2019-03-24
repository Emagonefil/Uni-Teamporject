package game.graphics;

import com.jfoenix.controls.JFXSlider;
import game.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController {


    @FXML
    private JFXSlider musicSlider;
    @FXML
    private JFXSlider soundSlider;

    public void initialize() {
        System.out.println("MUSIC VOLUME WAS: " + Main.audioPlayer.getMusicVolume());
        musicSlider.setValue((5.0f*Main.audioPlayer.getMusicVolume())/4);
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Main.audioPlayer.setMusicVolume((newValue.floatValue()*4)/5);
            System.out.println("MUSIC VOLUME IS NOW: " + Main.audioPlayer.getMusicVolume());

        });

        System.out.println("SOUND EFFECTS VOLUME WAS: " + Main.audioPlayer.getSoundEffectVolume());
        soundSlider.setValue((5.0f*Main.audioPlayer.getSoundEffectVolume())/4);
        soundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Main.audioPlayer.setSoundEffectVolume((newValue.floatValue()*4)/5);
            System.out.println("SOND EFFECTS VOLUME IS NOW: " + Main.audioPlayer.getSoundEffectVolume());

        });

    }

    @FXML
    protected void back(ActionEvent event) throws Exception {

        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("menu3.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();

    }
}
