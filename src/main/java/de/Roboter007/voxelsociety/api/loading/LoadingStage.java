package de.Roboter007.voxelsociety.api.loading;

import com.sun.javafx.application.PlatformImpl;
import de.Roboter007.voxelsociety.utils.ResourceLoader;

import java.util.Arrays;


public enum LoadingStage {

    DEPENDENCY(() -> PlatformImpl.startup(() -> System.out.println("Starting..."))),
    TEXTURES(ResourceLoader::loadTextures);
    private final Runnable task;

    LoadingStage(Runnable task) {
        this.task = task;
    }

    public int id() {
        return Arrays.stream(values()).toList().indexOf(this) + 1;
    }

    public Runnable task() {
        return task;
    }

    public void run() {
        if(this.task != null) {
            System.out.println("LoadingStage: " + this.name() + " with id: " + id());
            this.task.run();
        }
    }
}
