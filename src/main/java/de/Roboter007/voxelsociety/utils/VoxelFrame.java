package de.Roboter007.voxelsociety.utils;

import javax.swing.*;

public class VoxelFrame extends JFrame {

    public VoxelFrame(VoxelPanel voxelPanel) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(voxelPanel);
        voxelPanel.startGameThread();

        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setIconImage(getIcon().load());
    }

    public TextureLocation getIcon() {
        return TextureLocation.voxelSociety("icon", "voxelsociety-icon.png");
    }
}
