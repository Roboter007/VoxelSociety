package de.Roboter007.voxelsociety.config.values;

import de.Roboter007.voxelsociety.config.VoxelConfig;

public class VoxelConfigValue<T> {

    private Class<?> clazz;
    private T value;
    private String name;

    public VoxelConfigValue(Class<?> clazz, String name, T value) {
        if(value.getClass() != clazz) {
            System.out.println("Error! Value or Fallback have the wrong Class!!! current: " + value.getClass() + ", expected: " + clazz);
        }
        this.clazz = clazz;
        this.value = value;
        this.name = name;}

    public VoxelConfigValue(Class<?> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


    public void setValue(T value) {
        this.value = value;
    }

    public void addToConfig(VoxelConfig voxelConfig) {
        voxelConfig.addOption(this);
    }

    @Override
    public String toString() {
        return this.clazz.getCanonicalName() + "=" + name + "=" + value;
    }

    @SuppressWarnings("unchecked")
    public void fromString(String data) {
        String[] dataList = data.split("=");

        try {
            this.clazz = Class.forName(dataList[0]);
            this.name = dataList[1];
            this.value = (T) dataList[2];
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> VoxelConfigValue<T> newConfigValue(String data) {
        try {

            String[] dataList = data.split("=");

            return new VoxelConfigValue<>(Class.forName(dataList[0]), dataList[1], (T) dataList[2]);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
