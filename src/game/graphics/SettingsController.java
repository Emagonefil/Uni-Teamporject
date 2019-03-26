package game.graphics;

import com.jfoenix.controls.JFXSlider;
import game.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
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
        int musicVolume;
        if((int)Main.audioPlayer.getMusicVolume() == 0) {
            musicVolume = 60;
        } else {
            musicVolume = (int)Main.audioPlayer.getMusicVolume();
        }

        int soundVolume;
        if((int)Main.audioPlayer.getSoundEffectVolume() == 0) {
            soundVolume = 60;
        } else {
            soundVolume = (int)Main.audioPlayer.getSoundEffectVolume();
        }

        if(Main.audioPlayer.getMusicVolume() == 0) {
            music.setSelected(true);
        } else {
            music.setSelected(false);
        }
        if(Main.audioPlayer.getSoundEffectVolume() == 0) {
            sound.setSelected(true);
        } else {
            sound.setSelected(false);
        }

        music.setOnAction(event -> {
            if(music.isSelected()) {
                Main.audioPlayer.muteBackgroundMusic();
            } else {
                Main.audioPlayer.setMusicVolume(musicVolume);
            }
        });

        sound.setOnAction(event -> {
            if(sound.isSelected()) {
                Main.audioPlayer.muteSoundEffect();
            } else {
                Main.audioPlayer.setSoundEffectVolume(soundVolume);
            }
        });

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
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();
    }
}
