package de.Roboter007.voxelsociety.ui.elements.lists;

import de.Roboter007.voxelsociety.api.Pair;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.elements.listener.VoxelElementHandler;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.ui.elements.slider.ScrollDirection;
import de.Roboter007.voxelsociety.utils.VoxelPanel;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import static de.Roboter007.voxelsociety.ui.elements.slider.ScrollDirection.HORIZONTAL;

public class VoxelElementList extends VoxelElement {

    private final ScrollDirection scrollDirection;
    private final List<VoxelListEntry<?>> voxelElements;

    private int x;
    private int y;
    private final int spaceBetweenElements;

    private boolean scrollbarPressed;

    public int scroll;


    // Option for Scroll direction
    public VoxelElementList(ScrollDirection scrollDirection, int x, int y, int spaceBetweenElements, List<VoxelElement> voxelElements, int scroll) {
        super();
        this.scrollDirection = scrollDirection;
        this.x = x;
        this.y = y;
        this.spaceBetweenElements = spaceBetweenElements;

        List<VoxelListEntry<?>> voxelListEntryList = new ArrayList<>();
        if(scrollDirection == HORIZONTAL) {
            int xElement = x;
            for (VoxelElement voxelElement : voxelElements) {
                voxelListEntryList.add(new VoxelListEntry<>(voxelElement, xElement, y));
                xElement = xElement + voxelElement.getBoundingBox().width;
                if (voxelElement != voxelElements.getLast()) {
                    xElement = xElement + spaceBetweenElements;
                }
            }
        } else {
            int yElement = y;
            for (VoxelElement voxelElement : voxelElements) {
                voxelListEntryList.add(new VoxelListEntry<>(voxelElement, x, yElement));
                yElement = yElement + voxelElement.getBoundingBox().height;
                if (voxelElement != voxelElements.getLast()) {
                    yElement = yElement + spaceBetweenElements;
                }
            }
        }

        this.voxelElements = voxelListEntryList;
        this.scroll = scroll;
    }

    public boolean isMouseOverSlider() {
        return getScrollbar().contains(VoxelElementHandler.TRACKED_MOUSE_POSITION);
    }

    public boolean isMouseOverBB() {
        return getBoundingBox().contains(VoxelElementHandler.TRACKED_MOUSE_POSITION);
    }

    @Override
    public boolean isMouseOverElement() {
        return isMouseOverBB() || isMouseOverSlider();
    }


    public ScrollDirection getScrollDirection() {
        return scrollDirection;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpaceBetweenElements() {
        return spaceBetweenElements;
    }

    public List<VoxelListEntry<?>> getVoxelElements() {
        return voxelElements;
    }

    public int getScroll() {
        return scroll;
    }

    public int getHeight() {
        int height = 0;
        for(VoxelListEntry<?> voxelListEntry : this.voxelElements) {
            height = (int) (height + voxelListEntry.getElement().getBoundingBox().getHeight());
            if(voxelListEntry != this.voxelElements.getLast()) {
                height = height + spaceBetweenElements;
            }
        }
        return height;
    }

    //ToDo: einheitlich zwischen scrollDirection machen
    public int getWidth() {
        int width = 0;
        if(this.scrollDirection == HORIZONTAL) {
            for (VoxelListEntry<?> voxelListEntry : this.voxelElements) {
                width = (int) (width + voxelListEntry.getElement().getBoundingBox().getWidth());
                if (voxelListEntry != this.voxelElements.getLast()) {
                    width = width + spaceBetweenElements;
                }
            }
        } else {
            if(!this.voxelElements.isEmpty()) {
                for (VoxelListEntry<?> voxelListEntry : this.voxelElements) {
                    width = (int) (width + voxelListEntry.getElement().getBoundingBox().getWidth());
                }
                width = width / this.voxelElements.size();
                return width;
            }
        }
        return width;
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();
        for(VoxelListEntry<?> listEntry : getVoxelElements()) {
            int id = MenuHandler.getFocusedScreen().getVoxelElements().size() + getVoxelElements().indexOf(listEntry);
            MenuHandler.getFocusedScreen().drawElement(id, uiUtilities, listEntry.getElement());
        }

        uiUtilities.fillShape(Color.RED, getSidebar());
        uiUtilities.fillShape(Color.YELLOW, getScrollbar());

        if(scrollbarPressed) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.draw(this.getScrollbar());
        }
    }

