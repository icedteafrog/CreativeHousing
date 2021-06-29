package cz.goldzone.housing.Inventory;

import cz.goldzone.housing.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TimeGUI {
    public void openINV(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, "§0§lGZHousing Weather");
        if (p.getWorld().hasStorm()) {
            inv.setItem(1, ItemUtil.createItem(Material.WOOL, "§c§lVypnout dest", (byte) 14));
        } else {
            inv.setItem(1, ItemUtil.createItem(Material.WOOL, "§a§lZapnout dest", (byte) 5));
        }

        if (Boolean.parseBoolean(p.getWorld().getGameRuleValue("doDaylightCycle"))) {
            inv.setItem(4, ItemUtil.createItem(Material.WOOL, "§c§lVypnout stridani dne a noci", (byte) 14));
        } else {
            inv.setItem(4, ItemUtil.createItem(Material.WOOL, "§a§lZapnout stridani dne a noci", (byte) 5));
        }

        inv.setItem(6, ItemUtil.createItem(Material.WOOL, "§e§lDEN", (byte) 4));
        inv.setItem(7, ItemUtil.createItem(Material.WOOL, "§9§lNOC", (byte) 11));

        p.openInventory(inv);
    }
}
