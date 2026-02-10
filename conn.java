package employee.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class conn {

    Connection connection;
    Statement statement;

    public conn() {
        try {
            // ✅ load driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ✅ create connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employeemanagement",
                    "root",
                    "Yogesh@1234"
            );

            // ✅ create statement
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}