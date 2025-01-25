package de.Roboter007.voxelsociety.config.values;

import de.Roboter007.voxelsociety.config.holder.StringHolder;

public class DoubleConfigValue extends VoxelConfigValue<Double> {


    public DoubleConfigValue(Double value) {
        super(value);
    }

    public DoubleConfigValue(StringHolder stringHolder) {
        super(stringHolder);
    }

    @Override
    public String toString() {
        return "double$" + this.getValue();
    }

    @Override
    public Double fromString(String str) {
        String[] splitData = str.split("\\$");
        String data = splitData[1];
        if(data.equals("null")) {
            return null;
        } else {
            return Double.parseDouble(data);
        }
    }
}
