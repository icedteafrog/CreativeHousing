package cz.goldzone.housing.Commands;

import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckPoint implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (Plugin.getInstance().getParkour().getString("Players." + p.getName()) == null) {
            p.sendMessage(Plugin.prefix + " §cNemas ulozeny zadny checkpoint!");
            return false;
        }
        String[] parts = Plugin.getInstance().getParkour().getString("Players." + p.getName()).split("/");
        Location loc = new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        p.teleport(loc);
        p.sendMessage(Plugin.prefix + " §aByl jsi teleportovan na checkpoint!");
        return false;
    }
}
