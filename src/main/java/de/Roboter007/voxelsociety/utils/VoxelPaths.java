package de.Roboter007.voxelsociety.utils;

import de.Roboter007.voxelsociety.VoxelSociety;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VoxelPaths {


    public VoxelPaths() {
    }

    public Path getVoxelGamePath() {
        Path path = Path.of(System.getProperty("user.home") + "/AppData/Roaming/" + VoxelSociety.GAME_NAME);
        if(path.toFile().mkdirs()) {
            System.out.println("Created Game Path!");
        }
        return path;
    }

    public static List<String> readLines(String path) {
        List<String> list = new ArrayList<>();
        try {
            final BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                list.add(line);
            }
            in.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
