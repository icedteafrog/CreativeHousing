package cz.goldzone.housing.Managers;

import cz.goldzone.housing.Plugin;
import cz.goldzone.housing.cons.MysqlData;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.sql.*;

/**
 * MySQL.java edited by JesusChrist69
 * Edited 2021-07-01 17:16:15
 */
public class Mysql {

    @Getter
    @Setter
    private Connection connection;
    private MysqlData data;
    @Getter
    private boolean connected;

    /**
     * Constructor
     *
     * @param data {@link cz.goldzone.housing.cons.MysqlData}
     */
    public Mysql(MysqlData data) {
        this.data = data;
        this.connected = false;

        if (data.isEnabled()) {
            openConnection();
        }
    }

    /**
     * Get current amount of votes for current server
     *
     * @return
     */
    public int getVotes() {
        if (!data.isEnabled() || !connected) {
            return 0;
        }
        int votes = 0;
        PreparedStatement statement = null;
        try {
            statement = this.getConnection().prepareStatement("SELECT * FROM gzhousing WHERE server_name=?");
            statement.setString(1, Plugin.getInstance().getServerName());

            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    votes = results.getInt("votes");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return votes;
    }

    /**
     * Update server votes
     *
     * @param votes (int) - new amount of votes
     */
    public void updateVotes(int votes) {
        if (!data.isEnabled() || !connected) {
            return;
        }
        PreparedStatement statement = null;
        try {
            statement = this.getConnection().prepareStatement("UPDATE gzhousing SET votes=? WHERE server_name=?");
            statement.setInt(1, votes);
            statement.setString(2, Plugin.getInstance().getServerName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Update server motd
     *
     * @param motd {@link java.lang.String} - New MOTD
     */
    public void updateMotd(String motd) {
        if (!data.isEnabled() || !connected) {
            return;
        }
        PreparedStatement statement = null;
        try {
            statement = this.getConnection().prepareStatement("UPDATE gzhousing SET motd=? WHERE server_name=?");
            statement.setString(1, motd);
            statement.setString(2, Plugin.getInstance().getServerName());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Open connection to MySQL database if possible
     */
    private void openConnection() {
        if (!data.isEnabled()) {
            return;
        }
        try {
            synchronized (this) {
                if (this.getConnection() != null && !this.getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                this.setConnection(DriverManager.getConnection("jdbc:mysql://" + data.getHost() + ":" + data.getPort() + "/" +
                        data.getDatabase(), data.getUser(), data.getPassword()));
                this.connected = true;
                Bukkit.getLogger().info("Successfully connected to MySQL database.");
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Could not connect to MySQL database!");
            e.printStackTrace();
        } finally {
            if (this.isConnected()) {
                runSync(this::checkTables);
            }
        }
    }

    /**
     * Close connection to MySQL database if possible
     */
    public void disconnect() {
        if (this.getConnection() != null) {
            try {
                this.getConnection().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.connected = false;
    }

    /**
     * Run async task
     *
     * @param task {@link java.lang.Runnable} - task that should be executed
     */
    private void runSync(final Runnable task) {
        Bukkit.getServer().getScheduler().runTask(Plugin.getInstance(), task);
    }

    /**
     * Check MySQL tables, if not found create them
     */
    private void checkTables() {
        if (!data.isEnabled() || !connected) {
            return;
        }
        PreparedStatement statement = null;
        try {
            statement = this.getConnection()
                    .prepareStatement("CREATE TABLE IF NOT EXISTS gzhousing (server_name TEXT, motd TEXT, votes INT, last_on TIMESTAMP);");
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
