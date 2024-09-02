package databaseConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    public static Connection getConnection(){
        Properties prop = new Properties();
        Dotenv dotenv = Dotenv.load();
        Connection databaseLink = null;

        try {
            prop.load(new FileInputStream("src/config.properties"));
            String databaseName = prop.getProperty("databaseName");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");
            String databasePort = prop.getProperty("databasePort");
            String url = "jdbc:mysql://localhost:" + databasePort + "/" + databaseName;

            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return databaseLink;
    }

    public static void runScript(String filePath) throws Exception {

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        String sql = new String(Files.readAllBytes(Paths.get(filePath)));
        stmt.execute(sql);

        stmt.close();
        conn.close();
    }
}
