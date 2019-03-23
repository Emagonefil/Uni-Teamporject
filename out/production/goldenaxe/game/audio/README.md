

# AudioPlayer

```java
//default constructor
AudioPlayer audioPlayer = new AudioPlayer();

//return the current music volume in float
audioPlayer.getMusicVolume();

//set the volume of background music to volume
audioPlayer.setMusicVolume(float volume);

//return the current sound effect volume in float
audioPlayer.getSoundEffectVolume();

//set the volume of sound effect to volume
audioPlayer.setSoundEffectVolume(float volume);

//play the default background music
audioPlayer.playBackgourndMusic();

//mute the volume of background music(still playing)
audioPlayer.muteBackgroundMusic();

//mute the volume of sound effect
audioPlayer.muteSoundEffect();

//play the sound effect of shooting
audioPlayer.playShootSound();

//play the sound effect of picking up items
audioPlayer.playPickItemSound();

//play the sound effect of winning
audioPlayer.playWinSound();

//play the sound effect of losing
audioPlayer.playLoseSound();
```