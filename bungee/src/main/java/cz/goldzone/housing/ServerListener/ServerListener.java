package cz.goldzone.housing.ServerListener;

import cz.goldzone.housing.Events.OnReceiveEvent;
import cz.goldzone.housing.Plugin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener implements Runnable{
    private Thread t;
    public Socket link;
    private Scanner inputStream;
    private PrintWriter outputStream;
    private int port = 7777;
    public boolean online = false;

    public ServerListener() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException {
        link = null;
        try {
            link = new Socket(Plugin.ip, port);
            link.setReuseAddress(true);
            online = true;
            System.out.println("NODE: Pripojeno");
        } catch (IOException e) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(" ");
            System.out.println("Noda je offline! CreativeHousing nebude fungovat");
            System.out.println(" ");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return;
        }
        inputStream = new Scanner(link.getInputStream());
        outputStream = new PrintWriter(link.getOutputStream());

        t = new Thread(this);
        t.start();
        this.sendCMD("+auth " + Plugin.pass);
    }
    public void kill() {
        try {
            if(!(link == null)) {
                link.close();
                link = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendCMD(String cmd) {
        if(!(online)) {
            return;
        }

        outputStream.println(cmd);
        outputStream.flush();
    }
    public boolean testCon() {
        if(link == null) {
            return false;
        }
        outputStream.println("+testping");
        outputStream.flush();
        if(outputStream.checkError()) {
            return false;
        }

        return true;
    }
    @Override
    public void run() {
        while (inputStream.hasNextLine()) {
            if (inputStream.hasNextLine()) {
                OnReceiveEvent.OnReceive(inputStream.nextLine());
            }
        }
    }
}

