package cz.goldzone.node.ServerManager;

import java.io.IOException;
import java.io.OutputStream;

import cz.goldzone.node.Main;
import cz.goldzone.node.Server.BackSend;

public class ProcessChacker implements Runnable {
    private final Main main;

    private final Process proc;

    private final OutputStream out;

    private final ServerManager server;

    private boolean shutdown = false;

    private int frozen = 0;

    public ProcessChacker(ServerManager server, Process proc, OutputStream out) {
        this.main = Main.getInstance();
        this.server = server;
        this.proc = proc;
        this.out = out;
    }

    private void processCheck() {
        boolean enable = true;
        if (this.out != null && enable)
            try {
                this.out.write(13);
                this.out.flush();
            } catch (IOException e) {
                System.out.println("Server \"" + this.server.getServerName() + "\" may be stopped or frozen.");
                this.frozen++;
            }
    }

    public void run() {
        while (true) {
            processCheck();
            if (!this.proc.isAlive()) {
                if (this.frozen > 1) {
                    System.out.println("Server \"" + this.server.getServerName() + "\" stopped or frozen! Shutting down.");
                    BackSend.removeServer(this.server.getServerName());
                    this.shutdown = true;
                }
                if (!Main.managedServers.containsValue(this.server))
                    this.shutdown = true;
                this.frozen++;
            } else if (this.frozen > 0) {
                this.frozen--;
            }
            try {
                this.proc.exitValue();
                this.out.close();
                this.shutdown = true;
            } catch (IllegalThreadStateException | IOException illegalThreadStateException) {
            }
            if (this.shutdown)
                break;
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.valueOf(this.server.getServerName()) + " shut down.");
        BackSend.removeServer(this.server.getServerName());
        if (Main.managedServers.containsValue(this.server))
            Main.managedServers.remove(this.server.getServerName());
        this.server.killThreads();
        try {
            this.server.in.close();
            this.server.out.close();
            this.server.errors.close();
        } catch (IOException iOException) {
        }
    }
}
