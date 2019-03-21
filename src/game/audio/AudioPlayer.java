package game.audio;

import javafx.scene.media.AudioClip;

import javax.sound.sampled.*;
import java.io.File;

/**
 *  @author Feilue 3/21/2019
 */
public class AudioPlayer {

    private MusicPlayer backgroundMusicPlayer;
    private SoundPlayer shootSoundPlayer;
    private SoundPlayer pickItemSoundPlayer;
    private SoundPlayer winSoundPlayer;
    private SoundPlayer loseSoundPlayer;

    private static float musicVolume=60;
    private static float soundEffectVolume=60;


    public AudioPlayer(){

            this.backgroundMusicPlayer = getMusicPlayer(new File(AudioPath.backgroudMusic));
            this.shootSoundPlayer = getSoundPlayer(new File(AudioPath.shootMusic));
            this.pickItemSoundPlayer = getSoundPlayer(new File(AudioPath.pickItemMusic));
            this.winSoundPlayer = getSoundPlayer(new File(AudioPath.winMusic));
            this.loseSoundPlayer = getSoundPlayer(new File(AudioPath.loseMusic));
    }

    public float getMusicVolume(){
        return musicVolume;
    }
    public void setMusicVolume(float volume){
        if(volume<=80&&volume>=0) {
            this.musicVolume = volume;
            this.backgroundMusicPlayer.setVolume(this.musicVolume);
        }
    }
    public float getSoundEffectVolume(){
        return this.soundEffectVolume;
    }
    public void setSoundEffectVolume(float volume){
        if(volume<=80&&volume>=0)
        this.soundEffectVolume = volume;
    }

    public void playBackgroundMusic(){
        this.backgroundMusicPlayer.play();
    }
    public void muteBackgroundMusic(){
        this.backgroundMusicPlayer.mute();
    }
    public void muteSoundEffect(){
        soundEffectVolume=0;
    }
    public void playShootSound(){
        this.shootSoundPlayer.play();
    }
    public void playPickItemSound(){
        this.pickItemSoundPlayer.play();
    }
    public void playWinSound(){
        this.winSoundPlayer.play();
    }
    public void playLoseSound(){
        this.loseSoundPlayer.play();
    }



    private Clip getClip(File file){
        Clip clip=null;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }

    private MusicPlayer getMusicPlayer(File file){
        return new MusicPlayer(getClip(file));
    }

    private SoundPlayer getSoundPlayer(File file){
        return new SoundPlayer(getClip(file));
    }


    private static class MusicPlayer{
        Clip clip;
        FloatControl floatControl;


        public MusicPlayer(Clip clip){
            this.clip=clip;
            floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        }

        public void play(){
            setVolume(musicVolume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        public void mute(){
            floatControl.setValue(-80);
        }

        public void setVolume(float volume){
            floatControl.setValue(volume-80);
        }

    }

    private static class SoundPlayer{
        Clip clip;
        FloatControl floatControl;

        public SoundPlayer(Clip clip){
            this.clip=clip;
            floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        }

        public void play(){
            floatControl.setValue(soundEffectVolume-80);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    clip.setMicrosecondPosition(0);
                    clip.start();
                }
            }).start();

        }


    }



}
