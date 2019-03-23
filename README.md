

# AudioPlayer

```java
//default constructor
public AudioPlayer();

//return the current music volume in float
public float getMusicVolume();

//set the volume of background music to volume
public void setMusicVolume(float volume);

//return the current sound effect volume in float
public float getSoundEffectVolume();

//set the volume of sound effect to volume
public void setSoundEffectVolume(float volume);

//play the default background music
public void playBackgourndMusic();

//mute the volume of background music(still playing)
public void muteBackgroundMusic();

//mute the volume of sound effect
public void muteSoundEffect();

//play the sound effect of shooting
public void playShootSound();

//play the sound effect of picking up items
public void playPickItemSound();

//play the sound effect of winning
public void playWinSound();

//play the sound effect of losing
public void playLoseSound();

//generate and return a clip based on the file
private Clip getClip(File file);

//generate and return a MusicPlayer based on the file
private MusicPlayer getMusicPlayer(File file);

//generate and return a SoundPlayer based on the file
private SoundPlayer getSoundPlayer(File file);
```

# MusicPlayer

```java
//play the clip of this player in loop
public void play();

//set the volume of this player
public void setVolume(float volume);
```

# SoundPlayer

```java
//play the clip of this player once
public void play();
```