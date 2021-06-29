package cz.goldzone.housing.Managers;

import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectateManager {

    public static List<Player> spectators = new ArrayList<>();

    public static void spectate(Player p, Player spectated) {
        spectators.add(p);

        for (Player pp : Bukkit.getServer().getOnlinePlayers()) {
            if (!pp.hasPermission("perm.at"))
                pp.hidePlayer(Plugin.getInstance(), p);
        }

        p.setPlayerListName(p.getName() + " §7[Spec]");
        p.teleport(spectated);
        p.setGameMode(GameMode.SPECTATOR);
    }

    public static void unspectate(Player p) {
        spectators.remove(p);

        p.setGameMode(GameMode.SURVIVAL);

        String[] parts = Plugin.getInstance().getConfig().getString("spawn").split("/");
        Location spawn = new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        p.teleport(spawn);
    }
}
