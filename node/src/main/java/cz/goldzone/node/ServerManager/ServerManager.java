package cz.goldzone.node.ServerManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cz.goldzone.node.Main;

public class ServerManager {
    private ProcessBuilder procBuild;

    private List<String> command;

    Main main;

    OutputStream out;

    Process proc;

    String serverName;

    String path;

    String memx;

    String mems;

    String port;

    String slots;

    InputStream in;

    InputStream errors;

    private ConsoleWatcher console;

    private ConsoleWatcher err;

    String jarFile = "spigot.jar";

    public ServerManager(String serverName, String patch, String memx, String mems, String port, String slots) {
        this.main = Main.getInstance();
        this.serverName = serverName;
        this.path = patch;
        this.memx = String.valueOf(memx) + "M";
        this.mems = String.valueOf(mems) + "M";
        this.port = port;
        this.slots = slots;
        prepare();
        startup();
    }

    public ServerManager(List<String> cmd, String server, String path) {
        this.command = this.command;
        this.serverName = this.serverName;
        this.path = path;
        System.out.println("command: " + this.command);
        System.out.println("path: " + this.path);
        this.procBuild = (new ProcessBuilder(this.command)).directory(new File(path));
    }

    private void prepare() {
        this.command = new ArrayList<>();
        this.command.add("java");
        if (this.memx.matches("(\\-Xmx)(.*)")) {
            this.command.add(this.memx);
        } else {
            this.command.add("-Xmx" + this.memx);
        }
        if (this.main.isWindows)
            this.command.add("-Djline.terminal=jline.UnsupportedTerminal");
        if (!this.jarFile.matches("(?i)(paperspigot).+"))
            this.command.add("-Dcom.mojang.eula.agree=true");
        this.command.add("-jar");
        this.command.add(this.jarFile);
        if (this.main.isWindows)
            this.command.add("--nojline");
        this.command.add("--port");
        this.command.add(this.port);
        this.command.add("--max-players");
        this.command.add(this.slots);
        System.out.println("command: " + this.command);
        System.out.println("path: " + this.path);
        this.procBuild = (new ProcessBuilder(this.command)).directory(new File(this.path));
    }

    private void startup() {
        try {
            this.proc = this.procBuild.start();
            this.in = this.proc.getInputStream();
            this.errors = this.proc.getErrorStream();
            this.out = this.proc.getOutputStream();
            Main.serverProcesses.add(this.proc);
            System.out.println("Starting server within GZHWrapper");
            this.console = new ConsoleWatcher(this.serverName, this.in);
            this.err = new ConsoleWatcher("error", this.errors);
            ProcessChacker pc = new ProcessChacker(this, this.proc, this.out);
            (new Thread(pc)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            this.out.write("stop".getBytes());
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.out.close();
            } catch (IOException iOException) {
            }
        }
        System.out.println(String.valueOf(this.serverName) + ">> Shutting down in progress please wait");
    }

    public void sendCMD(String Command) {
        try {
            this.out.write(Command.getBytes());
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(String.valueOf(this.serverName) + ">> Sending CMD: " + Command);
    }

    public void kill() {
        killThreads();
        this.proc.destroy();
        System.out.println(String.valueOf(this.serverName) + ">> Killed");
    }

    public String getPort() {
        return this.port;
    }

    public String getSlots() {
        return this.slots;
    }

    public String getServerName() {
        return this.serverName;
    }

    public void killThreads() {
        this.console.killIt();
        this.err.killIt();
    }

    public String getPath() {
        return this.path;
    }

    public List<String> getCommand() {
        return this.command;
    }

    public ConsoleWatcher getconsole() {
        return this.console;
    }
}
