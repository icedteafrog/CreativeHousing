package cz.goldzone.node.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server extends Thread {
    private final int port = 7777;

    private ServerSocket serverSocket;

    private ArrayList<Socket> clientList;

    public void run() {
        System.out.println("Accepting clients...");
        while (true) {
            Socket client = null;
            try {
                client = this.serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.clientList.add(client);
            System.out.println("New client accepted..." + client.getRemoteSocketAddress());
            System.out.println("Total users: " + this.clientList.size());
            ChatClientHandler handler = new ChatClientHandler(client, this);
            Thread t = new Thread(handler);
            t.start();
        }
    }

    public synchronized void sendChatMessageToAll(String msg) throws IOException {
        for (Iterator<Socket> it = this.clientList.iterator(); it.hasNext(); ) {
            Socket client = it.next();
            if (!client.isClosed()) {
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                pw.println(msg);
                System.out.println(msg);
                pw.flush();
            }
        }
    }

    public synchronized void Kick(Socket client) {
        try {
            sendChatMessageToAll("Error :(");
            client.close();
            this.clientList.remove(client);
            System.out.println("Client " + client.getRemoteSocketAddress() + " zadal spatne heslo!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Socket> getOnline() {
        return this.clientList;
    }

    public Server() {
        try {
            this.serverSocket = new ServerSocket(7777);
            this.serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        this.clientList = new ArrayList<>();
    }
}
