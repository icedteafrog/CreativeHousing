package cz.goldzone.housing.Events;

import cz.goldzone.housing.Plugin;
import cz.goldzone.housing.Manager.BungeeServerManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;

import java.net.InetSocketAddress;

public class OnReceiveEvent implements Listener {
    public static void OnReceive(String data) {
        //System.out.println(data);
        String[] args = data.split(" ");
        String cmd = args[0];
        switch (cmd) {
            case "-serveradd":
                BungeeServerManager.addServer(args[1],new InetSocketAddress(Plugin.ip, Integer.parseInt(args[2])),"GZHoising",false);
                break;
            case "-serverremove":
                BungeeServerManager.removeServer(args[1]);
                break;
            case "-sendtoplayer":
                ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
                if(p == null) {
                    return;
                }
                String msg = "";
                for (int i = 2; i < args.length; i++) {
                    msg = msg + args[i] + " ";
                }
                p.sendMessage(msg.replaceAll("&","§"));
                break;
        }
    }
}
