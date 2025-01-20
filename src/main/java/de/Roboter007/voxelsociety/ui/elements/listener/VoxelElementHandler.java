package de.Roboter007.voxelsociety.ui.elements.listener;

import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;

import java.awt.*;
import java.awt.event.*;

public class VoxelElementHandler implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    public static Point TRACKED_MOUSE_POSITION = new Point();

    @Override
    public void mouseClicked(MouseEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customMouseClick(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onMouseClick(e);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customMousePress(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onMousePress(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customMouseRelease(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onMouseRelease(e);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customMouseEnter(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onMouseEnter(e);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customMouseExit(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onMouseExit(e);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customMouseDrag(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onMouseDrag(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        TRACKED_MOUSE_POSITION = e.getPoint();
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customMouseMove(e)) {
                Rectangle rectangle = voxelElement.getBoundingBox();
                if(rectangle.contains(e.getPoint())) {
                    voxelElement.onMouseMove(e);
                }
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customMouseWheelMove(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onMouseWheelMove(e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customKeyTyped(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onKeyTyped(e);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customKeyPressed(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onKeyPressed(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            if(!voxelElement.customKeyReleased(e) && voxelElement.isMouseOverElement()) {
                voxelElement.onKeyReleased(e);
            }
        }
    }
}
