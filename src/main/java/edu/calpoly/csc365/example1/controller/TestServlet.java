import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String JDBC_DB_URL = "jdbc:mysql://csc365.toshikuboi.net:3306/group01";

    // JDBC Database Credentials
    static final String JDBC_USER = "group01";
    static final String JDBC_PASS = "group01@CSC365";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connObj = null;
        PrintWriter out = response.getWriter();
        try {
            //checks if the driver class is available
            Class.forName(JDBC_DRIVER);
            //creates a connection to DB
            connObj = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);

            //create prepared sql statement
            Statement statement = connObj.createStatement();
            String sql = "SELECT * FROM customer";
            //execute the statement
            ResultSet resObj = statement.executeQuery(sql);
            //loop through the result set
            while (resObj.next()) {
                out.println(
                        //you have to know the data type of each attribute
                        "cid: " + resObj.getInt("cid") + " , lastName: " + resObj.getString("lastName") + " , address: " + resObj.getString("address"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
            if (connObj != null) {
                try {
                    connObj.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}