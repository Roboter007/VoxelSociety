package de.Roboter007.voxelsociety.sound;

import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playlist {

    public Sound currentTrack;
    public final List<Sound> sounds = new ArrayList<>(); ;

    private final SoundType soundType;
    public final String playListLocation;

    public Playlist(SoundType soundType, String playListLocation) {
        this.soundType = soundType;
        this.playListLocation = playListLocation;
        reloadSongs();
    }

    public void playTrack(String name) {
        if (!sounds.isEmpty()) {
            for (Sound sound : sounds) {
                if (sound.sourcePath().file().substring(0, sound.sourcePath().file().length() - 4).equals(name)) {
                    sound.play();
                    this.currentTrack = sound;
                }
            }
        } else {
            System.out.println("No Song available!");
        }
    }

    public void playTrack(int id) {
        if(currentTrack != null) {
            currentTrack.stop();
        }

        if (!sounds.isEmpty()) {
            Sound sound = sounds.get(id);
            sound.play();
            this.currentTrack = sound;
        } else {
            System.out.println("No Song available!");
        }
    }

    public void playRandomTrack() {
        playTrack(new Random().nextInt(0, sounds.size()));
    }

    public void reloadSongs() {
        URL resource = getClass().getResource("/voxelsociety/sounds/" + soundType.name().toLowerCase() + "/" + playListLocation);
        if (resource != null) {
            try {
                System.out.println("Reloading Songs in path... " + resource);

                File songFolder = new File(resource.toURI());

                File[] directories = songFolder.listFiles();

                if (directories != null) {
                    for (File file : directories) {
                        System.out.println("Added Song: " + file.getName());
                        this.sounds.add(new Sound(soundType, playListLocation + "/" + file.getName()));
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Resource Null: " + "/voxelsociety/sounds/" + soundType.name().toLowerCase() + "/" + playListLocation);
        }

    }

    public void resumeCurrentTrack() {
        if(currentTrack != null && currentTrackIsPaused()) {
            currentTrack.play();
        }
    }

    public void stopCurrentTrack() {
        if(currentTrack != null) {
            currentTrack.stop();
        }
    }

    public boolean currentTrackIsPaused() {
        System.out.println(currentTrack != null);
        if(currentTrack != null) {
            System.out.println(currentTrack.mediaPlayer.getStatus());
        }
        return currentTrack != null && currentTrack.mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED;
    }

    public void pauseCurrentTrack() {
        if(currentTrack != null) {
            currentTrack.pause();
        }
    }

    public boolean noTrackPlaying() {
        return !(currentTrack != null && currentTrack.isPlaying() && !currentTrackIsPaused());
    }

    public boolean noTrackSelected() {
        return !(currentTrack != null && currentTrack.isPlaying());
    }

    public void updateVolume() {
        if(this.currentTrack != null) {
            this.currentTrack.mediaPlayer.setVolume(soundType.volume() / 100);
        }
    }
}
