package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.screen.ScreenHandler;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class VoxelElement {

    public VoxelElement() {
        ScreenHandler.ACTIVE_ELEMENTS_LIST.add(this);
    }

    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println("Code: " + code);
        this.keyTyped(code);
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println("Code: " + code);
        this.keyPressed(code);
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println("Code: " + code);
        this.keyReleased(code);
    }

    public abstract void draw(UiUtilities uiUtilities);
    public void delete() {
        ScreenHandler.ACTIVE_ELEMENTS_LIST.remove(this);
    }
    public void keyTyped(int buttonNumber) {}
    public void keyPressed(int buttonNumber) {}
    public void keyReleased(int buttonNumber) {}

    public void onRightClick() {}
    public void onLeftClick() {}
    public void onMiddleClick() {}


    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {
            onLeftClick();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            onRightClick();
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            onMiddleClick();
        }
    }


}
