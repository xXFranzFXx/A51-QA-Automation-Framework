package db;

import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.client.result.Result;
import org.testng.Reporter;
import util.listeners.extentreports.ExtentManager;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class KoelDbActions extends KoelDb{
    private static Connection db ;
    private static PreparedStatement st;
    private static ResultSet rs;
    private String getUserPlaylists = """
            SELECT * FROM dbkoel.users u JOIN dbkoel.playlists p ON u.id = p.user_id WHERE u.email = ?
            """;
    private String getTotalSongCount = """
            SELECT COUNT(*) as count FROM dbkoel.songs
            """;
    private String artistNameQuery = """
        SELECT * FROM artists WHERE name = ?
        """;
   private String getSongByArtist = """
            SELECT * FROM dbkoel.songs s
            LEFT JOIN dbkoel.artists a
            ON a.id = s.artist_id WHERE a.name = ?
            """;

    private ResultSet simpleQuery(String sql) throws SQLException {
        db = getDbConnection();
        st=db.prepareStatement(sql);
        rs=st.executeQuery();
        ExtentManager.logInfoDetails("SQL statement: " + sql);
        return rs;
    }
    public ResultSet query(String sql, String[] args) throws SQLException {
        ExtentManager.logInfoDetails("SQL statement: " + sql);


        db = getDbConnection();
        st = db.prepareStatement(sql);
        if (args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].matches("-?(0|[1-9]\\d*)")) {
                    st.setInt(i+1 , Integer.parseInt(args[i]));
                }
                st.setString(i+1, args[i]);
            }
        } else {
            st.setString(1, args[0]);
        }
        rs = st.executeQuery();
        return rs;
    }
    public ResultSet artistQuery(String artist) throws SQLException {
        ExtentManager.logInfoDetails("Artist " + artist);
        String[] str = new String[]{artist};
        return query(artistNameQuery, str);
    }

    public ResultSet songByArtistJoinStmt(String artist) throws SQLException {
        String[] str = new String[]{artist};
        return query(getSongByArtist, str);
    }
    public ResultSet totalSongCount() throws SQLException {
        return simpleQuery(getTotalSongCount);
    }
    public ResultSet getUserPlaylst(String user) throws SQLException {
        ExtentManager.logInfoDetails("User " + user);

        String[] str = new String[]{user};
        return query(getUserPlaylists, str);
    }



}