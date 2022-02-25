package ir.alijk.atombansvelocity.database.models;

import ir.alijk.atombansvelocity.database.DataSource;
import ir.alijk.atombansvelocity.database.Queries;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BanRecord {
        @Setter @Getter private Integer id;
        @Setter @Getter private String player;
        @Setter @Getter private String type;
        @Setter @Getter private String createdAt;

        public BanRecord(String player, String type) {
                setPlayer(player);
                setType(type);
        }

        public void save() {
                try {
                        PreparedStatement pst = DataSource.getConnection().prepareStatement(Queries.INSERT_RECORD);
                        pst.setString(1, getPlayer());
                        pst.setString(2, getType());
                        DataSource.executeQueryAsync(pst);
                } catch (SQLException exception) {
                        exception.printStackTrace();
                }
        }

        public static void deletePlayerRecords(String player, String type) {
                try {
                        PreparedStatement pst = DataSource.getConnection().prepareStatement(Queries.DELETE_RECORDS);
                        pst.setString(1, player);
                        pst.setString(2, type);
                        DataSource.executeQueryAsync(pst);
                } catch (SQLException exception) {
                        exception.printStackTrace();
                }
        }


        public static int recordsCount(String player, String type) {

                List<BanRecord> records = new ArrayList<>();

                try {
                        Connection con = DataSource.getConnection();
                        PreparedStatement pst = con.prepareStatement(Queries.SELECT_PLAYER_TYPE_RECORDS);
                        pst.setString(1, player);
                        pst.setString(2, type);
                        ResultSet rs = pst.executeQuery();
                        records = new ArrayList<>();
                        BanRecord record;
                        while (rs.next()) {
                                Integer id = rs.getInt("id");
                                String dbPlayer = rs.getString("player");
                                String dbType = rs.getString("type");
                                String createdAt = rs.getString("created_at");

                                record = new BanRecord(dbPlayer, dbType);

                                record.setId(id);
                                record.setCreatedAt(createdAt);

                                records.add(record);
                        }
                } catch (SQLException exception) {
                        exception.printStackTrace();
                }

                return records.size();
        }


}
