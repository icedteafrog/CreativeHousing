package cz.goldzone.housing.Commands;

import cz.goldzone.housing.Inventory.MainGUI;
import cz.goldzone.housing.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Menu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (!(p.hasPermission("housing.menu"))) {
            p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
            return false;
        }
        new MainGUI().openINV(p);
        return false;
    }
}
