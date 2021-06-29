package cz.goldzone.housing.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public abstract class ReflectionUtil {

    private static String version;
    private static Method getHandle;

    static {
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            getHandle = Objects.requireNonNull(getObcClass("entity.CraftPlayer")).getMethod("getHandle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getObcClass(String classname) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + classname);
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

    public static void sendPacket(Player p, Object packet) {
        try {
            Object playerHandle = getHandle.invoke(p);
            Object playerConnection = playerHandle.getClass().getField("playerConnection").get(playerHandle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static String getVersion() {
        if ("".equals(version)) {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        }
        return version;
    }

}