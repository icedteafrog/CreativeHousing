package cz.goldzone.housing.Task;

import cz.goldzone.housing.Managers.Pinger;
import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;

import java.util.Map;

public class Task01 {
    public void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Plugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                Plugin.srv.sendCMD("+getservers");
                int now = 0;
                for(Map.Entry<String, Integer> module : Plugin.list.entrySet()) {
                    now = now + Integer.parseInt(Pinger.getONLINE2(Plugin.ip,module.getValue()));
                }
                Plugin.OnlineP = now;
            }
        }, 0L, 600L);
    }
}
