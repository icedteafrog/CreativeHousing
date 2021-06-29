package cz.goldzone.housing.Commands;

import cz.goldzone.housing.Managers.VoteManager;
import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Vote implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        Plugin main = Plugin.getInstance();
        ArrayList<String> players = (ArrayList<String>) main.getConfig().getList("playerVotes");
        if (!(players == null)) {
            if (players.contains(p.getName())) {
                p.sendMessage(Plugin.prefix + " §cPro tuhle parcelu jsi jiz hlasoval!");
                return false;
            }
        } else {
            players = new ArrayList<>();
        }
        VoteManager.addVote();
        players.add(p.getName());
        main.getConfig().set("playerVotes", players);
        main.saveConfig();
        Bukkit.broadcastMessage(Plugin.prefix + " §7Hrac §e" + p.getName() + " §7zahlasoval pro parcelu, dekujeme :)");
        return false;
    }
}
