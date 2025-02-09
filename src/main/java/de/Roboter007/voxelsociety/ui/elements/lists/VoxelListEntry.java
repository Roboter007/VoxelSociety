package de.Roboter007.voxelsociety.ui.elements.lists;

import de.Roboter007.voxelsociety.ui.elements.VoxelElement;

public class VoxelListEntry<E extends VoxelElement> {

    private final E element;

    private int x;
    private int y;

    public VoxelListEntry(E element, int x, int y) {
        this.element = element;
        this.updatePos(x, y);
    }

    public E getElement() {
        return element;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
        this.getElement().move(x, this.getElement().getBoundingBox().y);
    }

    public void setY(int y) {
        this.y = y;
        this.getElement().move(this.getElement().getBoundingBox().x, y);

    }

    public void updatePos(int x, int y) {
        this.x = x;
        this.y = y;
        this.getElement().move(x, y);
    }

}
