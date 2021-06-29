package cz.goldzone.housing.Inventory;

import cz.goldzone.housing.Utils.ItemUtil;
import cz.goldzone.housing.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class ParkourGUI {
    public static HashMap<Player, String> nowpar = new HashMap<>();

    public void parkourGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§0§lGZHousing Parkour");

        inv.setItem(0, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(1, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(2, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(3, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(4, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(5, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(6, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(7, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(8, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(9, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(17, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(18, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(26, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(27, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(35, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(36, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(44, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(45, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(46, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(47, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(48, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(49, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(50, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(51, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(52, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(53, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));

        inv.setItem(40, ItemUtil.createItem(Material.ANVIL, "§6§lVYTVORIT PARKOUR"));

        if (!(Plugin.getInstance().getParkour().getString("Parkours") == null)) {
            for (String key : Plugin.getInstance().getParkour().getConfigurationSection("Parkours").getKeys(false)) {
                inv.addItem(ItemUtil.createItem(Material.SIGN, Plugin.getInstance().getParkour().getString("Parkours." + key + ".name")));
            }
        }

        p.openInventory(inv);
    }

    public void selectModGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "§0§lGZHousing SelMOD");
        inv.setItem(0, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(1, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(2, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(3, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(4, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(5, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(6, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(7, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(8, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(9, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(17, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(18, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(19, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(20, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(21, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(22, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(23, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(24, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(25, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(26, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));

        inv.setItem(10, ItemUtil.createSkull("§9§lZOBRAZIT LIST HRACU", "Steav"));
        inv.setItem(12, ItemUtil.createItem(Material.GOLD_SPADE, "§6§lEDITOVAT MAPU"));
        ArrayList<String> st = new ArrayList<>();
        st.add("§7Kliknutim nastavis item co drzis prave v ruce");
        inv.setItem(14, ItemUtil.createItem(Material.DIAMOND, "§b§lNASTAVENI ODMENY", st));
        st.clear();
        inv.setItem(16, ItemUtil.createItem(Material.BARRIER, "§4§lSMAZAT"));

        p.openInventory(inv);
    }

    public void listINV(Player p) {
        HashMap<String, ItemStack> items = new HashMap<>();
        items.clear();
        if (!(Plugin.getInstance().getParkour().getString("Parkours." + nowpar.get(p) + ".donePlayers") == null)) {
            for (String pl : (ArrayList<String>) Plugin.getInstance().getParkour().getList("Parkours." + nowpar.get(p) + ".donePlayers")) {
                ItemStack parcela = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
                meta.setOwner(pl);
                meta.setDisplayName("§7" + pl);
                parcela.setItemMeta(meta);

                items.put(pl, parcela);
            }
        }
        ParkourScoller.getInstance(items, "§c§lGZHousing ParList", p);


    }
}
