package cz.goldzone.housing;

import cz.goldzone.housing.Commands.Start;
import cz.goldzone.housing.Events.SendLobby;
import cz.goldzone.housing.Events.ServerSwitchEvent;
import cz.goldzone.housing.Manager.AutoReconect;
import cz.goldzone.housing.ServerListener.ServerListener;

public class Plugin extends net.md_5.bungee.api.plugin.Plugin {
    public static ServerListener srv;
    public static String ip = "82.208.16.234";
    public static String pass = "1234";
    private static Plugin pl;

    @Override
    public void onEnable() {
        pl = this;
        srv = new ServerListener();
        this.getProxy().getPluginManager().registerCommand(this,new Start());
        this.getProxy().getPluginManager().registerListener(this,new ServerSwitchEvent());
        this.getProxy().getPluginManager().registerListener(this,new SendLobby());
        AutoReconect.Check();
    }

    @Override
    public void onDisable() {
        srv.sendCMD("+exit");
    }

    public static Plugin getInstance() {
        return pl;
    }
}