    @Override
    public boolean customMouseWheelMove(MouseWheelEvent e) {

        if (scrollDirection == HORIZONTAL) {
            if (e.isControlDown()) {
                this.x = x + ((scroll * 10) * -e.getWheelRotation());
            } else {
                this.x = x + (scroll * -e.getWheelRotation());
            }
        } else {
            if (e.isControlDown()) {
                this.y = y + ((scroll * 10) * -e.getWheelRotation());
            } else {
                this.y = y + (scroll * -e.getWheelRotation());
            }
        }

        recalcElementPositions();
        return true;
    }

    public void recalcElementPositions() {
        if(scrollDirection == HORIZONTAL) {
            int xElement = this.x;
            for (VoxelListEntry<?> voxelListEntry : voxelElements) {
                voxelListEntry.setX(xElement);
                xElement = xElement + voxelListEntry.getElement().getBoundingBox().width;
                if(voxelListEntry != voxelElements.getLast()) {
                    xElement = xElement + spaceBetweenElements;
                }
            }
        } else {
            int yElement = this.y;
            for(VoxelListEntry<?> voxelListEntry : voxelElements) {
                voxelListEntry.setY(yElement);
                yElement = yElement + voxelListEntry.getElement().getBoundingBox().height;
                if(voxelListEntry != voxelElements.getLast()) {
                    yElement = yElement + spaceBetweenElements;
                }
            }
        }
    }

    @Override
    public boolean isSimilarTo(VoxelElement voxelElement) {
        return this.getBoundingBox().height == voxelElement.getBoundingBox().height && this.getBoundingBox().width == voxelElement.getBoundingBox().width;
    }


    @Override
    public void updateBoundingBox(int x, int y, int width, int height) {

    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    public Rectangle getScrollbar() {
        int scrollbarW = 10;
        if(scrollDirection == HORIZONTAL) {
            return new Rectangle(x , y - scrollbarW, this.getWidth(), scrollbarW);
        } else {
            return new Rectangle(x + this.getWidth() - scrollbarW, y, scrollbarW, this.getHeight());
        }
    }

    public Rectangle getSidebar() {
        int scrollbarW = 10;
        if(scrollDirection == HORIZONTAL) {
            return new Rectangle(0, y - scrollbarW, VoxelPanel.screenWidth, scrollbarW);
        } else {
            return new Rectangle(VoxelPanel.screenWidth - scrollbarW, 0, VoxelPanel.screenWidth, VoxelPanel.screenHeight);
        }
    }

    //ToDo: Reversed drag on here like in Mc
    @Override
    public boolean customMouseDrag(MouseEvent e) {
        //ToDo: Fix Lagg
        Point clickedPos = e.getPoint();
        if (this.scrollbarPressed) {
            if (scrollDirection == HORIZONTAL) {
                this.x = (clickedPos.x - this.getBoundingBox().x);
            } else {
                this.y = clickedPos.y - this.getBoundingBox().y;

            }

            recalcElementPositions();
        }
        return true;
    }

    @Override
    public boolean customMousePress(MouseEvent e) {
        if(isLeftClick(e) || isRightClick(e)) {
            this.scrollbarPressed = this.getScrollbar().contains(e.getPoint());
        }


        return true;
    }

    @Override
    public boolean customMouseRelease(MouseEvent e) {
        if(isLeftClick(e) || isRightClick(e)) {
            this.scrollbarPressed = false;
        }

        return true;
    }

}
