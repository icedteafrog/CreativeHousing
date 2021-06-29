package cz.goldzone.node.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cz.goldzone.node.util.DateUtil;

public class Mysql {
    static Connection connection;

    String host = "host";
    String port = "3306";
    String database = "database";
    String username = "username";
    String password = "password";
    Statement statement;

    public Mysql() {
        try {
            openConnection();
            this.statement = connection.createStatement();
            System.out.println("Mysql pripojeno!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS gzhousing (`ServerName` TEXT,`MOTD` TEXT, `Votes` INT,`LastOn` TEXT);");
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
        if (connection != null && !connection.isClosed())
            return;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?user=" + this.username + "&password=" + this.password + "&?autoReconnect=true");
    }

    public void closeConn() {
        try {
            this.statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateLastOn(String serverName) {
        ifExist(serverName);
        try {
            this.statement.executeUpdate("UPDATE gzhousing SET LastOn='" + DateUtil.get30days() + "' WHERE ServerName='" + serverName + "'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteServer(String serverName) {
        try {
            this.statement.executeUpdate("DELETE FROM `gzhousing` WHERE `ServerName` = '" + serverName + "'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void ifExist(String serverName) {
        ResultSet result = null;
        try {
            result = this.statement.executeQuery("SELECT * FROM gzhousing WHERE ServerName = '" + serverName + "';");
            if (result.next())
                return;
            this.statement.executeUpdate("INSERT INTO `gzhousing`(`ServerName`, `MOTD`, `Votes`, `LastOn`,`Slots`,`Ram`) VALUES ('" + serverName + "','CreativeHousing',0,'" + DateUtil.get30days() + "',20,512);");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getVotes(String serverName) {
        int balance = 0;
        try {
            PreparedStatement statement = null;
            try {
                statement = prepareStatement("SELECT * FROM gzhousing WHERE ServerName=?");
            } catch (Exception e) {
                e.printStackTrace();
            }
            statement.setString(1, serverName);
            ResultSet results = statement.executeQuery();
            results.next();
            balance = results.getInt("Votes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public int getSlots(String serverName) {
        int balance = 0;
        try {
            PreparedStatement statement = null;
            try {
                statement = prepareStatement("SELECT * FROM gzhousing WHERE ServerName=?");
            } catch (Exception e) {
                e.printStackTrace();
            }
            statement.setString(1, serverName);
            ResultSet results = statement.executeQuery();
            results.next();
            balance = results.getInt("Slots");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public int getRam(String serverName) {
        int balance = 0;
        try {
            PreparedStatement statement = null;
            try {
                statement = prepareStatement("SELECT * FROM gzhousing WHERE ServerName=?");
            } catch (Exception e) {
                e.printStackTrace();
            }
            statement.setString(1, serverName);
            ResultSet results = statement.executeQuery();
            results.next();
            balance = results.getInt("Ram");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
}
