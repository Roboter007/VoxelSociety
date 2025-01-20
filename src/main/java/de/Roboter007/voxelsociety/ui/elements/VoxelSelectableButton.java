package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class VoxelSelectableButton extends VoxelButton {

    private boolean selected;
    private final BiConsumer<VoxelSelectableButton, MouseEvent> task;

    public VoxelSelectableButton(String text, UiStyle uiStyle, int x, int y, int width, int height, BiConsumer<VoxelSelectableButton, MouseEvent> task) {
        super(text, uiStyle, x, y, width, height);
        this.task = task;
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();
        if(!this.isMouseOverElement()) {
            graphics2D.setColor(Color.BLACK);
        } else {
            graphics2D.setColor(Color.DARK_GRAY);
        }
        graphics2D.fillRect(x, y, width, height);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawRect(x, y, width, height);

        if(this.text != null) {
            int x = (this.x + ((this.width / 2) - ((int) this.uiStyle.getFont().getStringBounds(this.text, graphics2D.getFontRenderContext()).getWidth() / 2))) - 2;
            int y = (this.y + ((this.height / 2) + ((int) this.uiStyle.getFont().getStringBounds(this.text, graphics2D.getFontRenderContext()).getHeight() / 2))) - 2;

            uiUtilities.drawString(this.text, uiStyle, false, x, y);
        }
    }

    @Override
    public boolean isMouseOverElement() {
        boolean mouseOverElement = super.isMouseOverElement();
        if(mouseOverElement) {
            return true;
        } else {
            return selected;
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        task.accept(this, e);
    }
}
