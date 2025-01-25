package de.Roboter007.voxelsociety.sound;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.utils.texture.SourcePath;

import java.util.ArrayList;
import java.util.List;

public class SoundPlayer implements Runnable {

    private Thread soundThread;
    private List<Sound> playingSounds = new ArrayList<>();

    public SoundPlayer() {
        this.soundThread = new Thread("Sound Thread");
    }

    public List<Sound> getPlayingSounds() {
        return playingSounds;
    }

    public void start() {
        this.soundThread = new Thread("Sound Thread");
        VoxelSociety.SERVICE.submit(soundThread);
    }

    public void stop() {

    }

    public boolean isSoundPlaying(SourcePath location) {
        for(Sound sound : playingSounds) {
            if(sound.sourcePath().equals(location)) {
                return true;
            }
        }
        return false;
    }

    public void run() {
        //while (soundThread != null) {

        //}
    }
}
