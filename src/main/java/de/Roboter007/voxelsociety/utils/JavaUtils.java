package de.Roboter007.voxelsociety.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaUtils {

    @SafeVarargs
    public static <E> ArrayList<E> arrayListOf(E... e) {
        return new ArrayList<>(Arrays.stream(e).toList());
    }
}
