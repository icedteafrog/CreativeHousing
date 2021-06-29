package cz.goldzone.housing.Manager;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class BungeeServerManager {

    public static ArrayList<ServerInfo> onlineH = new ArrayList<>();

    public static void addServer(String name, InetSocketAddress address, String motd, boolean restricted) {
        ProxyServer.getInstance().getLogger().info("§eServer " + name + " added to bungee servers");
        ServerInfo add = ProxyServer.getInstance().constructServerInfo(name, address, motd, restricted);
        onlineH.add(add);
        ProxyServer.getInstance().getServers().put(name, add);
    }
    public static void removeServer(String name) {
        if(ProxyServer.getInstance().getServerInfo(name) == null) {
            ProxyServer.getInstance().getLogger().info("§eServer is null!");
            return;
        }
        for (ProxiedPlayer p : ProxyServer.getInstance().getServerInfo(name).getPlayers()) {
            ServerInfo target = ProxyServer.getInstance().getServerInfo("Lobby");
            p.connect(target);
        }
        onlineH.remove(ProxyServer.getInstance().getServerInfo(name));
        ProxyServer.getInstance().getServers().remove(name);
        ProxyServer.getInstance().getLogger().info("§eServer " + name + " removed from bungee servers");
    }
}
