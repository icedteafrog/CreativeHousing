package cz.goldzone.housing.Utils;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class APIActionBar {

    private static Method a;
    private static Constructor<?> constructor;

    static {
        try {
            constructor = ReflectionUtil.getNMSClass("PacketPlayOutChat").getConstructor(ReflectionUtil.getNMSClass("IChatBaseComponent"), ChatMessageType.class);
            a = ReflectionUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class);

        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public static void sendActionbar(Player p, String message) {
        try {
            Object icbc = a.invoke(null, "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
            Object packet = constructor.newInstance(icbc, ChatMessageType.GAME_INFO);
            ReflectionUtil.sendPacket(p, packet);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
