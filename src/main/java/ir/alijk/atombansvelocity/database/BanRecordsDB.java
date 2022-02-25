package ir.alijk.atombansvelocity.database;

import java.sql.SQLException;
import java.sql.Statement;

public class BanRecordsDB {
        public static void createTables() {
                try {
                        final Statement statement = DataSource.getConnection().createStatement();
                        String query = Queries.CREATE_RECORDS_TABLE;
                        statement.execute(query);
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
}
