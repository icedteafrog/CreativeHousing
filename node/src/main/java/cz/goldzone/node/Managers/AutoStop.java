package cz.goldzone.node.Managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TimerTask;

import cz.goldzone.node.Main;
import cz.goldzone.node.util.Pinger;
import cz.goldzone.node.ServerManager.ServerManager;

public class AutoStop extends TimerTask {
    public static ArrayList<ServerManager> zerop = new ArrayList<>();

    public void run() {
        ArrayList<String> nostop = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String onlineString = String.join("','", Main.managedServers.keySet());
            statement = Main.mysql.prepareStatement("SELECT ServerName FROM gzhousing WHERE ServerName IN ('" + onlineString + "') ORDER BY Votes DESC LIMIT 7");
            rs = statement.executeQuery();
            while (rs.next())
                nostop.add(rs.getString("ServerName"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception exception) {
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception exception) {
            }
        }
        for (ServerManager srv : Main.managedServers.values()) {
            int op = Integer.valueOf(Pinger.getONLINE("127.0.0.1", Integer.valueOf(srv.getPort()).intValue())).intValue();
            if (op == 0) {
                if (zerop.contains(srv))
                    if (nostop.contains(srv.getServerName())) {
                        System.out.println("Server: " + srv.getServerName() + " je v top 7");
                    } else {
                        ServerStarter.stopServer(srv.getServerName());
                        zerop.remove(srv);
                    }
                zerop.add(srv);
                continue;
            }
            if (zerop.contains(srv))
                zerop.remove(srv);
        }
    }
}
