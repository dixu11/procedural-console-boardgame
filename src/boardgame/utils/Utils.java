package boardgame.utils;


import boardgame.components.entity.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {        //Class for helper static methods

    public static List<String> getUnmodifiableList(String[] strings) {
        return Collections.unmodifiableList(Arrays.asList(strings));
    }

    public static boolean roll(double chance) {
        return Math.random() <= chance;
    }

    public static Competitor castToCompetitor(Entity entity) {
        if (entity instanceof Competitor) {
            return (Competitor) entity;
        }
        throw new IllegalStateException("Casting Entity to Competitor impossible");
    }

    public static String getStars(int howMany) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < howMany; i++) {
            builder.append("*");
        }
        return builder.toString();
    }

}
