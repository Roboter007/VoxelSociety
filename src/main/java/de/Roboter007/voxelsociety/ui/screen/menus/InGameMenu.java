package de.Roboter007.voxelsociety.ui.screen.menus;

import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.screen.menus.menu.GameMenu;
import de.Roboter007.voxelsociety.ui.screen.overlays.overlay.GameOverlay;
import de.Roboter007.voxelsociety.world.entity.Entities;

import java.util.ArrayList;
import java.util.List;

//ToDo: add Overlay Cache for every Menu (for example for the PauseMenu and SelectionBar) - new System
// -> Overlay -> new Type of Screens, seperation of InGameScreens and normal Menus -> only these should be able to kind of stack and have a render order
public class InGameMenu extends GameMenu {

    public static List<GameOverlay> GAME_OVERLAYS = new ArrayList<>();

    public InGameMenu() {
        super("InGameMenu");
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        Entities.PLAYER.drawScreen(uiUtilities.getGraphics2D());
        Entities.PLAYER.drawPlayer(uiUtilities.getGraphics2D());
        Entities.PLAYER.drawSelectionBar(uiUtilities.getGraphics2D());
    }

    @Override
    public boolean deleteOnChange(GameMenu newMenu) {
        return !(newMenu instanceof InfoMenu || newMenu instanceof PauseMenu);
    }
}
