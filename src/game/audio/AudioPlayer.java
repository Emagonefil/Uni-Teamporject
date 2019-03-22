package game.audio;

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

    /**
     * Default constructor that initialize all the music needed
     */
    public AudioPlayer(){
            this.backgroundMusicPlayer = getMusicPlayer(new File(AudioPath.backgroudMusic));
            this.shootSoundPlayer = getSoundPlayer(new File(AudioPath.shootMusic));
            this.pickItemSoundPlayer = getSoundPlayer(new File(AudioPath.pickItemMusic));
            this.winSoundPlayer = getSoundPlayer(new File(AudioPath.winMusic));
            this.loseSoundPlayer = getSoundPlayer(new File(AudioPath.loseMusic));
    }

    /**
     * get the current volume value of the music
     * @return the volume of the music
     */
    public float getMusicVolume(){
        return musicVolume;
    }

    /**
     * set the volume for music
     * @param volume value of the volume in (0-80)
     */
    public void setMusicVolume(float volume){
        if(volume<=80&&volume>=0) {
            this.musicVolume = volume;
            this.backgroundMusicPlayer.setVolume(this.musicVolume);
        }
    }

    /**
     * get the current volume value of the sound effect
     * @return the volume of the sound effect
     */
    public float getSoundEffectVolume(){
        return this.soundEffectVolume;
    }

    /**
     * set the volume of sound effect
     * @param volume value of the volume in (0-80)
     */
    public void setSoundEffectVolume(float volume){
        if(volume<=80&&volume>=0)
        this.soundEffectVolume = volume;
    }

    /**
     * play the default background music
     */
    public void playBackgroundMusic(){
        this.backgroundMusicPlayer.play();
    }

    /**
     * mute all background music
     * (set the volume of music to 0)
     */
    public void muteBackgroundMusic(){
        setMusicVolume(0);
    }

    /**
     * mute all sound effects
     * (set the volume of sound effects to 0)
     */
    public void muteSoundEffect(){
        setSoundEffectVolume(0);
    }

    /**
     * play the sound effect of shooting
     */
    public void playShootSound(){
        this.shootSoundPlayer.play();
    }

    /**
     * play the sound of picking up items
     */
    public void playPickItemSound(){
        this.pickItemSoundPlayer.play();
    }

    /**
     * play the sound of winning
     */
    public void playWinSound(){
        this.winSoundPlayer.play();
    }

    /**
     * play the sound of losing
     */
    public void playLoseSound(){
        this.loseSoundPlayer.play();
    }


    /**
     *
     * @param file
     * @return a Clip that generated from the file
     */
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

    /**
     *
     * @param file the file of musice that wants to be played
     * @return a MusicPlayer that can play the music of the file
     * @see MusicPlayer
     */
    private MusicPlayer getMusicPlayer(File file){
        return new MusicPlayer(getClip(file));
    }

    /**
     *
     * @param file the file of sound that wants to be played
     * @return a SoundPlayer that can play the sound of the file
     * @see SoundPlayer
     */
    private SoundPlayer getSoundPlayer(File file){
        return new SoundPlayer(getClip(file));
    }


    private static class MusicPlayer{
        Clip clip;
        FloatControl floatControl;

        /**
         * default constructor
         * @param clip the clip for this player to play
         */
        public MusicPlayer(Clip clip){
            this.clip=clip;
            floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        }

        /**
         * play the music in loop
         */
        public void play(){
            setVolume(musicVolume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        /**
         * set the volume of this player
         * @param volume
         */
        public void setVolume(float volume){
            floatControl.setValue(volume-80);
        }

    }


    private static class SoundPlayer{
        Clip clip;
        FloatControl floatControl;

        /**
         * default constructor
         * @param clip
         */
        public SoundPlayer(Clip clip){
            this.clip=clip;
            floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        }

        /**
         * play the sound of the clip
         */
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
