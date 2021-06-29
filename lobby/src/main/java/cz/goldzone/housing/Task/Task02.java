package cz.goldzone.housing.Task;

import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;

import java.io.IOException;

public class Task02 {
    public void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Plugin.getInstance(), new Runnable() {

            @Override
            public void run() {
                if(!(Plugin.srv.testCon())) {
                    System.out.println("Pripojeni k GZNode bylo ztraceno pokus");
                    Plugin.srv.kill();
                    try {
                        Plugin.srv.init();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0L, 100L);
    }
}
