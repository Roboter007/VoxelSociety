package de.Roboter007.voxelsociety.utils;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.utils.texture.Texture;
import de.Roboter007.voxelsociety.utils.texture.PathLocation;

import javax.swing.*;

public class VoxelFrame extends JFrame {

    public VoxelFrame(VoxelPanel voxelPanel) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(VoxelSociety.GAME_NAME);

        this.add(voxelPanel);
        voxelPanel.startGameThread();

        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setIconImage(getIcon().load());
    }

    public Texture getIcon() {
        return Texture.voxelSociety("icon", "icon.png");
    }
}
