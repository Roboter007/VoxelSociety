package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiStyle;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class VoxelTaskButton extends VoxelButton {

    protected final Consumer<MouseButton> action;

    public VoxelTaskButton(String text, UiStyle uiStyle, int x, int y, int width, int height, Runnable action) {
        super(text, uiStyle, x, y, width, height);
        this.action = (_) -> action.run();

    }

    public VoxelTaskButton(String text, UiStyle uiStyle, int x, int y, int width, int height, Consumer<MouseButton> action) {
        super(text, uiStyle, x, y, width, height);
        this.action = action;

    }

    public VoxelTaskButton(UiStyle uiStyle, int x, int y, int width, int height, Consumer<MouseButton> action) {
        super(uiStyle, x, y, width, height);
        this.action = action;
    }

    @Override
    public void onMousePress(MouseEvent e) {
        if(isLeftClick(e)) {
            action.accept(MouseButton.LEFT_CLICK);
        } else if (isMiddleClick(e)) {
            action.accept(MouseButton.MIDDLE_CLICK);
        } else if (isRightClick(e)) {
            action.accept(MouseButton.RIGHT_CLICK);
        }
    }

    public enum MouseButton {
        LEFT_CLICK(0),
        MIDDLE_CLICK(1),
        RIGHT_CLICK(2);

        private final int id;

        MouseButton(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
