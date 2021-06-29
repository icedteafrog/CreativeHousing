package cz.goldzone.housing.GUI;

import cz.goldzone.housing.Util.HashMapUtil;
import cz.goldzone.housing.Util.ItemUtil;
import cz.goldzone.housing.Managers.Pinger;
import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainGUI {

    @SuppressWarnings("deprecation")
    public void JoinnGUI(Player p) {
        Map<String,ItemStack> items = new LinkedHashMap<>();
        Map<String,Integer> sort = new HashMap<>();
        for(Map.Entry<String, Integer> module : Plugin.list.entrySet()) {
            sort.put(module.getKey(),Plugin.mysql.getVotes(module.getKey()));
        }

        HashMapUtil.sortByValueDescending(sort).forEach((k, v) -> {
            ItemStack parcela;
            if(Integer.parseInt(Pinger.getONLINE2(Plugin.ip,Plugin.list.get(k))) == 0) {
                parcela =  new ItemStack(Material.SKULL_ITEM, 1,(short) 3);
            } else {
                parcela =  new ItemStack(Material.SKULL_ITEM, Integer.parseInt(Pinger.getONLINE2(Plugin.ip,Plugin.list.get(k))),(short) 3);
            }
            SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
            meta.setOwner(k);
            meta.setDisplayName("§6Parcela hrace " + k);

            ArrayList<String> lore = new ArrayList<>();

            lore.add(" ");
            lore.add("§7Vlastnik §e" + k);
            lore.add(" ");
            lore.add("§7MOTD: §e" + Plugin.mysql.getMotd(k));
            lore.add(" ");
            lore.add("§7Hodnoceni: §e" + v);
            lore.add(" ");
            lore.add("§7Online: §e" + Pinger.getONLINE(Plugin.ip,Plugin.list.get(k)));



            meta.setLore(lore);
            parcela.setItemMeta(meta);

            items.put(k, ItemUtil.setString(k,parcela));
        });
        ScrollerInventory.getInstance(items, "§0§lGZHousing", p);
    }
    public void ConfirmGUI(Player p) {
        Inventory i2 = Bukkit.createInventory(null,9,"§4§lPotvrzeni");
        i2.setItem(0,ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        i2.setItem(1,ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        i2.setItem(2,ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        i2.setItem(3,ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        i2.setItem(5,ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        i2.setItem(6,ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        i2.setItem(7,ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        i2.setItem(8,ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        i2.setItem(4,ItemUtil.createItem(Material.REDSTONE_BLOCK,"§4§l!POTVRDIT!"));
        p.openInventory(i2);
    }
}
