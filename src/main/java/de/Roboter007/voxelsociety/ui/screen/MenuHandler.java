package de.Roboter007.voxelsociety.ui.screen;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.elements.listener.VoxelElementHandler;
import de.Roboter007.voxelsociety.ui.screen.menus.menu.GameMenu;
import de.Roboter007.voxelsociety.ui.screen.menus.InGameMenu;
import de.Roboter007.voxelsociety.ui.screen.menus.LoadingMenu;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MenuHandler {

    //ToDo: Fix/Implement the removal of unused Elements -> if screen is resized, there still are some weird unused Elements
    public static final ConcurrentHashMap<String, List<VoxelElement>> CACHED_VOXEL_ELEMENTS = new ConcurrentHashMap<>();
    //ToDo: Fix ConcurrentModificationException when this is not a synchronizedList
    public static final List<GameMenu> ACTIVE_MENU_LIST = Collections.synchronizedList(new ArrayList<>());
    //ToDo: kleines System Rewrite???
    private static GameMenu FOCUSED = new LoadingMenu();
    public static VoxelElementHandler INPUT_HANDLER;

    public static void setFocusedScreen(GameMenu menu) {
        if(FOCUSED != menu) {
            FOCUSED.onChangeFrom(menu);
            if(FOCUSED.deleteOnChange(menu)) {
                MenuHandler.removeScreen(FOCUSED);
            }

            FOCUSED = menu;

            FOCUSED.onChangeTo();
        }
    }


    public static void removeScreen(GameMenu menuToRemove) {
        System.out.println("onDelete for: " + menuToRemove.getName());
        menuToRemove.onDelete();
        //CACHED_VOXEL_ELEMENTS.replace(screenToRemove.getName(), new ArrayList<>());
        ACTIVE_MENU_LIST.remove(menuToRemove);
    }

    public static void removeFocusedScreen() {
        removeScreen(FOCUSED);
        FOCUSED = ACTIVE_MENU_LIST.getFirst();
    }

    public static GameMenu getFocusedScreen() {
        return FOCUSED;
    }

    public static boolean isScreenFocused(GameMenu menu) {
        return FOCUSED == menu;
    }

    public static void drawAllScreens(UiUtilities uiUtilities) {
        if (FOCUSED != null) {
            for (GameMenu menu : ACTIVE_MENU_LIST.reversed()) {
                menu.draw(uiUtilities);
            }
        }

        uiUtilities.getGraphics2D().setFont(UiStyle.DEFAULT.getFont());
        uiUtilities.getGraphics2D().setColor(Color.CYAN);
        uiUtilities.getGraphics2D().setFont(uiUtilities.getGraphics2D().getFont().deriveFont(20F));
        uiUtilities.getGraphics2D().drawString(ACTIVE_MENU_LIST.toString(), 0, 20);

        uiUtilities.getGraphics2D().setFont(UiStyle.DEFAULT.getFont());
        uiUtilities.getGraphics2D().setColor(Color.ORANGE);
        uiUtilities.getGraphics2D().setFont(uiUtilities.getGraphics2D().getFont().deriveFont(10F));

        for(String s : CACHED_VOXEL_ELEMENTS.keySet()) {
            List<VoxelElement> voxelElements = CACHED_VOXEL_ELEMENTS.get(s);
            for(VoxelElement v : voxelElements) {
                uiUtilities.getGraphics2D().drawString(s + ", " + v.toString(), 0, 40 + (20 * voxelElements.indexOf(v)));
            }
        }

        // Logger for Cached Elements
        //System.out.println(CACHED_VOXEL_ELEMENTS);
    }

    public static void addElement(String screenName, VoxelElement element) {
        List<VoxelElement> voxelElements = CACHED_VOXEL_ELEMENTS.get(screenName);

        if(voxelElements == null) {
            voxelElements = new ArrayList<>();
            voxelElements.add(element);
            CACHED_VOXEL_ELEMENTS.put(screenName, voxelElements);
        } else {
            if (voxelElements.isEmpty() || !voxelElements.contains(element)) {
                voxelElements.add(element);
                CACHED_VOXEL_ELEMENTS.replace(screenName, voxelElements);
            }
        }

    }

    public static List<VoxelElement> getCurrentElementsOnScreen() {
        if(FOCUSED != null) {
            return CACHED_VOXEL_ELEMENTS.get(FOCUSED.getName());
        }else {
            return List.of();
        }
    }

    public static boolean isScreenActive(Class<? extends GameMenu> screen) {
        for(GameMenu menuInList : ACTIVE_MENU_LIST) {
            if(menuInList.getClass() == screen) {
                return true;
            }
        }
        return false;
    }

    public static GameMenu getScreenIfActive(Class<? extends GameMenu> screenClass) {
        for(GameMenu menu : ACTIVE_MENU_LIST) {
            if(menu.getClass() == screenClass) {
                return menu;
            }
        }
        return null;
    }

    public static void removeScreen(Class<? extends GameMenu> screenClass) {
        for(GameMenu menu : ACTIVE_MENU_LIST) {
            if(menu.getClass() == screenClass) {
                removeScreen(menu);
            }
        }
    }

    public static boolean isInGame() {
        return isScreenActive(InGameMenu.class);
    }


}
