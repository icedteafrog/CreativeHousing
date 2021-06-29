package cz.goldzone.housing.Events;

import cz.goldzone.housing.Plugin;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchEvent implements Listener {

    @EventHandler
    public void onSwitch(ServerConnectedEvent e) {
        if(e.getServer().getInfo().getName().contains(e.getPlayer().getName())) {
            if(e.getPlayer().hasPermission("gzhousing.worldedit")) {
                Plugin.srv.sendCMD("+ownerjoin " + e.getPlayer().getName() + " we");
            } else {
                Plugin.srv.sendCMD("+ownerjoin " + e.getPlayer().getName() + " normal");

            }
        }
    }

}
