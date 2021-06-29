package cz.goldzone.housing.Events;

import cz.goldzone.housing.Inventory.ParkourGUI;
import cz.goldzone.housing.Plugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

public class BlockPlace implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        Location loc2 = e.getBlockPlaced().getLocation();

        if (loc2.getBlockX() >= 128 || loc2.getBlockX() <= 26 || loc2.getBlockZ() >= 117 || loc2.getBlockZ() <= 15) {
            e.setCancelled(true);
        }

        if (!(Plugin.getInstance().getConfig().getBoolean("destroyblock"))) {
            if (!(e.getPlayer().hasPermission("housing.bypass"))) {
                e.setCancelled(true);
            }
        }

        if (e.getItemInHand().getItemMeta().getDisplayName() == null) {
            return;
        }
        if (e.getItemInHand().getItemMeta().getDisplayName().equals("§eJumpPad (Poloz)")) {
            ArrayList<String> points;
            Location location = e.getBlockPlaced().getLocation();

            if (Plugin.getInstance().getConfig().get("Jumppads") == null) {
                points = new ArrayList<>();
            } else {
                points = (ArrayList<String>) Plugin.getInstance().getConfig().getList("Jumppads");
            }
            points.add(location.getBlockX() + "/" + location.getBlockY() + "/" +
                    location.getBlockZ() + "/" + location.getWorld().getName());
            Plugin.getInstance().getConfig().set("Jumppads", points);
            Plugin.getInstance().saveConfig();
            p.sendMessage(Plugin.prefix + " §a§lHotovo! §aJumpPad byl nastaven.");
        }
        if (e.getItemInHand().getItemMeta().getDisplayName().equals("§7Start (Poloz)")) {
            String name = ParkourGUI.nowpar.get(p);
            Location location = e.getBlockPlaced().getLocation();
            Plugin.getInstance().getParkour().set("Parkours." + name + ".startLoc", location.getBlockX() + "/" + location.getBlockY() + "/" +
                    location.getBlockZ() + "/" + location.getWorld().getName());
            Plugin.getInstance().saveParkour();
            p.getInventory().remove(e.getItemInHand());
            p.sendMessage(Plugin.prefix + " §a§lHotovo! §aStart byl nastaven.");
        }
        if (e.getItemInHand().getItemMeta().getDisplayName().equals("§eCheckPoint (Poloz)")) {
            String name = ParkourGUI.nowpar.get(p);
            Location location = e.getBlockPlaced().getLocation();
            ArrayList<String> points;
            if (Plugin.getInstance().getParkour().get("Parkours." + name + ".checkPoints") == null) {
                points = new ArrayList<>();
            } else {
                points = (ArrayList<String>) Plugin.getInstance().getParkour().getList("Parkours." + name + ".checkPoints");
            }
            points.add(location.getBlockX() + "/" + location.getBlockY() + "/" +
                    location.getBlockZ() + "/" + location.getWorld().getName());
            Plugin.getInstance().getParkour().set("Parkours." + name + ".checkPoints", points);
            Plugin.getInstance().saveParkour();
            p.sendMessage(Plugin.prefix + " §a§lHotovo! §aCheckPoint byl nastaven.");
        }
        if (e.getItemInHand().getItemMeta().getDisplayName().equals("§bKonec parkouru (Poloz)")) {
            String name = ParkourGUI.nowpar.get(p);
            Location location = e.getBlockPlaced().getLocation();
            Plugin.getInstance().getParkour().set("Parkours." + name + ".endLoc", location.getBlockX() + "/" + location.getBlockY() + "/" +
                    location.getBlockZ() + "/" + location.getWorld().getName());
            Plugin.getInstance().saveParkour();
            p.getInventory().clear();
            p.sendMessage(Plugin.prefix + " §a§lHotovo! §aKonec byl nastaven.");
        }
    }
}
