package cz.goldzone.housing.Events;

import cz.goldzone.housing.Plugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class BlockBreak implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Location loc2 = e.getBlock().getLocation();

        if (loc2.getBlockX() >= 128 || loc2.getBlockX() <= 26 || loc2.getBlockZ() >= 117 || loc2.getBlockZ() <= 15) {
            e.setCancelled(true);
        }

        if (!(Plugin.getInstance().getConfig().getBoolean("destroyblock"))) {
            if (!(e.getPlayer().hasPermission("housing.bypass"))) {
                e.setCancelled(true);
            }
        }
        if (e.getBlock().getType() == Material.GOLD_PLATE) {
            ArrayList<String> points = (ArrayList<String>) Plugin.getInstance().getConfig().get("Jumppads");
            if (points == null) {
                return;
            }
            Location location = e.getBlock().getLocation();
            String now = (location.getBlockX() + "/" + location.getBlockY() + "/" +
                    location.getBlockZ() + "/" + location.getWorld().getName());
            if (points.contains(now)) {
                e.getPlayer().sendMessage(Plugin.prefix + " §c§lHotovo! §cJumpPad byl zrusen.");
                points.remove(now);
                Plugin.getInstance().getConfig().set("Jumppads", points);
                Plugin.getInstance().saveConfig();
            }
        }
    }
}
