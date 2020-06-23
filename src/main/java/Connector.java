import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static Connection conn;

    private Connector(){
    }

    public static Connection getConn(){
        try {
            if (conn==null || conn.isClosed()){
                conn = DriverManager.getConnection("jdbc:mysql://db.diplomportal.dk/s195495?" +
                        "user=s195495&password=qe9zsGBJNuaOVBuyJuqAH");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
