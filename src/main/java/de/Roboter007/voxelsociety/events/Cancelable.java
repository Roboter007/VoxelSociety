package de.Roboter007.voxelsociety.events;

public interface Cancelable {

    boolean isCanceled();

    void setCanceled(boolean canceled);
}
