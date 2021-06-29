package cz.goldzone.housing.Commands;

import cz.goldzone.housing.Manager.BungeeServerManager;
import cz.goldzone.housing.Plugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

public class Start extends Command {
    public Start() {
        super("gzhadmin","gzhousing.admin");
    }

    String prefix = "§8[§6§lHousing§8] ";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(prefix + "§cPouzi /gzhadmin help");
        } else {
            String arg = args[0];
            if(arg.equalsIgnoreCase("help")) {
                sender.sendMessage(prefix + "§7/gzhadmin help");
                sender.sendMessage(prefix + "§7/gzhadmin start <housing>");
                sender.sendMessage(prefix + "§7/gzhadmin stop <housing>");
                sender.sendMessage(prefix + "§7/gzhadmin sendcmd <housing> <cmd>");
                sender.sendMessage(prefix + "§7/gzhadmin delete <housing>");
                sender.sendMessage(prefix + "§7/gzhadmin announce <msg>");
                sender.sendMessage(prefix + "§7/gzhadmin stopall");
            } else if(arg.equalsIgnoreCase("start")) {
                if(args.length == 2) {
                    sender.sendMessage(prefix + "§aOdesilam prikaz do NODE");
                    Plugin.srv.sendCMD("+start " + args[1]);
                } else {
                    sender.sendMessage(prefix + "§cChybne argumenty");
                }
            } else if(arg.equalsIgnoreCase("stop")) {
                if (args.length == 2) {
                    sender.sendMessage(prefix + "§aOdesilam prikaz do NODE");
                    Plugin.srv.sendCMD("+stop " + args[1]);
                } else {
                    sender.sendMessage(prefix + "§cChybne argumenty");
                }
            } else if(arg.equalsIgnoreCase("announce")) {
                if (args.length >= 1) {
                    String msg = "";
                    for (int i = 1; i < args.length; i++) {
                        msg = msg + args[i] + " ";
                    }
                    sender.sendMessage(prefix + "§aOznamuji zpravu: " + msg.replaceAll("§","&"));
                    for(ServerInfo srv : BungeeServerManager.onlineH) {
                        for(ProxiedPlayer p : srv.getPlayers()) {
                            p.sendMessage(prefix + "§c§l[!] §cZdeleni: §7" + msg.replaceAll("§","&"));
                        }
                    }
                } else {
                    sender.sendMessage(prefix + "§cChybne argumenty");
                }
            } else if(arg.equalsIgnoreCase("sendcmd")) {
                if (args.length >= 2) {
                    sender.sendMessage(prefix + "§aOdesilam prikaz do NODE");
                    String msg = "";
                    for (int i = 2; i < args.length; i++) {
                        msg = msg + args[i] + " ";
                    }
                    Plugin.srv.sendCMD("+sendcmd " + args[1] + " " + msg);
                } else {
                    sender.sendMessage(prefix + "§cChybne argumenty");
                }
            } else if(arg.equalsIgnoreCase("stopall")) {
                for(ServerInfo srv : BungeeServerManager.onlineH) {
                    for(ProxiedPlayer p : srv.getPlayers()) {
                        p.sendMessage(prefix + "§c§l[!] §cByl zahajen restart vsech housingu!");
                        p.connect(ProxyServer.getInstance().getServerInfo("Lobby-2"));
                    }
                }
                Plugin.srv.sendCMD("+exit");
            } else if(arg.equalsIgnoreCase("delete")) {
                if (args.length == 2) {
                    sender.sendMessage(prefix + "§aOdesilam prikaz do NODE");
                    Plugin.srv.sendCMD("+delete " + args[1]);
                } else {
                    sender.sendMessage(prefix + "§cChybne argumenty");
                }
            } else {
                sender.sendMessage(prefix + "§cChybne argumenty");
            }
        }
    }
}
