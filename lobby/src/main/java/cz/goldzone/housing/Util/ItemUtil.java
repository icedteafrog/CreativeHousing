package cz.goldzone.housing.Util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class ItemUtil {
    public static ItemStack createItem(Material mat,String name) {
        ItemStack i = new ItemStack(mat);
        ItemMeta ie = i.getItemMeta();
        ie.setDisplayName(name);
        i.setItemMeta(ie);
        return i;
    }
    public static ItemStack createItem(Material mat, String name, ArrayList<String> lore) {
        ItemStack i = new ItemStack(mat);
        ItemMeta ie = i.getItemMeta();
        ie.setLore(lore);
        ie.setDisplayName(name);
        i.setItemMeta(ie);
        return i;
    }
    public static ItemStack createItem(Material mat,String name,byte b) {
        ItemStack i = new ItemStack(mat,1, b);
        ItemMeta ie = i.getItemMeta();
        ie.setDisplayName(name);
        i.setItemMeta(ie);
        return i;
    }
    public static ItemStack createCustomHead(String name,String value) {
        String signature = "H116D5fhmj/7BVWqiRQilXmvoJO6wJzXH4Dvou6P2o9YMb+HaJT8s9+zt03GMYTipzK+NsW2D2JfzagnxLUTuiOtrCHm6V2udOM0HG0JeL4zR0Wn5oHmu+S7kUPUbt7HVlKaRXry5bobFQ06nUf7hOV3kPfpUJsfMajfabmoJ9RGMRVot3uQszjKOHQjxyAjfHP2rjeI/SktBrSscx0DzwBW9LCra7g/6Cp7/xPQTIZsqz2Otgp6i2h3YpXJPy02j4pIk0H4biR3CaU7FB0V4/D1Hvjd08giRvUpqF0a1w9rbpIWIH5GTUP8eLFdG/9SnHqMCQrTj4KkQiN0GdBO18JvJS/40LTn3ZLag5LBIa7AyyGus27N3wdIccvToQ6kHHRVpW7cUSXjircg3LOsSQbJmfLoVJ/KAF/m+de4PxIjOJIcbiOkVyQfMQltPg26VzRiu3F0qRvJNAAydH8AHdaqhkpSf6yjHqPU3p3BHFJld5o59WoD4WNkE3wOC//aTpV/f9RJ0JQko08v2mGBVKx7tpN7vHD1qD5ILzV1nDCV1/qbKgiOK9QmdXqZw9J3pM/DHtZ6eiRKni9BuGWlbWFN/qfFO2xY+J7SYFqTxBbffmvwvuF83QP5UdRTNVLYoV5S+yR5ac7fVWUZmLbq7tawyuCu0Dw24M9E1BSnpSc=";

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", value, signature));

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
        skullMeta.setDisplayName(name);
        skull.setItemMeta(skullMeta);

        return skull;
    }


    public static ItemStack setString(String player,ItemStack itemold) {
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemold);
        NBTTagCompound tag = item.getTag();
        if (tag != null) {
            tag.set("player", new NBTTagString(player));
        }
        item.setTag(tag);
        return CraftItemStack.asBukkitCopy(item);
    }

    public static String getStringFromItem(ItemStack i) {
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = item.getTag();
        return tag != null ? ((NBTTagString) tag.get("player")).c_() : null;
    }
}
