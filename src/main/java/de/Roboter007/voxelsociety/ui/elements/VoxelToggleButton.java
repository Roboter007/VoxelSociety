package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class VoxelToggleButton extends VoxelButton {

    private boolean state;
    private final Consumer<Boolean> action;
    private final String originText;

    public VoxelToggleButton(String text, UiStyle uiStyle, int x, int y, int width, int height, Consumer<Boolean> action) {
        super(text, uiStyle, x, y, width, height);
        this.action = action;
        this.originText = text;
    }

    public VoxelToggleButton(UiStyle uiStyle, int x, int y, int width, int height, Consumer<Boolean> action) {
        super(uiStyle, x, y, width, height);
        this.action = action;
        this.originText = null;
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.fillRect(x, y, width, height);

        if(this.isMouseOverElement()) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawRect(x, y, width, height);
        }

        if(this.text != null) {
            Color color;
            if(state) {
                color = Color.GREEN;
            } else {
                color = Color.RED;
            }
            int x = (this.x + ((this.width / 2) - ((int) this.uiStyle.getFont().getStringBounds(this.getText(), graphics2D.getFontRenderContext()).getWidth() / 2))) - 2;
            int y = (this.y + ((this.height / 2) + ((int) this.uiStyle.getFont().getStringBounds(this.getText(), graphics2D.getFontRenderContext()).getHeight() / 2))) - 2;

            uiUtilities.drawString(this.getText(), uiStyle.getFont(), color, x, y);
        }
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        if(isRightClick(e) || isLeftClick(e)) {
            state = !state;
            this.action.accept(state);
        }
    }

    @Override
    public String getText() {
        if(originText != null) {
            return originText + this.getState();
        } else {
            return Boolean.toString(this.getState());
        }
    }
}
