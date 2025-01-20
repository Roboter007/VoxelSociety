package de.Roboter007.voxelsociety.ui.screen.menus;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.screen.menus.menu.GameMenu;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.world.entity.Entities;

import java.awt.*;

public class InfoMenu extends GameMenu {

    public InfoMenu() {
        super("Info Menu", 0);
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();
        graphics2D.setFont(UiStyle.DEFAULT.getFont());

        // Draw Time
        graphics2D.setColor(Color.WHITE);
        String info = "Draw Time: Nano - " + uiUtilities.getVoxelPanel().timePassedNano + "; Seconds - " + uiUtilities.getVoxelPanel().timePassedSec;
        graphics2D.drawString(info, 5, 20);

        //Fps
        graphics2D.setColor(Color.CYAN);
        String fps_string = "Fps: " + VoxelPanel.fps;
        graphics2D.drawString(fps_string, 5, 40);

        //Tps
        graphics2D.setColor(Color.YELLOW);
        String tps_string = "Tps: " + Entities.PLAYER.world().tps;
        graphics2D.drawString(tps_string, 5, 60);

        //Coordinates
        graphics2D.setColor(Color.GREEN);
        String blockPos = "X: " + Entities.PLAYER.blockPosition.getX() / VoxelPanel.tileSize + ", Y: " + Entities.PLAYER.blockPosition.getY() / VoxelPanel.tileSize;
        graphics2D.drawString(blockPos, 5, 80);

        //World
        graphics2D.setColor(Color.BLUE);
        String world_string = "World: " + Entities.PLAYER.world().getName() + " in Path: " + Entities.PLAYER.world().getSavePath();
        graphics2D.drawString(world_string, 5, 100);

        //World Seed
        graphics2D.setColor(Color.ORANGE);
        String world_seed_string = "Seed: " + Entities.PLAYER.world().getSeed() + ", maxXSize: " + Entities.PLAYER.world.getMaxXSize() + ", maxYSize: " + Entities.PLAYER.world.getMaxYSize();
        graphics2D.drawString(world_seed_string, 5, 120);

        //Selected Block
        graphics2D.setColor(Color.MAGENTA);
        String selectedBlockInfo_string = "Selected Block: " + Entities.PLAYER.targetedBlock.getBlockEntry().getName();
        graphics2D.drawString(selectedBlockInfo_string, 5, 140);
    }

    @Override
    public boolean deleteOnChange(GameMenu newMenu) {
        return false;
    }
}
