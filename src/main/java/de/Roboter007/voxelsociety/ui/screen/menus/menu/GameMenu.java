package de.Roboter007.voxelsociety.ui.screen.menus.menu;

import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.elements.VoxelTextField;
import de.Roboter007.voxelsociety.ui.elements.listener.VoxelElementHandler;
import de.Roboter007.voxelsociety.ui.elements.lists.VoxelElementList;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.ui.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public abstract class GameMenu extends Screen implements Menu {

    private int elementId;
    private final String name;
    public VoxelElementHandler inputHandler;

    public GameMenu(String name, int renderOrder) {
        this.name = name;
        if(!MenuHandler.CACHED_VOXEL_ELEMENTS.containsKey(name)) {
            MenuHandler.CACHED_VOXEL_ELEMENTS.put(name, new ArrayList<>());
        }
        this.inputHandler = new VoxelElementHandler();
        updateElements();
        MenuHandler.INPUT_HANDLER = this.inputHandler;
        if(renderOrder == -1) {
            MenuHandler.ACTIVE_MENU_LIST.add(this);
        } else {
            MenuHandler.ACTIVE_MENU_LIST.add(renderOrder, this);
        }
    }

    //ToDo: Remove renderOrder Data because it will soon not be needed anymore -> Overlays
    public GameMenu(String name) {
        this(name, -1);
    }

    public String getName() {
        return name;
    }

    public boolean deleteOnChange(GameMenu newMenu) {
        return true;
    }

    public void updateElements() {
        for(VoxelElement voxelElement : MenuHandler.getCurrentElementsOnScreen()) {
            voxelElement.update();
        }
    }



    public VoxelElement getOrCreateElement(int elementId, VoxelElement voxelElement) {
        List<VoxelElement> voxelElementList = MenuHandler.CACHED_VOXEL_ELEMENTS.get(this.getName());
        if(voxelElementList != null && !voxelElementList.isEmpty() && voxelElementList.size() > elementId) {
            VoxelElement cachedVoxelElement = voxelElementList.get(elementId);
            if (cachedVoxelElement != null) {
                if(cachedVoxelElement instanceof VoxelTextField textField) {
                    if (textField.getBoundingBox().getX() == cachedVoxelElement.getBoundingBox().getX()
                            && textField.getBoundingBox().getY() == cachedVoxelElement.getBoundingBox().getY()
                            && textField.getBoundingBox().getWidth() == cachedVoxelElement.getBoundingBox().getWidth()) {
                        return cachedVoxelElement;
                    } else {

                        voxelElementList.set(elementId, voxelElement);
                        return voxelElement;
                    }
                } else {
                    if (voxelElement.getBoundingBox().equals(cachedVoxelElement.getBoundingBox())) {
                        return cachedVoxelElement;
                    } else {

                        if(cachedVoxelElement instanceof VoxelElementList voxelListElement) {
                            voxelListElement.setHeight(((VoxelElementList) voxelElement).getHeight());
                        }

                        //ToDo: besserer Bug Fix - only update on window resizing
                        voxelElementList.set(elementId, voxelElement);
                        return voxelElement;
                    }
                }

            } else {
                MenuHandler.addElement(MenuHandler.getFocusedScreen().getName(), voxelElement);
                return voxelElement;
            }
        } else {
            MenuHandler.addElement(MenuHandler.getFocusedScreen().getName(), voxelElement);
            return voxelElement;
        }
    }

    public void drawElement(int id, UiUtilities uiUtilities, VoxelElement voxelElement) {
        this.getOrCreateElement(id, voxelElement).draw(uiUtilities);
    }

    public VoxelElement getElement(int elementId) {
        return MenuHandler.CACHED_VOXEL_ELEMENTS.get(this.getName()).get(elementId);
    }

    public void setElement(int elementId, VoxelElement voxelElement) {
        MenuHandler.CACHED_VOXEL_ELEMENTS.get(this.getName()).set(elementId, voxelElement);
    }

    public List<VoxelElement> getVoxelElements() {
        return MenuHandler.CACHED_VOXEL_ELEMENTS.get(this.getName());
    }


    public abstract void draw(UiUtilities uiUtilities);


    public void onChangeTo() {}

    public void onChangeFrom(GameMenu newMenu) {}

    public void onDelete() {}

    //ToDo: implement System
    @Override
    public Menu lastMenu() {
        return null;
    }
}
