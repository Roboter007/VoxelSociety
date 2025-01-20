package de.Roboter007.voxelsociety.ui.elements.lists;

import de.Roboter007.voxelsociety.api.Pair;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.ScrollableVoxelElement;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

public class VoxelElementList extends ScrollableVoxelElement {

    private final Builder voxelBuilder;


    public VoxelElementList(Direction scrollDirection, Rectangle completeArea, Rectangle renderedArea, int scrollSpeed) {
        super(scrollDirection, completeArea, renderedArea, scrollSpeed);
        this.voxelBuilder = new Builder(this);
        updateSize();
    }

    public int getHeight() {
        int height = 0;
        for(VoxelElement voxelElement : this.voxelBuilder.getVoxelElementList()) {
            height = (int) (height + voxelElement.getBoundingBox().getHeight());
        }
        return height;
    }

    public int getWidth() {
        if(!this.voxelBuilder.getVoxelElementList().isEmpty()) {
            int width = 0;
            for (VoxelElement voxelElement : this.voxelBuilder.getVoxelElementList()) {
                width = (int) (width + voxelElement.getBoundingBox().getWidth());
            }
            width = width / this.voxelBuilder.getVoxelElementList().size();
            return width;
        } else {
            return 0;
        }
    }

    public void updateSize() {
        this.renderedArea.setSize(getWidth(), getHeight());
    }

    @Override
    public void drawRenderedArea(Rectangle renderedArea, UiUtilities uiUtilities) {
        for(VoxelElement voxelElement : getVoxelElements()) {
            int id = MenuHandler.getFocusedScreen().getVoxelElements().size() + getVoxelElements().indexOf(voxelElement);
            MenuHandler.getFocusedScreen().drawElement(id, uiUtilities, voxelElement);
        }

        uiUtilities.fillShape(Color.YELLOW, getScrollbar());

    }

    @Override
    public void onScroll() {
        updateElementsPos();
    }

    public void updateElementsPos() {
        for(VoxelElement voxelElement : getVoxelElements()) {
            Pair<Integer, Integer> pair = this.getVoxelBuilder().getElementPos(voxelElement);
            voxelElement.move(this.getRenderedArea().x + (voxelElement.getBoundingBox().width * pair.getFirst()), this.getRenderedArea().y + (voxelElement.getBoundingBox().height * pair.getSecond()));
        }
    }

    int prevX, prevY;

    @Override
    public boolean customMouseDrag(MouseEvent e) {
        if (this.getScrollDirection() == Direction.Y) {
            int y = e.getYOnScreen();
            int movedPixel = prevY - y;
            if (y < prevY) {
                this.renderedArea.setLocation(this.getRenderedArea().x, this.getRenderedArea().y - movedPixel);
            } else if (y > prevY) {
                this.renderedArea.setLocation(this.getRenderedArea().x, this.getRenderedArea().y - movedPixel);
            }
            updateElementsPos();
        } else {
            int x = e.getXOnScreen();
            int movedPixel = prevX - x;
            if (x < prevX) {
                this.getRenderedArea().setLocation(this.getRenderedArea().x - movedPixel, this.getRenderedArea().y);
            } else if (x > prevX) {
                this.getRenderedArea().setLocation(this.getRenderedArea().x - movedPixel, this.getRenderedArea().y);
            }
            updateElementsPos();
        }


        prevX = e.getXOnScreen();
        prevY = e.getYOnScreen();

        return true;
    }

    @Override
    public boolean customMouseMove(MouseEvent e) {
        prevX = e.getXOnScreen();
        prevY = e.getYOnScreen();
        return true;
    }

    public List<VoxelElement> getVoxelElements() {
        return voxelBuilder.getVoxelElementList();
    }

    public Builder getVoxelBuilder() {
        return voxelBuilder;
    }

    public Rectangle getScrollbar() {
        int scrollbarW = 10;
        if(this.getScrollDirection() == Direction.Y) {
            return new Rectangle(this.getRenderedArea().x + this.getWidth() - scrollbarW, this.getRenderedArea().y, scrollbarW, this.getHeight());
        } else {
            return new Rectangle(this.getRenderedArea().x, this.getRenderedArea().y - scrollbarW, this.getHeight(), scrollbarW);
        }
    }

    public static class Builder {
        private final HashMap<VoxelElement, Pair<Integer, Integer>> voxelElementMap;
        private final VoxelElementList voxelListElement;

        public Builder(VoxelElementList voxelListElement) {
            this.voxelListElement = voxelListElement;
            this.voxelElementMap = new HashMap<>();
        }

        public Builder addElement(int row, int column, VoxelElement voxelElement) {
            voxelElement.move(voxelListElement.getRenderedArea().x + (voxelElement.getBoundingBox().width * row), voxelListElement.getRenderedArea().y + (voxelElement.getBoundingBox().height * column));
            this.voxelElementMap.put(voxelElement, new Pair<>(row, column));
            return this;
        }

        public Builder addElements(List<VoxelElement> voxelElements) {
            for(VoxelElement voxelElement : voxelElements) {
                voxelElement.move(voxelListElement.getRenderedArea().x, voxelListElement.getRenderedArea().y + (voxelElement.getBoundingBox().height * voxelElements.indexOf(voxelElement)));
                this.voxelElementMap.put(voxelElement, new Pair<>(0, voxelElements.indexOf(voxelElement)));
            }
            return this;
        }

        public Builder updateElements(List<VoxelElement> voxelElements) {
            this.voxelElementMap.clear();
            for(VoxelElement voxelElement : voxelElements) {
                voxelElement.move(voxelListElement.getRenderedArea().x, voxelListElement.getRenderedArea().y + (voxelElement.getBoundingBox().height * voxelElements.indexOf(voxelElement)));
                this.voxelElementMap.put(voxelElement, new Pair<>(0, voxelElements.indexOf(voxelElement)));
            }
            return this;
        }

        public Builder updateSize() {
            voxelListElement.updateSize();
            return this;
        }

        public VoxelElementList build() {
            return this.voxelListElement;
        }

        public List<VoxelElement> getVoxelElementList() {
            return voxelElementMap.keySet().stream().toList();
        }

        public Pair<Integer, Integer> getElementPos(VoxelElement voxelElement) {
            return voxelElementMap.get(voxelElement);
        }
    }
}
