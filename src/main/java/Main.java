import dataaccess.MyCourseRepository;
import dataaccess.MySqlCourseRepository;
import dataaccess.MysqlDatabaseConnection;
import ui.Cli;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {
            Cli myCli = new Cli(new MySqlCourseRepository());
            myCli.start();
        }
        catch (SQLException e){
            System.out.println("Datenbankfehler: " + e.getMessage()+ " SQL State: "+e.getSQLState());
        }
        catch (ClassNotFoundException e){
            System.out.println("Datenbankfehler: " + e.getMessage());

        }
    }
}
