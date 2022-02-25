package ir.alijk.atombansvelocity.database;

public class Queries {
        public static String SELECT_PLAYER_RECORDS = "SELECT * FROM banrecords WHERE player = ?";
        public static String SELECT_PLAYER_TYPE_RECORDS = "SELECT * FROM banrecords WHERE player = ? AND type = ?";
        public static String INSERT_RECORD = "INSERT INTO banrecords (player, type) VALUES (?, ?)";
        public static String SELECT_ALL_RECORDS = "SELECT * FROM banrecords ORDER BY created_at DESC";
        public static String DELETE_RECORDS = "DELETE FROM banrecords WHERE player = ? AND type = ?";
        public static String CREATE_RECORDS_TABLE = "CREATE TABLE IF NOT EXISTS banrecords (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player VARCHAR(16) NOT NULL," +
                "type VARCHAR(150) NOT NULL," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";
}
