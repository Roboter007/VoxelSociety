package de.Roboter007.voxelsociety.events;

public class PlayerEvent extends Event implements Cancelable {

    public boolean canceled = false;

    public PlayerEvent() {
        super();
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
