package cz.goldzone.housing.Events;

import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatEvent implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (PermissionsEx.getPermissionManager().getUser(p).getGroupsNames()[0].equals("HRAC")) {
            e.setFormat("§7" + e.getPlayer().getDisplayName() + ": §7" + e.getMessage());
        } else if (PermissionsEx.getPermissionManager().getUser(p).getGroupsNames()[0].equals("OWNER")) {
            e.setFormat("§4" + e.getPlayer().getDisplayName() + ": §7" + e.getMessage());
        } else if (PermissionsEx.getPermissionManager().getUser(p).getGroupsNames()[0].equals("OWNERWE")) {
            e.setFormat("§4" + e.getPlayer().getDisplayName() + ": §7" + e.getMessage());
        } else if (PermissionsEx.getPermissionManager().getUser(p).getGroupsNames()[0].equals("OWNERHV")) {
            e.setFormat("§4" + e.getPlayer().getDisplayName() + ": §7" + e.getMessage());
        } else if (PermissionsEx.getPermissionManager().getUser(p).getGroupsNames()[0].equals("COOWNER")) {
            e.setFormat("§c" + e.getPlayer().getDisplayName() + ": §7" + e.getMessage());
        } else if (PermissionsEx.getPermissionManager().getUser(p).getGroupsNames()[0].equals("MANAGER")) {
            e.setFormat("§9" + e.getPlayer().getDisplayName() + ": §7" + e.getMessage());
        } else if (PermissionsEx.getPermissionManager().getUser(p).getGroupsNames()[0].equals("VIP")) {
            e.setFormat("§e" + e.getPlayer().getDisplayName() + ": §7" + e.getMessage());
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("//schematic") || e.getMessage().startsWith("//schem") || e.getMessage().startsWith("/schematic") || e.getMessage().startsWith("/schem") || e.getMessage().startsWith("/download") || e.getMessage().startsWith("//download") || e.getMessage().contains(":")) {
            e.getPlayer().sendMessage(Plugin.prefix + "§c Tato funkce je zakazana!");
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onATPunish(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("/ban") || e.getMessage().startsWith("/hban") || e.getMessage().startsWith("/ban-ip") || e.getMessage().startsWith("/kick") || e.getMessage().startsWith("/hkick")) {
            String[] args = e.getMessage().split(" ");

            if (args.length < 2)
                return;

            if (Bukkit.getPlayer(args[1]) == null)
                return;

            if (Bukkit.getPlayer(args[1]).hasPermission("perm.at")) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("§8[§6§lServer§8] §cNemuzes trestat clena AT!");
            }
        }
    }
}
