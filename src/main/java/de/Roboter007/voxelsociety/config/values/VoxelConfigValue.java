package de.Roboter007.voxelsociety.config.values;

import de.Roboter007.voxelsociety.config.holder.StringHolder;

public abstract class VoxelConfigValue<V> {

    private V value;

    public VoxelConfigValue(V value) {
        this.value = value;
    }

    public VoxelConfigValue(StringHolder stringHolder) {
        this.value = fromString(stringHolder.string());
    }


    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public abstract String toString();
    public abstract V fromString(String str);

    public static VoxelConfigValue<?> newFromString(String str) {
        String[] splitData = str.split("\\$");

        String dataType = splitData[0];
        switch (dataType) {
            case "boolean" -> {
                return new BooleanConfigValue(new StringHolder(str));
            }
            case "byte" -> {
                return new ByteConfigValue(new StringHolder(str));
            }
            case "char" -> {
                return new CharConfigValue(new StringHolder(str));
            }
            case "double" -> {
                return new DoubleConfigValue(new StringHolder(str));
            }
            case "float" -> {
                return new FloatConfigValue(new StringHolder(str));
            }
            case "integer" -> {
                return new IntConfigValue(new StringHolder(str));
            }
            case "long" -> {
                return new LongConfigValue(new StringHolder(str));
            }
            case "short" -> {
                return new ShortConfigValue(new StringHolder(str));
            }
            case "string" -> {
                return new StringConfigValue(new StringHolder(str));
            }
            default -> {
                System.out.println("Error! This Config Value does not exist!");
                return null;
            }
        }
    }
}
