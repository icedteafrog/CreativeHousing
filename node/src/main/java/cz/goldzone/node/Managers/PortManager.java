package cz.goldzone.node.Managers;

import java.util.ArrayList;

public class PortManager {
    private static ArrayList<Integer> usedPorts = new ArrayList<>();

    public static Integer getAvailPort() {
        int port = (int) (Math.random() * 35535.0D + 1.0D + 30000.0D);
        while (usedPorts.contains(Integer.valueOf(port)))
            port = (int) (Math.random() * 35535.0D + 1.0D + 30000.0D);
        return Integer.valueOf(port);
    }
}
