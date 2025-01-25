package de.Roboter007.voxelsociety.config;


import de.Roboter007.voxelsociety.config.values.VoxelConfigValue;

public class VoxelConfigOption<T> {

    private String name;
    private VoxelConfigValue<T> data;

    public VoxelConfigOption(String name, VoxelConfigValue<T> data) {
        this.data = data;
        this.name = name;
    }

    public VoxelConfigOption(String name) {
        this.name = name;
    }

    public VoxelConfigValue<T> getValue() {
        return data;
    }

    public String getName() {
        return name;
    }


    public void setValue(VoxelConfigValue<T> value) {
        this.data = value;
    }


    @Override
    public String toString() {
        return name + "=" + data;

    }

    @SuppressWarnings("unchecked")
    public void fromString(String data) {
        String[] dataList = data.split("=");

         this.name = dataList[0];
         System.out.println("Test 10");
         this.data = (VoxelConfigValue<T>) VoxelConfigValue.newFromString(dataList[1]);
    }

    public static VoxelConfigOption<?> newConfigValue(String data) {
        String[] dataList = data.split("=");

        return new VoxelConfigOption<>(dataList[0], VoxelConfigValue.newFromString(dataList[1]));
    }


}
