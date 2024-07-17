package de.Roboter007.voxelsociety.world;

public enum WorldGenStatus {

    NOT_STARTED(0),
    BLOCKS(1),
    FLOWERS(2),
    STRUCTURES(3),
    CIVILIZATIONS(4),
    FINISHED(5);


    private static WorldGenStatus currentWorldGenStatus = NOT_STARTED;

    private final int status;

    WorldGenStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public boolean equalsCurrent() {
        return this == currentWorldGenStatus;
    }

    public static void setCurrentWorldGenStatus(WorldGenStatus status) {
        currentWorldGenStatus = status;
    }

    public static boolean isStatus(WorldGenStatus status) {
        return currentWorldGenStatus == status;
    }
}
