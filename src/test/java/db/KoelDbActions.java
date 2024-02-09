package db;
import org.mariadb.jdbc.Connection;
import util.listeners.TestListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class KoelDbActions extends KoelDbBase {
    private static Connection db ;
    private static PreparedStatement st;
    private static ResultSet rs;
    private String getSmartPLaylist = """
            SELECT * FROM dbkoel.playlists p JOIN dbkoel.users u ON p.user_id = u.id WHERE u.email= ? AND p.name= ?
            """;
    private String getArtistsInDb = """
            SELECT a.name as name FROM dbkoel.artists a  ORDER BY a.name ASC
            """;
    private ResultSet query(String sql, String[] args) throws SQLException {
        TestListener.logInfoDetails("SQL statement: " + sql);
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
    private ResultSet simpleQuery(String sql) throws SQLException {
        db = getDbConnection();
        st=db.prepareStatement(sql);
        rs=st.executeQuery();
        TestListener.logInfoDetails("SQL statement: " + sql);
        return rs;
    }
    public ResultSet checkSmartPl(String user, String smartPl) throws SQLException {
        String[] str = new String[]{user, smartPl};
        return query(getSmartPLaylist, str);
    }
    public ResultSet checkArtistsInDb() throws SQLException {
        return simpleQuery(getArtistsInDb);
    }
}