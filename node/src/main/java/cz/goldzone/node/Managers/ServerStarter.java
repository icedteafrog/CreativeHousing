package cz.goldzone.node.Managers;

import java.io.File;

import cz.goldzone.node.Enums.Lang;
import cz.goldzone.node.Main;
import cz.goldzone.node.Server.BackSend;
import cz.goldzone.node.ServerManager.ServerManager;
import cz.goldzone.node.util.PMSG;

public class ServerStarter {
    public static void startServer(String name) {
        int count = Main.managedServers.size();
        if (count >= 45) {
            PMSG.sendMSG(name, Lang.MAXH);
            return;
        }
        Main.mysql.ifExist(name);
        if (!ifExist(name)) {
            createServer(name);
            return;
        }
        if (Main.managedServers.containsKey(name))
            return;
        PMSG.sendMSG(name, Lang.STARTING);
        int port = PortManager.getAvailPort().intValue();
        ServerManager srv = new ServerManager(name, "servers/" + name, String.valueOf(Main.getMysql().getRam(name)), String.valueOf(Main.getMysql().getRam(name)), String.valueOf(port), String.valueOf(Main.getMysql().getSlots(name)));
        Main.managedServers.put(name, srv);
        Main.mysql.updateLastOn(name);
        BackSend.addServer(name, String.valueOf(port));
    }

    public static void stopServer(String name) {
        if (!ifExist(name))
            return;
        if (!Main.managedServers.containsKey(name))
            return;
        ((ServerManager) Main.managedServers.get(name)).shutdown();
        Main.managedServers.remove(name);
        BackSend.removeServer(name);
    }

    public static void createServer(String name) {
        FileManager.copyNew(name, "default");
        PMSG.sendMSG(name, Lang.CREATING);
        PMSG.sendMSG(name, Lang.DONE);
        startServer(name);
    }

    public static void deleteServer(final String name) {
        File f = new File("servers/" + name);
        if (!f.exists()) {
            PMSG.sendMSG(name, Lang.NODELETE);
            return;
        }
        PMSG.sendMSG(name, Lang.DELETING);
        Thread thread = new Thread() {
            public void run() {
                if (Main.managedServers.containsKey(name))
                    ((ServerManager) Main.managedServers.get(name)).kill();
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                File f = new File("servers/" + name);
                if (f.exists()) {
                    FileManager.recursiveDelete(new File("servers/" + name));
                    Main.getMysql().deleteServer(name);
                    PMSG.sendMSG(name, Lang.DELETE);
                } else {
                    PMSG.sendMSG(name, Lang.NODELETE);
                }
            }
        };
        thread.start();
    }

    public static boolean ifExist(String name) {
        File f = new File("servers/" + name);
        if (f.exists())
            return true;
        return false;
    }
}
