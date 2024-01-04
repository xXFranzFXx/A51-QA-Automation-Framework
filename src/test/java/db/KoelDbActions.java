package db;
import org.mariadb.jdbc.Connection;
import util.listeners.TestListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class KoelDbActions extends KoelDbBase {
    private static Connection db ;
    private static PreparedStatement st;
    private static ResultSet rs;
    private final String getUserPwdInfo = """
            SELECT password, updated_at FROM dbkoel.users u WHERE u.email = ?
            """;
    private final String getNewUser = """
            SELECT * FROM dbkoel.users u WHERE u.email = ?
            """;
    private ResultSet simpleQuery(String sql) throws SQLException {
        db = getDbConnection();
        st=db.prepareStatement(sql);
        rs=st.executeQuery();
        TestListener.logInfoDetails("SQL statement: " + sql);
        return rs;
    }
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
   
    public ResultSet getPwdInfo(String user) throws SQLException {
        TestListener.logInfoDetails("String user: " + user);
        String[] str = new String[]{user};
        return query(getUserPwdInfo, str);
    }
    public ResultSet getUserInfo(String user) throws SQLException {
        TestListener.logInfoDetails("String user: " + user);
        String[] str = new String[]{user};
        return query(getNewUser, str);
    }

}