package de.Roboter007.voxelsociety.config;


import de.Roboter007.voxelsociety.config.values.VoxelConfigValue;

public class VoxelConfigOption<T> {

    private String name;
    private VoxelConfigValue<T> configValue;

    public VoxelConfigOption(String name, VoxelConfigValue<T> configValue) {
        this.configValue = configValue;
        this.name = name;
    }

    public VoxelConfigOption(String name) {
        this.name = name;
    }

    public VoxelConfigValue<T> getConfigValue() {
        return configValue;
    }

    public String getName() {
        return name;
    }


    public void setConfigValue(VoxelConfigValue<T> value) {
        this.configValue = value;
    }


    @Override
    public String toString() {
        return name + "=" + configValue;

    }

    @SuppressWarnings("unchecked")
    public void fromString(String data) {
        String[] dataList = data.split("=");

         this.name = dataList[0];
         System.out.println("Test 10");
         this.configValue = (VoxelConfigValue<T>) VoxelConfigValue.newFromString(dataList[1]);
    }

    public static VoxelConfigOption<?> newConfigValue(String data) {
        String[] dataList = data.split("=");

        return new VoxelConfigOption<>(dataList[0], VoxelConfigValue.newFromString(dataList[1]));
    }


}
