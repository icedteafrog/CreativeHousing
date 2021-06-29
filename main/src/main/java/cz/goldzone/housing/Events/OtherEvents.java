package cz.goldzone.housing.Events;

import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class OtherEvents implements Listener {

    private ArrayList<Player> jumpers = new ArrayList<Player>();

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        if (!(Plugin.getInstance().getConfig().getBoolean("hunger"))) {
            Player p = (Player) e.getEntity();
            p.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(Plugin.getInstance().getConfig().getBoolean("damage"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location loc2 = e.getPlayer().getLocation();
        if (loc2.getBlockX() >= 142 || loc2.getBlockX() <= 12 || loc2.getBlockZ() >= 131 || loc2.getBlockZ() <= 1) {
            e.setCancelled(true);
        }

        if (e.getTo().getBlock().getType() == Material.GOLD_PLATE) {
            ArrayList<String> points = (ArrayList<String>) Plugin.getInstance().getConfig().get("Jumppads");
            if (points == null) {
                return;
            }
            Location location = e.getTo().getBlock().getLocation();
            String now = (location.getBlockX() + "/" + location.getBlockY() + "/" +
                    location.getBlockZ() + "/" + location.getWorld().getName());
            if (points.contains(now)) {
                e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(5));
                e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 1.0D, e.getPlayer().getVelocity().getZ()));
                jumpers.add(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL && jumpers.contains(p)) {
                e.setCancelled(true);
                jumpers.remove(p);
            }
        }
    }

    @EventHandler
    public void onVoidTP(PlayerMoveEvent e) {
        if (Plugin.getInstance().getConfig().getBoolean("damage")) {
            return;
        }
        if (e.getPlayer().getLocation().getY() <= 0) {
            String[] parts = Plugin.getInstance().getConfig().getString("spawn").split("/");
            if (parts == null) {
                return;
            }
            Location spawn = new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            e.getPlayer().teleport(spawn);
        }
    }

    @EventHandler
    public void onDeath(PlayerRespawnEvent e) {
        String[] parts = Plugin.getInstance().getConfig().getString("spawn").split("/");
        if (parts == null) {
            return;
        }
        Location spawn = new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        e.setRespawnLocation(spawn);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        e.blockList().clear();
    }
}
