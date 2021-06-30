package cz.goldzone.housing.Events;

import cz.goldzone.housing.Managers.SpectateManager;
import lombok.Cleanup;
import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class JoinLeave implements Listener {
    private static final List<String> atPerms = Arrays.asList("-coreprotect.purge", "-coreprotect.purge.*", "coreprotect.*", "perm.at", "housing.bypass", "minecraft.command.gamemode", "minecraft.command.teleport", "minecraft.command.ban", "minecraft.command.pardon", "minecraft.command.pardon-ip");

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        //
        // This code below required an additonal dependency, which i won't provide. This just determines the rank, and check for spectate.
        //

//        try {
//            @Cleanup
//            Connection connection = GoldAC.getMySQL().getConnection();
//            @Cleanup
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE nick = ? AND rank IN ('ADMIN', 'HELPER', 'HELPERKA', 'HLHELPER', 'EHELPER', 'ADMIN_SEC')");
//            statement.setString(1, p.getName());
//            @Cleanup
//            ResultSet rs = statement.executeQuery();
//
//            if (rs.next()) {
//                PermissionsEx.getUser(p).setPermissions(atPerms);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        if (Plugin.getRedis("CREATIVE_HOUSING;" + p.getUniqueId() + ";spec") != null) {
//            Player spectated = Bukkit.getPlayer(Plugin.getRedis("CREATIVE_HOUSING;" + p.getUniqueId() + ";spec"));
//
//            if (spectated != null && spectated.isOnline()) {
//                e.setJoinMessage(null);
//
//                SpectateManager.spectate(p, spectated);
//
//                return;
//            }
//        }

        Plugin.getInstance().getScoreBoard().createBoard(p);

        if (!p.hasPermission("perm.at"))
            for (Player pp : SpectateManager.spectators)
                p.hidePlayer(Plugin.getInstance(), pp);

        e.setJoinMessage("§6[§a+§6] " + e.getPlayer().getName());

        switch (Plugin.getInstance().getConfig().getInt("Gamemode")) {
            case 0:
                p.setGameMode(GameMode.SURVIVAL);
                break;
            case 1:
                p.setGameMode(GameMode.CREATIVE);
                break;
            case 2:
                p.setGameMode(GameMode.ADVENTURE);
                break;
            case 3:
                p.setGameMode(GameMode.SPECTATOR);
                break;
        }
        String[] parts = Plugin.getInstance().getConfig().getString("spawn").split("/");
        Location spawn = new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        p.teleport(spawn);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (SpectateManager.spectators.contains(e.getPlayer()))
            e.setQuitMessage(null);
        else
            e.setQuitMessage("§6[§c-§6] " + e.getPlayer().getName());
        Plugin.getInstance().getScoreBoard().removeBoard(e.getPlayer());

        for (String atPerm : atPerms) {
            PermissionsEx.getUser(e.getPlayer()).removePermission(atPerm);
        }

        SpectateManager.unspectate(e.getPlayer());
    }
}
