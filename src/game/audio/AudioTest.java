package game.audio;

import java.util.Scanner;

/**
 *  @author Feilue 3/21/2019
 */
public class AudioTest {


    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();
        Scanner scanner = new Scanner(System.in);

        audioPlayer.playBackgroundMusic();
        while(true){
            String s = scanner.nextLine();
            if(s.equalsIgnoreCase("shoot")) audioPlayer.playShootSound();
            if(s.equalsIgnoreCase("pick")) audioPlayer.playPickItemSound();
            if(s.equalsIgnoreCase("win")) audioPlayer.playWinSound();
            if(s.equalsIgnoreCase("lose")) audioPlayer.playLoseSound();
            if(s.equalsIgnoreCase("mute music")) audioPlayer.muteBackgroundMusic();
            if(s.equalsIgnoreCase("mute sound")) audioPlayer.muteSoundEffect();
            if(s.equalsIgnoreCase("set music volume")){
                float volume = scanner.nextFloat();
                audioPlayer.setMusicVolume(volume);
            }
            if(s.equalsIgnoreCase("set sound volume")){
                float volume = scanner.nextFloat();
                audioPlayer.setSoundEffectVolume(volume);
            }
            System.out.println("Music volume: "+audioPlayer.getMusicVolume());
            System.out.println("sound effect volume: "+audioPlayer.getSoundEffectVolume());
        }


    }
}
