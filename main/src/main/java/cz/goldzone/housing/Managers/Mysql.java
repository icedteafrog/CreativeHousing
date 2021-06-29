package cz.goldzone.housing.Managers;

import lombok.Cleanup;
import cz.goldzone.housing.Plugin;

import java.sql.*;

public class Mysql {
    static Connection connection;
    String host, port, database, username, password;

    public Mysql() {
        this.host = "host";
        this.port = "3306";
        this.database = "database";
        this.username = "username";
        this.password = "password";

        try {
            openConnection();
            System.out.println("Mysql pripojeno!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            @Cleanup
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS gzhousing (`ServerName` TEXT,`MOTD` TEXT, `Votes` INT,`LastOn` TEXT);");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public PreparedStatement prepareStatement(String paramString) {
        try {
            return connection.prepareStatement(paramString);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?user=" + this.username + "&password=" + password + "&?autoReconnect=true");
    }

    public void closeConn() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getVotes() {
        String serverName = Plugin.serverName;
        int balance = 0;
        try {
            @Cleanup
            PreparedStatement statement = null;
            try {
                statement = this.prepareStatement("SELECT * FROM gzhousing WHERE ServerName=?");
            } catch (Exception e) {
                e.printStackTrace();
            }
            statement.setString(1, serverName);
            @Cleanup
            ResultSet results = statement.executeQuery();
            results.next();
            balance = results.getInt("Votes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void updateVotes(int votes) {
        try {
            @Cleanup
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE gzhousing SET Votes=" + votes + " WHERE ServerName='" + Plugin.serverName + "'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateMotd(String motd) {
        try {
            @Cleanup
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE gzhousing SET MOTD='" + motd + "' WHERE ServerName='" + Plugin.serverName + "'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
