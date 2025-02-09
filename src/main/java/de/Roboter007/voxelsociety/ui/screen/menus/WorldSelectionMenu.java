package de.Roboter007.voxelsociety.ui.screen.menus;

import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.elements.VoxelSelectableButton;
import de.Roboter007.voxelsociety.ui.elements.VoxelTaskButton;
import de.Roboter007.voxelsociety.ui.elements.lists.VoxelElementList;
import de.Roboter007.voxelsociety.ui.elements.lists.VoxelListEntry;
import de.Roboter007.voxelsociety.ui.elements.slider.ScrollDirection;
import de.Roboter007.voxelsociety.ui.screen.AmbientMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.Worlds;
import de.Roboter007.voxelsociety.world.entity.Entities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

//ToDo: This Menu duplicates already existing buttons -> fix VoxelElementList
public class WorldSelectionMenu extends AmbientMenu {

    public WorldSelectionMenu() {
        super("World Selection Menu");
        Worlds.loadWorlds();
    }


    @Override
    public void draw(UiUtilities uiUtilities) {
        super.draw(uiUtilities);
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        RecCalculator recCalculator = new RecCalculator();

        graphics2D.setColor(new Color(189, 189, 189));
        graphics2D.fillRect(0, 100, VoxelPanel.screenWidth, VoxelPanel.screenHeight - 300);

        recCalculator.calc(1000, VoxelPanel.screenHeight);
        drawElement(0, uiUtilities, new VoxelElementList(ScrollDirection.VERTICAL, 0, 200, 20, getWorldSelButtons(), 10));

        graphics2D.setColor(new Color(42, 42, 42));
        graphics2D.fillRect(0, VoxelPanel.screenHeight - 200, VoxelPanel.screenWidth, VoxelPanel.screenHeight);

        recCalculator.calc(200, 80);
        drawElement(1, uiUtilities, new VoxelTaskButton("Back", UiStyle.DEFAULT, recCalculator.getX(), VoxelPanel.screenHeight - 90, 200, 80, () -> MenuHandler.setFocusedScreen(new MainMenu())));

        drawElement(2, uiUtilities, new VoxelTaskButton("Delete", UiStyle.DEFAULT, recCalculator.getX() + 220, VoxelPanel.screenHeight - 90, 200, 80, () -> {
            if(this.getElement(3) instanceof VoxelElementList voxelElementList) {
                for(VoxelListEntry<?> voxelElement : voxelElementList.getVoxelElements()) {
                    if(voxelElement.getElement() instanceof VoxelSelectableButton voxelSelectableButton) {
                        if (voxelSelectableButton.isSelected()) {
                            String worldName = voxelSelectableButton.getText();
                            World world = Worlds.byName(worldName);
                            MenuHandler.setFocusedScreen(new ConfirmMenu("Delete this World? - There is no way to save this World...",this, () -> {
                                System.out.println("Deleting World... " + world.getName());
                                world.deleteWorld(this::updateElements);
                            }));
                        } else {
                            voxelSelectableButton.setSelected(false);
                        }
                    }
                }
            }
        }));

        drawElement(3, uiUtilities, new VoxelTaskButton("Create New World", UiStyle.DEFAULT, recCalculator.getX() - 220, VoxelPanel.screenHeight - 90, 200, 80, () -> MenuHandler.setFocusedScreen(new WorldCreationMenu())));

        graphics2D.setColor(new Color(42, 42, 42));
        graphics2D.fillRect(0, 0, VoxelPanel.screenWidth, 100);

        graphics2D.setFont(new Font("Century Schoolbook Bold", Font.BOLD, 25));
        Rectangle2D stringBounds = graphics2D.getFont().getStringBounds(this.getName(), graphics2D.getFontRenderContext());
        recCalculator.calc((int) stringBounds.getWidth(), (int) stringBounds.getHeight());

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(this.getName(), recCalculator.getX(), 60);


    }

    public static List<VoxelElement> getWorldSelButtons() {
        List<VoxelElement> voxelElements = new ArrayList<>();
        for (World world : Worlds.REGISTERED_WORLDS) {
            voxelElements.add(new VoxelSelectableButton(world.getName(), UiStyle.DEFAULT, 0, 110 + (Worlds.worldId(world) * 100), VoxelPanel.screenWidth, 100, (button, mouseEvent) -> {
                if(!button.isSelected()) {
                    if (SwingUtilities.isRightMouseButton(mouseEvent) || SwingUtilities.isLeftMouseButton(mouseEvent)) {
                        button.setSelected(!button.isSelected());
                    }
                } else {
                    if(mouseEvent.getClickCount() == 2) {
                        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                            Entities.PLAYER.changeWorld(world);
                            MenuHandler.setFocusedScreen(new InGameMenu());
                        } else if (SwingUtilities.isMiddleMouseButton(mouseEvent)) {

                        } else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                            MenuHandler.setFocusedScreen(new WorldConfigurationMenu(world));
                        }
                    } else {
                        if (SwingUtilities.isRightMouseButton(mouseEvent) || SwingUtilities.isLeftMouseButton(mouseEvent)) {
                            button.setSelected(!button.isSelected());
                        }
                    }
                }

            }));
        }
        return voxelElements;
    }
}
