package org.firstinspires.ftc.teamcode.utils;

public class RegressionTable {

    private static final int MIN_DIST = 30;
    private static final int MAX_DIST = 75;

   //table values
    private static final double[] TARGETS = {
            2942, 2919, 2896, 2873, 2850, 2827, // 30 - 35
            2804, 2781, 2758, 2735, 2712, 2688, // 36 - 41
            2665, 2642, 2619, 2596, 2573, 2550, // 42 - 47
            2556, 2562, 2569, 2575, 2581, 2588, // 48 - 53
            2594, 2600, 2604, 2607, 2611, 2614, // 54 - 59
            2618, 2621, 2625, 2629, 2632, 2636, // 60 - 65
            2639, 2643, 2646, 2650, 2654, 2657, // 66 - 71
            2661, 2664, 2668, 2671              // 72 - 75
    };

    /**
     * gets rpm target
     */
    public static double getTarget(double distance) {
        int index = (int) Math.round(distance) - MIN_DIST;

        if (index < 0) return TARGETS[0];
        if (index >= TARGETS.length) return TARGETS[TARGETS.length - 1];

        return TARGETS[index];
    }
}
