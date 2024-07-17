package de.Roboter007.voxelsociety.keys;

import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.screen.ScreenHandler;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener {


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        for(VSKey key : VSKey.values()) {
            if(code == key.getKeyNumber()) {
                if(!key.isToggleable()) {
                    key.setPressed(true);
                } else {
                    key.setPressed(!key.isPressed());
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        for(VSKey key : VSKey.values()) {
            if(code == key.getKeyNumber() && !key.isToggleable()) {
                key.setPressed(false);
            }
        }
    }

    //ToDo: Fix funktioniert noch nicht
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            System.out.println("Left");
        }
        for(VoxelElement voxelElement : ScreenHandler.ACTIVE_ELEMENTS_LIST) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                System.out.println("Left");
                voxelElement.onLeftClick();
            } else if (SwingUtilities.isRightMouseButton(e)) {
                voxelElement.onRightClick();
            } else if (SwingUtilities.isMiddleMouseButton(e)) {
                voxelElement.onMiddleClick();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
