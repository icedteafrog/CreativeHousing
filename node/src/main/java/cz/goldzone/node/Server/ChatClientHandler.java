package cz.goldzone.node.Server;

import cz.goldzone.node.Main;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientHandler implements Runnable {
    private Socket client;

    private Server server;

    private Scanner inputStream;

    public ChatClientHandler(Socket client, Server server) {
        this.client = client;
        this.server = server;
    }

    public void run() {
        try {
            this.inputStream = new Scanner(this.client.getInputStream());
            while (true) {
                if (!this.inputStream.hasNext())
                    return;
                String chatLine = this.inputStream.nextLine();
                if (!chatLine.equals("+testping"))
                    System.out.println(this.client.getRemoteSocketAddress() + " Command: " + chatLine);
                if (!Main.authUsers.contains(this.client) &&
                        !chatLine.contains("+auth")) {
                    Main.srv.Kick(this.client);
                    return;
                }
                Commands.onCommand(chatLine, this.client);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
