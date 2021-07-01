package cz.goldzone.housing;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cz.goldzone.housing.Commands.CheckPoint;
import cz.goldzone.housing.Commands.Fly;
import cz.goldzone.housing.Commands.Menu;
import cz.goldzone.housing.Commands.Vote;
import cz.goldzone.housing.Events.*;
import cz.goldzone.housing.Managers.Mysql;
import cz.goldzone.housing.Managers.ScoreboardManager;
import cz.goldzone.housing.Utils.DateUtil;
import cz.goldzone.housing.Utils.FileUtil;
import cz.goldzone.housing.cons.MysqlData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Plugin extends JavaPlugin {

    @Getter
    public String serverName;
    @Getter
    public String prefix = "§8[§6§lHousing§8]";
    @Getter
    public Mysql mysql;
    private static Plugin main;
    public FileConfiguration defConfig;
    public File defFile;
    public FileConfiguration parConfig;
    public File parFile;
    public ScoreboardManager scoreBoard;

    public static Plugin getInstance() {
        return main;
    }

    @Override
    public void onEnable() {
        // instance
        main = this;
        // Commands
        this.getCommand("menu").setExecutor(new Menu());
        this.getCommand("vote").setExecutor(new Vote());
        this.getCommand("checkpoint").setExecutor(new CheckPoint());
        this.getCommand("fly").setExecutor(new Fly());
        // Listeners
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new GUIEvents(), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        this.getServer().getPluginManager().registerEvents(new JoinLeave(), this);
        this.getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OtherEvents(), this);
        // Plugin Channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        // File loading
        mysql = new Mysql(new MysqlData(FileUtil.loadFile(getDataFolder().getAbsolutePath(), "mysql.yml")));
        FileConfiguration customConfig = YamlConfiguration.loadConfiguration(new File("plugins/serverName.yml"));
        serverName = (String) customConfig.get("serverName");
        createConfig();
        loadConfig();
        if (defConfig.get("resetDay") == null) {
            defConfig.set("resetDay", DateUtil.get30days());
        }
        if (Plugin.getInstance().getConfig().get("Gamemode") == null) {
            Plugin.getInstance().getConfig().set("Gamemode", 0);
            Plugin.getInstance().saveConfig();
        }
        if (DateUtil.isPurget(defConfig.getString("resetDay"))) {
            defConfig.set("playerVotes", null);
            defConfig.set("resetDay", DateUtil.get30days());
            mysql.updateVotes(0);
        }
        //new ScoreBoardTask().runTask();
        saveConfig();
        createParkour();
        loadParkour();
        saveParkour();
        scoreBoard = new ScoreboardManager();
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("Lobby-2");

            p.sendPluginMessage(Plugin.getInstance(), "BungeeCord", out.toByteArray());
        }
    }

    public FileConfiguration getConfig() {
        return this.defConfig;
    }

    public void createConfig() {
        this.defFile = new File(getDataFolder(), "Config.yml");
        if (!this.defFile.exists()) {
            this.defFile.getParentFile().mkdirs();
            saveResource("Config.yml", false);
        }
        this.defConfig = (FileConfiguration) new YamlConfiguration();
        try {
            this.defConfig.load(this.defFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            this.defConfig.save(this.defFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            getConfig().load(this.defFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();

        }
    }

    public FileConfiguration getParkour() {
        return this.parConfig;
    }

    public void createParkour() {
        this.parFile = new File(getDataFolder(), "Parkour.yml");
        if (!this.parFile.exists()) {
            this.parFile.getParentFile().mkdirs();
            saveResource("Parkour.yml", false);
        }
        this.parConfig = (FileConfiguration) new YamlConfiguration();
        try {
            this.parConfig.load(this.parFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveParkour() {
        try {
            this.parConfig.save(this.parFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadParkour() {
        try {
            getConfig().load(this.parFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();

        }
    }

    public ScoreboardManager getScoreBoard() {
        return scoreBoard;
    }

}
