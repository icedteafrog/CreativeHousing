package cz.goldzone.housing.Commands;

import cz.goldzone.housing.GUI.MainGUI;
import cz.goldzone.housing.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GZHousingCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(Plugin.srv.online) {
                new MainGUI().JoinnGUI(p);
            } else {
                p.sendMessage("§8[§6§lHousing§8] §c§l[!] §cA jeje neco se pokazilo! Nepodarilo se kontaktovat NODE");
            }
        }
        return true;
    }
}
