package de.Roboter007.voxelsociety.keys;

import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.Worlds;
import de.Roboter007.voxelsociety.world.block.BlockRegistry;
import de.Roboter007.voxelsociety.world.entity.Entities;
import de.Roboter007.voxelsociety.world.pos.IntPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KeyHandler implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {

    public IntPosition cursorPos = new IntPosition();
    public boolean rightClick;
    public boolean leftClick;
    public boolean middleClick;
    public int selectedBlockId = 1;

    public int selectedSlot = 0;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        for (VSKey key : VSKey.values()) {
            if (code == key.getKeyNumber()) {
                if (key.isToggleable()) {
                    key.setPressed(!key.isPressed());
                    key.runAction();
                } else {
                    key.setPressed(true);
                    key.runAction();
                }
            }
        }

        if (code == KeyEvent.VK_X) {
            int worldId = Worlds.worldId(Entities.PLAYER.world()) + 1;
            if (worldId == Worlds.REGISTERED_WORLDS.size()) {
                worldId = 0;
            }
            World world = Worlds.REGISTERED_WORLDS.get(worldId);
            Entities.PLAYER.changeWorld(world);
        }
        if (code == KeyEvent.VK_LEFT) {
            if (selectedSlot <= 0) {
                selectedSlot = 9;
            } else {
                selectedSlot--;
            }

        }

        if (code == KeyEvent.VK_RIGHT) {
            if (selectedSlot >= 9) {
                selectedSlot = 0;
            } else {
                selectedSlot++;
            }
        }
        if (code == KeyEvent.VK_1) {
            selectedSlot = 0;
        } else if (code == KeyEvent.VK_2) {
            selectedSlot = 1;
        } else if (code == KeyEvent.VK_3) {
            selectedSlot = 2;
        } else if (code == KeyEvent.VK_4) {
            selectedSlot = 3;
        } else if (code == KeyEvent.VK_5) {
            selectedSlot = 4;
        } else if (code == KeyEvent.VK_6) {
            selectedSlot = 5;
        } else if (code == KeyEvent.VK_7) {
            selectedSlot = 6;
        } else if (code == KeyEvent.VK_8) {
            selectedSlot = 7;
        } else if (code == KeyEvent.VK_9) {
            selectedSlot = 8;
        } else if (code == KeyEvent.VK_0) {
            selectedSlot = 9;
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


    @Override
    public void mouseDragged(MouseEvent e) {
        cursorPos.setPos(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cursorPos.setPos(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(SwingUtilities.isRightMouseButton(e)) {
            rightClick = true;
        }

        if(SwingUtilities.isLeftMouseButton(e)) {
            leftClick = true;
        }

        if(SwingUtilities.isMiddleMouseButton(e)) {
            middleClick = true;
        }

        if(SwingUtilities.isMiddleMouseButton(e)) {
            if (Entities.PLAYER.targetedBlock != null) {
                Entities.PLAYER.setBlockIdFromSelectedSlot(Entities.PLAYER.targetedBlock.getBlockEntry().getBlockId());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        rightClick = false;
        leftClick = false;
        middleClick = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        selectedSlot = selectedSlot + e.getWheelRotation();

        if (selectedSlot >= 10) {
            selectedSlot = 0;
        }
        if (selectedSlot < 0) {
            selectedSlot = 9;
        }
    }
}
