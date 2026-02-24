import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

void main() throws Exception {


    //use this connection for mac
    String url = "jdbc:mysql://localhost:8889/CatnOwner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    //use this connection for windows
    // String url = "jdbc:mysql://localhost:3306/CatnOwner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    String user = "root";
    String pass = "root";

    String sql = "SELECT 1";

    try (Connection c = DriverManager.getConnection(url, user, pass);
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        rs.next();
        int value = rs.getInt(1);

        IO.println("DB connection OK, SELECT 1 -> " + value);
    }
}