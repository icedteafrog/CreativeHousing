package cz.goldzone.housing.Manager;

import cz.goldzone.housing.Plugin;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AutoReconect {
    public static void Check() {
        Plugin.getInstance().getProxy().getScheduler().schedule(Plugin.getInstance(), new Runnable() {

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
        }, 1, 5, TimeUnit.SECONDS);
    }
}
