package cz.goldzone.housing.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {
    static Connection connection;
    String host, port, database, username, password;
    Statement statement;

    public Mysql() {
        this.host = "host";
        this.port = "3306";
        this.database = "database";
        this.username = "username";
        this.password = "password";
        try {
            openConnection();
            statement = connection.createStatement();
            System.out.println("Mysql pripojeno!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
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
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getMotd(String serverName) {
        String balance = null;
        try {
            PreparedStatement statement = null;
            try {
                statement = this.prepareStatement("SELECT * FROM gzhousing WHERE ServerName=?");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            statement.setString(1, serverName);
            ResultSet results = statement.executeQuery();
            results.next();
            balance = results.getString("MOTD");

            statement.close();
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
    public int getVotes(String serverName) {
        int balance = 0;
        try {
            PreparedStatement statement = null;
            try {
                statement = this.prepareStatement("SELECT * FROM gzhousing WHERE ServerName=?");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            statement.setString(1, serverName);
            ResultSet results = statement.executeQuery();
            results.next();
            balance = results.getInt("Votes");

            statement.close();
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

}
