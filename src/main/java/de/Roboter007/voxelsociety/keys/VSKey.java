package de.Roboter007.voxelsociety.keys;

import de.Roboter007.voxelsociety.ui.screen.menus.menu.GameMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.ui.screen.menus.InGameMenu;
import de.Roboter007.voxelsociety.ui.screen.menus.InfoMenu;
import de.Roboter007.voxelsociety.ui.screen.menus.PauseMenu;
import de.Roboter007.voxelsociety.utils.VoxelUtils;
import de.Roboter007.voxelsociety.world.entity.Entities;

import java.awt.event.KeyEvent;

public enum VSKey {
    //ToDo: add Place and Break Key Bind -> -1 right click, -2 middle click, -3 left click for keyNumber
    // + Fix Walk Speed when two walk keys are pressed
    WALK_UP(KeyCategory.WALKING, KeyEvent.VK_W, false),
    WALK_DOWN(KeyCategory.WALKING, KeyEvent.VK_S, false),
    WALK_LEFT(KeyCategory.WALKING, KeyEvent.VK_A, false),
    WALK_RIGHT(KeyCategory.WALKING, KeyEvent.VK_D, false),
    ESCAPE(() -> {
        if(Entities.PLAYER.isInWorld()) {
            Entities.PLAYER.showPauseScreen = !Entities.PLAYER.showPauseScreen;

            if (Entities.PLAYER.showPauseScreen) {
                if (MenuHandler.getFocusedScreen().getClass() != PauseMenu.class) {
                    MenuHandler.setFocusedScreen(new PauseMenu());
                }
            } else {
                if (MenuHandler.getFocusedScreen() instanceof PauseMenu) {
                    MenuHandler.removeFocusedScreen();
                    VoxelUtils.unpauseGame();
                }
            }
        }
    } , KeyCategory.PAUSE, KeyEvent.VK_ESCAPE, false, InGameMenu.class, PauseMenu.class),
    DEBUG_DRAW_TIME(() -> {
        if(Entities.PLAYER.isInWorld()) {
            Entities.PLAYER.showInfoScreen = !Entities.PLAYER.showInfoScreen;

            if (Entities.PLAYER.showInfoScreen) {

                MenuHandler.setFocusedScreen(new InfoMenu());

            } else {
                if (MenuHandler.getFocusedScreen() instanceof InfoMenu) {
                    MenuHandler.removeFocusedScreen();
                }
            }
        }
    }, KeyCategory.DEBUG, KeyEvent.VK_F1, false, InGameMenu.class, InfoMenu.class),
    DEBUG_COLLISION(KeyCategory.DEBUG, KeyEvent.VK_F2, true, InGameMenu.class, InfoMenu.class);


    private final Runnable actionOnPressed;
    private final int keyNumber;
    private final KeyCategory keyCategory;
    private final boolean toggleable;
    private boolean pressed;
    private final Class<? extends GameMenu>[] supportedScreens;

    VSKey(Runnable actionOnPressed, KeyCategory keyCategory, int keyNumber, boolean toggleable) {
        this.actionOnPressed = actionOnPressed;
        this.supportedScreens = null;
        this.keyCategory = keyCategory;
        this.keyNumber = keyNumber;
        this.toggleable = toggleable;
    }

    @SafeVarargs
    VSKey(Runnable actionOnPressed, KeyCategory keyCategory, int keyNumber, boolean toggleable, Class<? extends GameMenu>... supportedScreens) {
        this.actionOnPressed = actionOnPressed;
        this.supportedScreens = supportedScreens;
        this.keyCategory = keyCategory;
        this.keyNumber = keyNumber;
        this.toggleable = toggleable;
    }


    VSKey(KeyCategory keyCategory, int keyNumber, boolean toggleable) {
        this(null, keyCategory, keyNumber, toggleable);
    }

    @SafeVarargs
    VSKey(KeyCategory keyCategory, int keyNumber, boolean toggleable, Class<? extends GameMenu>... supportedScreens) {
        this(null, keyCategory, keyNumber, toggleable, supportedScreens);
    }

    public void runAction() {
        if(this.actionOnPressed != null) {
            this.actionOnPressed.run();
        }
    }

    public KeyCategory getKeyCategory() {
        return keyCategory;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setPressed(boolean pressed) {
        if(supportsScreen()) {
            this.pressed = pressed;
        }
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isToggleable() {
        return toggleable;
    }

    public boolean supportsScreen() {
        if(supportedScreens != null) {
            for (Class<? extends GameMenu> supScreen : supportedScreens) {
                if (MenuHandler.isScreenActive(supScreen)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
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
