package cz.goldzone.housing.cons;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

public class MysqlData {

    // Constants
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";
    private static final String DEFAULT_DATABASE = "";
    private static final int DEFAULT_PORT = 3306;
    private static final boolean DEFAULT_ENABLED = false;

    // Variables
    @Getter
    private final String host, user, password, database;
    @Getter
    private final int port;
    @Getter @Setter
    private boolean enabled;

    /**
     * Constructor
     *
     * @param mysqlFile {@link org.bukkit.configuration.file.FileConfiguration} - Configuration of mysql.yml
     */
    public MysqlData(final FileConfiguration mysqlFile) {
        this.host = mysqlFile.isSet("MYSQL.HOST") ? mysqlFile.getString("MYSQL.HOST") : DEFAULT_HOST;
        this.user = mysqlFile.isSet("MYSQL.USER") ? mysqlFile.getString("MYSQL.USER") : DEFAULT_USER;
        this.password = mysqlFile.isSet("MYSQL.PASSWORD") ? mysqlFile.getString("MYSQL.PASSWORD") : DEFAULT_PASSWORD;
        this.database = mysqlFile.isSet("MYSQL.DATABASE") ? mysqlFile.getString("MYSQL.DATABASE") : DEFAULT_DATABASE;
        this.port = mysqlFile.isSet("MYSQL.PORT") ? mysqlFile.getInt("MYSQL.PORT") : DEFAULT_PORT;
        this.enabled = mysqlFile.isSet("MYSQL.ENABLED") ? mysqlFile.getBoolean("MYSQL.ENABLED") : DEFAULT_ENABLED;
    }

}
