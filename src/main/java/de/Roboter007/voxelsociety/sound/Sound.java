package de.Roboter007.voxelsociety.sound;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.utils.texture.PathLocation;
import de.Roboter007.voxelsociety.utils.texture.SourcePath;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

//ToDo: implement Sound Options
public class Sound extends PathLocation {

    public final MediaPlayer mediaPlayer;
    private final SoundType soundType;
    public double speed = 1.0D;

    public Sound(SoundType soundType, String key, String location, String file) {
        super(key, location, file);
        this.soundType = soundType;
        URL resource = getClass().getResource(this.sourcePath.fullLocation());
        if(resource != null) {
            Media media = new Media(resource.toString());
            this.mediaPlayer = new MediaPlayer(media);
        } else {
            this.mediaPlayer = null;
        }
    }

    public Sound(SoundType soundType, String location, String file) {
        this(soundType, voxelSociety(location, file));
    }

    public Sound(SoundType soundType, String file) {
        this(soundType, voxelSociety("sounds/" + soundType.name().toLowerCase(), file));
    }

    public Sound(SoundType soundType, SourcePath sourcePath) {
        super(sourcePath);
        this.soundType = soundType;
        URL resource = getClass().getResource(this.sourcePath.fullLocation());
        if(resource != null) {
            Media media = new Media(resource.toString());
            this.mediaPlayer = new MediaPlayer(media);
        } else {
            this.mediaPlayer = null;
        }
    }

    public static SourcePath voxelSociety(String location, String file) {
        return new SourcePath(VoxelSociety.GAME_ID, location, file);
    }


    public void play(double volume, double speed) {
        if(mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
            mediaPlayer.setRate(speed);
            mediaPlayer.play();
            System.out.println("Playing with Volume: " + mediaPlayer.getVolume() + ", with Speed: " + this.mediaPlayer.getRate());
        } else {
            System.out.println("MediaPlayer is null: " + this.sourcePath.fullLocation());
        }
    }

    public void play(double speed) {
        play(soundType.volume(), speed);
    }

    public void play() {
        play(soundType.volume(), 1.0D);
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void pause() {
        this.mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }



}
