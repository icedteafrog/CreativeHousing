package cz.goldzone.node.ServerManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import cz.goldzone.node.Main;
import cz.goldzone.node.util.PMSG;
import cz.goldzone.node.Enums.Lang;

public class ConsoleWatcher {
    Main main;

    private final InputStream in;

    private final String name;

    private boolean stopped = false;

    public ConsoleWatcher(String name, InputStream in) {
        this.main = Main.getInstance();
        this.name = name;
        this.in = in;
        run();
    }

    public void killIt() {
        this.stopped = true;
    }

    private void run() {
        (new Thread(new Runnable() {
            public void run() {
                Scanner scanner = new Scanner(new BufferedInputStream(ConsoleWatcher.this.in));
                while (!ConsoleWatcher.this.stopped) {
                    try {
                        if (ConsoleWatcher.this.in.read() == -1) {
                            ConsoleWatcher.this.killIt();
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String next;
                    if ((next = scanner.nextLine()) != null) {
                        if (Main.debug) {
                            if (ConsoleWatcher.this.name.equalsIgnoreCase("in")) {
                                System.out.println(String.valueOf(ConsoleWatcher.this.name) + ":Server Log: " + next);
                                continue;
                            }
                            if (ConsoleWatcher.this.name.equalsIgnoreCase("error")) {
                                System.out.println("ERROR: " + next);
                                continue;
                            }
                            if (next.contains("For help, type \"help\" or \"?\"")) {
                                PMSG.sendMSG(ConsoleWatcher.this.name, Lang.DONES);
                                continue;
                            }
                            System.out.println(String.valueOf(ConsoleWatcher.this.name) + ":Server Log: " + next);
                        }
                        continue;
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException interruptedException) {
                    }
                }
            }
        })).start();
    }
}
