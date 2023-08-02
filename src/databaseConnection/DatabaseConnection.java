package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public static Connection getConnection(){
        private String databaseName = "libraryproject";
        private String databaseUser = "root";
        private String databasePassword = "" ;
        private String url = "jdbc:mysql://localhost/" + databaseName;
        private Connection databaseLink;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }

        return databaseLink;
    }
}
