package de.Roboter007.voxelsociety.keys;

import java.awt.event.KeyEvent;

public enum VSKey {
    WALK_UP(KeyCategory.WALKING, KeyEvent.VK_R, false),
    WALK_DOWN(KeyCategory.WALKING, KeyEvent.VK_S, false),
    WALK_LEFT(KeyCategory.WALKING, KeyEvent.VK_A, false),
    WALK_RIGHT(KeyCategory.WALKING, KeyEvent.VK_D, false),
    DEBUG_DRAW_TIME(KeyCategory.DEBUG, KeyEvent.VK_F1, true),
    DEBUG_COLLISION(KeyCategory.DEBUG, KeyEvent.VK_F2, true);

    private final int keyNumber;
    private final KeyCategory keyCategory;
    private final boolean toggleable;
    private boolean pressed;

    VSKey(KeyCategory keyCategory, int keyNumber, boolean toggleable) {
        this.keyCategory = keyCategory;
        this.keyNumber = keyNumber;
        this.toggleable = toggleable;
    }

    public KeyCategory getKeyCategory() {
        return keyCategory;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isToggleable() {
        return toggleable;
    }

    public static boolean isKeyPressedByCategory(KeyCategory keyCategory) {
        boolean pressed = false;
        for(VSKey key : values()) {
            if (key.isPressed() && key.getKeyCategory() == keyCategory) {
                pressed = true;
                break;
            }
        }
        return pressed;
    }
}
