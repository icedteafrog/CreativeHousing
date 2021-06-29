package cz.goldzone.housing.GUI;

import cz.goldzone.housing.Util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;


public class ScrollerInventory {
    private static ScrollerInventory instance;

    public List<Inventory> pages = new ArrayList<>();
    public UUID id;
    public int currpage = 0;

    public Map<Integer,Map<Integer,String>> slots2 = new HashMap<>();
    public static Map<UUID, ScrollerInventory> users = new HashMap<>();
    //Running this will open a paged inventory for the specified player, with the items in the arraylist specified.
    public ScrollerInventory(Map<String,ItemStack> items, String name, Player p){
        this.id = UUID.randomUUID();
//create new blank page
        Inventory page = getBlankPage(name,p.getName());
        //According to the items in the arraylist, add items to the ScrollerInventory
        Map<Integer,String> slots = new HashMap<>();
        for(Map.Entry<String, ItemStack> module : items.entrySet()) {
            //If the current page is full, add the page to the inventory's pages arraylist, and create a new page to add the items.
            if(page.firstEmpty() == 46){
                pages.add(page);
                page = getBlankPage(name,p.getName());
                slots.put(page.firstEmpty(),module.getKey());
                page.addItem(module.getValue());
            }else{
//Add the item to the current page as per normal
                slots.put(page.firstEmpty(),module.getKey());
                page.addItem(module.getValue());
            }
        }
        pages.add(page);
//open page 0 for the specified player
        slots2.put(currpage,slots);
        p.openInventory(pages.get(currpage));
        users.put(p.getUniqueId(), this);
    }


    public static final String nextPageName = "§6§lDALSI STRANKA".replaceAll("&", "§");
    public static final String previousPageName = "§6§lPREDESLA STRANKA".replaceAll("&", "§");
    //This creates a blank page with the next and prev buttons
    private Inventory getBlankPage(String name,String playerName){
        Inventory page = Bukkit.createInventory(null, 54, name);



        page.setItem(0, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(1, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(2, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(3, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(4, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(5, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(6, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(7, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(8, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(9, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(17, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(27, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(35, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(36, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(37, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(38, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(39, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(40, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(41, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(42, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(43, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(44, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(45, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));
        page.setItem(53, ItemUtil.createItem(Material.STAINED_GLASS_PANE," ", (byte) 7));


        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§7Teleportujes se na nahodnou parcelu!");

        page.setItem(47, ItemUtil.createItem(Material.COMPASS,"§6§lNAHODNA PARCELA",lore));

        lore.clear();
        page.setItem(49, ItemUtil.createItem(Material.BARRIER,"§4§lPERMANENTNE SMAZAT",lore));


        lore.clear();
        lore.add(" ");
        lore.add("§7Kliknutim se teleportujes na svoji parcelu!");

        ItemStack parcela =  new ItemStack(Material.SKULL_ITEM, 1,(short) 3);
        SkullMeta meta = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setOwner(playerName);
        meta.setDisplayName("§6§lMOJE PARCELA");
        meta.setLore(lore);
        parcela.setItemMeta(meta);

        page.setItem(51,parcela);



        page.setItem(26, ItemUtil.createCustomHead(nextPageName,"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19"));
        page.setItem(18, ItemUtil.createCustomHead(previousPageName,"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="));
        return page;
    }
    public static ScrollerInventory getInstance(Map<String,ItemStack> items, String name, Player p) {
        ScrollerInventory.instance = null;
        ScrollerInventory.instance = new ScrollerInventory(items, name, p);
        return ScrollerInventory.instance;
    }
}
