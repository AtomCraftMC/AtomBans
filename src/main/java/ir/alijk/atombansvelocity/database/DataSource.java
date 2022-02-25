package ir.alijk.atombansvelocity.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ir.alijk.atombansvelocity.AtomBansVelocity;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSource {
        private static HikariConfig config = new HikariConfig();
        private static HikariDataSource ds;
        private static Connection connection;

        public static void SQLite() throws SQLException, IOException, ClassNotFoundException {
                Class.forName("org.sqlite.JDBC");

                File file = new File(AtomBansVelocity.getInstance().getFolder().toString(), "data.db");
                if (!file.exists()) file.createNewFile();

                config.setJdbcUrl("jdbc:sqlite:" + AtomBansVelocity.getInstance().getFolder().toString() + "/data.db");
                config.setConnectionTestQuery("SELECT 1");
                config.addDataSourceProperty("cachePrepStmts", "true");
                config.addDataSourceProperty("prepStmtCacheSize", "250");
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

                ds = new HikariDataSource(config);

                connection = ds.getConnection();

                BanRecordsDB.createTables();
        }

        public static Connection getConnection() {
                return connection;
        }

        public static void executeQueryAsync(PreparedStatement statement) {
                try {
                        statement.execute();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
}
