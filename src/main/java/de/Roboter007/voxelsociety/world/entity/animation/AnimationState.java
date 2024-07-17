package de.Roboter007.voxelsociety.world.entity.animation;

public enum AnimationState {

    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    private final int number;

    AnimationState(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return this.name().toLowerCase();
    }
}
