package cz.goldzone.housing.Util;

import java.util.*;

public class HashMapUtil {
    public static Map<String, Integer> sortByValueDescending(final Map<String, Integer> map) {
        // Create a list from elements of HashMap
        final List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        // Sort the list
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
        // Put data to temp hashmap
        final Map<String, Integer> temp = new LinkedHashMap<>();
        for (final Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
