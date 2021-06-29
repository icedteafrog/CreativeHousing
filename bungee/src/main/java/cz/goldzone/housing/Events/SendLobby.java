package cz.goldzone.housing.Events;

import cz.goldzone.housing.Plugin;
import cz.goldzone.housing.Manager.BungeeServerManager;
import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SendLobby implements Listener {
    @EventHandler
    public void onServerKickEvent(ServerKickEvent ev) {
        ServerInfo kickedFrom = null;
        if (ev.getPlayer().getServer() != null) {
            kickedFrom = ev.getPlayer().getServer().getInfo();
        }
        if(!(BungeeServerManager.onlineH.contains(kickedFrom))) {
            return;
        } else if (Plugin.getInstance().getProxy().getReconnectHandler() != null) {
            kickedFrom = Plugin.getInstance().getProxy().getReconnectHandler().getServer(ev.getPlayer());
        }
        else {
            kickedFrom = AbstractReconnectHandler.getForcedHost(ev.getPlayer().getPendingConnection());
            if (kickedFrom == null) {
                kickedFrom = ProxyServer.getInstance().getServerInfo("Lobby-2");
            }
        }
        ev.setCancelled(true);
    }
}
