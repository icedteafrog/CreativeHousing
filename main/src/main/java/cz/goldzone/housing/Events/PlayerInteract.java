package cz.goldzone.housing.Events;

import cz.goldzone.housing.Plugin;
import cz.goldzone.housing.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerInteract implements Listener {

    private HashMap<Player, String> ingpar = new HashMap<>();

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.PHYSICAL)) {
            if (e.getClickedBlock().getType() == Material.STONE_PLATE) {
                Player p = e.getPlayer();
                for (String key : Plugin.getInstance().getParkour().getConfigurationSection("Parkours").getKeys(false)) {
                    String[] parts = Plugin.getInstance().getParkour().getString("Parkours." + key + ".startLoc").split("/");
                    if (parts == null) {
                        return;
                    }
                    Location stone = new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (stone.equals(e.getClickedBlock().getLocation())) {
                        p.sendMessage(Plugin.prefix + " §aZacal jsi skakat parkour §e" + key + "§a, hodne stesti!");
                        if (ingpar.containsKey(p)) {
                            ingpar.replace(p, key);
                        } else {
                            ingpar.put(p, key);
                        }
                    }
                }
            }
            if (e.getClickedBlock().getType() == Material.IRON_PLATE) {
                Player p = e.getPlayer();
                if (ingpar.containsKey(p)) {
                    ArrayList<String> points = (ArrayList<String>) Plugin.getInstance().getParkour().getList("Parkours." + ingpar.get(p) + ".checkPoints");
                    if (points == null) {
                        return;
                    }
                    Location location = e.getClickedBlock().getLocation();
                    String now = (location.getBlockX() + "/" + location.getBlockY() + "/" +
                            location.getBlockZ() + "/" + location.getWorld().getName());
                    if (!(Plugin.getInstance().getParkour().getString("Players." + p.getName()) == null)) {
                        if (Plugin.getInstance().getParkour().getString("Players." + p.getName()).equals(now)) {
                            return;
                        }
                    }
                    if (points.contains(now)) {
                        p.sendMessage(Plugin.prefix + " §e§lHotovo! §eTvuj checkpoint byl ulozen, pro teleport pouzi §6/checkpoint");
                        Plugin.getInstance().getParkour().set("Players." + p.getName(), now);
                        Plugin.getInstance().saveParkour();
                    }
                    return;
                }
            }
            if (e.getClickedBlock().getType() == Material.GOLD_PLATE) {
                Player p = e.getPlayer();
                if (ingpar.containsKey(p)) {
                    String points = Plugin.getInstance().getParkour().getString("Parkours." + ingpar.get(p) + ".endLoc");
                    if (points == null) {
                        return;
                    }
                    Location location = e.getClickedBlock().getLocation();
                    String now = (location.getBlockX() + "/" + location.getBlockY() + "/" +
                            location.getBlockZ() + "/" + location.getWorld().getName());
                    if (points.contains(now)) {
                        Bukkit.broadcastMessage(Plugin.prefix + " §aHrac §e" + p.getName() + " §adoskakal parkour §e" + ingpar.get(p) + "§a!");
                        Plugin.getInstance().getParkour().set("Players." + p.getName(), null);
                        ArrayList<String> points2;
                        if (Plugin.getInstance().getParkour().get("Parkours." + ingpar.get(p) + ".donePlayers") == null) {
                            points2 = new ArrayList<>();
                        } else {
                            points2 = (ArrayList<String>) Plugin.getInstance().getParkour().getList("Parkours." + ingpar.get(p) + ".donePlayers");
                        }
                        if (!(points2.contains(p.getName()))) {
                            points2.add(p.getName());
                            Plugin.getInstance().getParkour().set("Parkours." + ingpar.get(p) + ".donePlayers", points2);
                        }
                        Plugin.getInstance().getParkour().set("Players." + p.getName(), null);
                        Plugin.getInstance().saveParkour();
                        if (!(Plugin.getInstance().getParkour().getString("Parkours." + ingpar.get(p) + ".reward") == null)) {
                            p.getInventory().addItem(ItemUtil.fromBase64(Plugin.getInstance().getParkour().getString("Parkours." + ingpar.get(p) + ".reward")));
                        }
                        ingpar.remove(p);
                    }
                    return;
                }
            }
        }
    }
}
