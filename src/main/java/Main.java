import dataaccess.MyCourseRepository;
import dataaccess.MySqlCourseRepository;
import dataaccess.MysqlDatabaseConnection;
import ui.Cli;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Establish a database connection
            Connection con = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "");
            MyCourseRepository repository = new MySqlCourseRepository(con);

            // Start CLI
            Cli myCli = new Cli(repository);
            myCli.start();
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage() + " SQL State: " + e.getSQLState());
        } catch (ClassNotFoundException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unerwarteter Fehler: " + e.getMessage());
        }
    }
}
