package cz.goldzone.housing;

import cz.goldzone.housing.Commands.GZHousingCMD;
import cz.goldzone.housing.Events.GUIEvents;
import cz.goldzone.housing.Managers.Mysql;
import cz.goldzone.housing.Task.Task01;
import cz.goldzone.housing.Task.Task02;
import cz.goldzone.housing.ServerListener.ServerListener;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Plugin extends JavaPlugin {
    public static Plugin plugin;
    public static String pass = "1234";
    public static String ip = "82.208.16.234";
    public static ServerListener srv;
    public static Mysql mysql;
    public static HashMap<String,Integer> list = new HashMap<>();
    public static int OnlineP;
    @Override
    public void onEnable() {
        plugin = this;
        srv = new ServerListener();
        mysql = new Mysql();
        this.getCommand("gzhousing").setExecutor(new GZHousingCMD());
        this.getServer().getPluginManager().registerEvents(new GUIEvents(),this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.registerPlaceholders();
        srv.sendCMD("+getservers");
        new Task01().run();
        new Task02().run();
    }

    private void registerPlaceholders() {
        PlaceholderAPI.registerPlaceholderHook("housing", new PlaceholderHook() {

            @Override
            public String onPlaceholderRequest(Player p, String params) {
                if(p==null){
                    return null;
                }
                // %tscplugin_PARAMS%
                if (params.equals("onlineplayers")) {
                    return String.valueOf(Plugin.OnlineP);
                }
                if (params.equals("online")) {
                    return String.valueOf(Plugin.list.size());
                }
                return null;
            }
        });
    }
    @Override
    public void onDisable() {
        srv.sendCMD("+exits");
        srv.kill();
    }

    public static Plugin getInstance() {
        return plugin;
    }
}
