package cz.goldzone.node;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import cz.goldzone.node.Managers.FileManager;
import cz.goldzone.node.Managers.ServerStarter;
import cz.goldzone.node.Managers.AutoStop;
import cz.goldzone.node.Managers.Mysql;
import cz.goldzone.node.Server.Server;
import cz.goldzone.node.ServerManager.ServerManager;
import cz.goldzone.node.util.DateUtil;

public class Main {
    public static String pass = "pass";

    public static boolean debug = true;

    public static final ConcurrentHashMap<String, ServerManager> managedServers = new ConcurrentHashMap<>();

    public static final ArrayList<Process> serverProcesses = new ArrayList<>();

    public static ArrayList<Socket> authUsers = new ArrayList<>();

    public static Server srv;

    public static String patch;

    public static Mysql mysql;

    private static Main main;

    public boolean isWindows = false;

    public static void main(String[] args) throws IOException {
        try {
            patch = (new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        main = new Main();
        mysql = new Mysql();
        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM gzhousing");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (!DateUtil.isPurget(rs.getString("LastOn")))
                    continue;
                System.out.println("Server: " + rs.getString("ServerName") + " nebyl zapnutej 30 dni a proto ho mazu");
                FileManager.recursiveDelete(new File("servers/" + rs.getString("ServerName")));
                mysql.deleteServer(rs.getString("ServerName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate((TimerTask) new AutoStop(), 0L, 300000L);
        srv = new Server();
        srv.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("NODE byla terminovana!");
                for (ServerManager srv : Main.managedServers.values()) {
                    ServerStarter.stopServer(srv.getServerName());
                    System.out.println("Server: " + srv.getServerName() + " se vypíná");
                }
            }
        });
    }

    public static Main getInstance() {
        return main;
    }

    public static Mysql getMysql() {
        return mysql;
    }
}
