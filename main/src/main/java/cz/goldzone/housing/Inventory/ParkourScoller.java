package cz.goldzone.housing.Inventory;

import cz.goldzone.housing.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParkourScoller {
    public static final String nextPageName = "§6§lDALSI STRANKA".replaceAll("&", "§");
    public static final String previousPageName = "§6§lPREDESLA STRANKA".replaceAll("&", "§");
    public static HashMap<Integer, String> slots = new HashMap<>();
    public static HashMap<UUID, ParkourScoller> users = new HashMap<UUID, ParkourScoller>();
    private static ParkourScoller instance;
    public ArrayList<Inventory> pages = new ArrayList<Inventory>();
    public UUID id;
    public int currpage = 0;
    //Running this will open a paged inventory for the specified player, with the items in the arraylist specified.
    public ParkourScoller(HashMap<String, ItemStack> items, String name, Player p) {
        this.id = UUID.randomUUID();
//create new blank page
        Inventory page = getBlankPage(name, p.getName());
        //According to the items in the arraylist, add items to the ScrollerInventory
        for (Map.Entry<String, ItemStack> module : items.entrySet()) {
            //If the current page is full, add the page to the inventory's pages arraylist, and create a new page to add the items.
            if (page.firstEmpty() == 46) {
                pages.add(page);
                page = getBlankPage(name, p.getName());
                slots.put(page.firstEmpty(), module.getKey());
                page.addItem(module.getValue());
            } else {
//Add the item to the current page as per normal
                slots.put(page.firstEmpty(), module.getKey());
                page.addItem(module.getValue());
            }
        }
        pages.add(page);
//open page 0 for the specified player
        p.openInventory(pages.get(currpage));
        users.put(p.getUniqueId(), this);
    }

    public static ParkourScoller getInstance(HashMap<String, ItemStack> items, String name, Player p) {
        ParkourScoller.instance = null;
        ParkourScoller.instance = new ParkourScoller(items, name, p);
        return ParkourScoller.instance;

    }

    //This creates a blank page with the next and prev buttons
    private Inventory getBlankPage(String name, String playerName) {
        Inventory inv = Bukkit.createInventory(null, 54, name);


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
        inv.setItem(50, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(51, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(52, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(53, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));


        inv.setItem(49, ItemUtil.createItem(Material.BARRIER, "§4§lZPET"));


        inv.setItem(26, ItemUtil.createCustomHead(nextPageName, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19"));
        inv.setItem(18, ItemUtil.createCustomHead(previousPageName, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="));
        return inv;
    }
}