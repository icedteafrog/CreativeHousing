package cz.goldzone.housing.Commands;

import cz.goldzone.housing.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (!(p.hasPermission("housing.fly"))) {
            p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
            return false;
        }
        if (p.getAllowFlight()) {
            p.setAllowFlight(false);
            p.setFlying(false);
            p.sendMessage(Plugin.prefix + " §cFly vypnut!");
        } else {
            p.setAllowFlight(true);
            p.sendMessage(Plugin.prefix + " §aFly zapnut!");
        }
        return false;
    }
}
