package cz.goldzone.node.Server;

import cz.goldzone.node.Enums.Lang;
import cz.goldzone.node.Main;
import cz.goldzone.node.Managers.Codder;
import cz.goldzone.node.Managers.ServerStarter;
import cz.goldzone.node.ServerManager.ServerManager;
import cz.goldzone.node.util.PMSG;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Commands {
    public static void onCommand(String cmd2, Socket client) {
        String name2, msg;
        int i;
        String name;
        HashMap<String, Integer> list;
        String[] args = cmd2.split(" ");
        String cmd = args[0];
        String str1;
        switch ((str1 = cmd).hashCode()) {
            case -1827505770:
                if (!str1.equals("+delete"))
                    break;
                ServerStarter.deleteServer(args[1]);
                break;
            case -388649433:
                if (!str1.equals("+sendcmd"))
                    break;
                name2 = args[1];
                msg = "";
                for (i = 2; i < args.length; i++)
                    msg = String.valueOf(msg) + args[i] + " ";
                if (Main.managedServers.containsKey(name2))
                    ((ServerManager) Main.managedServers.get(name2)).sendCMD(msg);
                break;
            case 42717267:
                if (!str1.equals("+auth"))
                    break;
                if (!args[1].equals(Main.pass)) {
                    Main.srv.Kick(client);
                    break;
                }
                if (!Main.authUsers.contains(client)) {
                    Main.authUsers.add(client);
                    System.out.println("Uzivatel " + client.getRemoteSocketAddress() + " se prihlasil");
                }
                break;
            case 42838985:
                if (!str1.equals("+exit"))
                    break;
                for (ServerManager srv : Main.managedServers.values()) {
                    ServerStarter.stopServer(srv.getServerName());
                    System.out.println("Server: " + srv.getServerName() + " se vypíná");
                }
                System.out.println("BungeeServer se vypnul! Restartuji node");
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(0);
                break;
            case 43252397:
                if (!str1.equals("+stop"))
                    break;
                ServerStarter.stopServer(args[1]);
                break;
            case 158154386:
                if (!str1.equals("+ownerjoin"))
                    break;
                name = args[1];
                if (args[2].equals("we")) {
                    if (Main.managedServers.containsKey(name))
                        ((ServerManager) Main.managedServers.get(name)).sendCMD("pex user " + name + " group set OWNERWE");
                } else if (Main.managedServers.containsKey(name)) {
                    ((ServerManager) Main.managedServers.get(name)).sendCMD("pex user " + name + " group set OWNER");
                }
                PMSG.sendMSG(name, Lang.INFO);
                break;
            case 1328008650:
                if (!str1.equals("+exits"))
                    break;
                Main.srv.getOnline().remove(client);
                System.out.println("Uzivatel " + client.getRemoteSocketAddress() + " se odpojil");
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1340811031:
                if (!str1.equals("+start"))
                    break;
                ServerStarter.startServer(args[1]);
                break;
            case 1396859877:
                if (!str1.equals("+getservers"))
                    break;
                list = new HashMap<>();
                for (ServerManager serverManager : Main.managedServers.values())
                    list.put(serverManager.getServerName(), Integer.valueOf(serverManager.getPort()));
                BackSend.sendServers(Codder.code(list));
                break;
        }
    }
}
