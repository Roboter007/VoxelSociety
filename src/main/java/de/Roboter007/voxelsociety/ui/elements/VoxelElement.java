package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.listener.VoxelElementHandler;
import de.Roboter007.voxelsociety.ui.elements.lists.VoxelElementList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public abstract class VoxelElement {


    private Consumer<VoxelElement> updateTask = null;
    protected Rectangle boundingBox;

    public VoxelElement() {
    }


    public boolean isMouseOverElement() {
        Rectangle rectangle = this.getBoundingBox();
        return rectangle.contains(VoxelElementHandler.TRACKED_MOUSE_POSITION);
    }

    public abstract void draw(UiUtilities uiUtilities);

    public void delete() {

    }

    public VoxelElementList setUpdateTask(Consumer<VoxelElement> updateTask) {
        this.updateTask = updateTask;
        return (VoxelElementList) this;
    }

    public boolean canUpdate() {
        return updateTask != null;
    }

    public void update() {
        if(canUpdate()) {
            updateTask.accept(this);
        }
    }

    public abstract void updateBoundingBox(int x, int y, int width, int height);
    public abstract void move(int x, int y);

    public abstract Rectangle getBoundingBox();

    public void onMouseClick(MouseEvent e) {}
    public void onMousePress(MouseEvent e) {}
    public void onMouseRelease(MouseEvent e) {}
    public void onMouseEnter(MouseEvent e) {}
    public void onMouseExit(MouseEvent e) {}
    public void onMouseDrag(MouseEvent e) {}
    public void onMouseMove(MouseEvent e) {}
    public void onMouseWheelMove(MouseWheelEvent e) {}
    public void onKeyTyped(KeyEvent e) {}
    public void onKeyPressed(KeyEvent e) {}
    public void onKeyReleased(KeyEvent e) {}


    // If true disables default on Logic
    public boolean customMouseClick(MouseEvent e) {
        return false;
    }

    public boolean customMousePress(MouseEvent e) {
        return false;
    }

    public boolean customMouseRelease(MouseEvent e) {
        return false;
    }

    public boolean customMouseEnter(MouseEvent e) {
        return false;
    }

    public boolean customMouseExit(MouseEvent e) {
        return false;
    }

    public boolean customMouseDrag(MouseEvent e) {
        return false;
    }

    public boolean customMouseMove(MouseEvent e) {
        return false;
    }

    public boolean customMouseWheelMove(MouseWheelEvent e) {
        return false;
    }

    public boolean customKeyTyped(KeyEvent e) {
        return false;
    }

    public boolean customKeyPressed(KeyEvent e) {
        return false;
    }

    public boolean customKeyReleased(KeyEvent e) {
        return false;
    }

    public boolean isRightClick(MouseEvent e) {
        return SwingUtilities.isRightMouseButton(e);
    }

    public boolean isLeftClick(MouseEvent e) {
        return SwingUtilities.isLeftMouseButton(e);
    }
    public boolean isMiddleClick(MouseEvent e) {
        return SwingUtilities.isMiddleMouseButton(e);
    }

}
