package databaseConnection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.ibatis.jdbc.ScriptRunner;

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
        String script = new String(Files.readAllBytes(Paths.get(filePath)));
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        // Split the script into individual statements
        String[] statements = script.split(";");

        for (String statement : statements) {
            // Trim whitespace and skip empty statements
            statement = statement.trim();
            if (!statement.isEmpty()) {
                try {
                    stmt.execute(statement);
                } catch (SQLException e) {
                    System.err.println("Error executing statement: " + statement);
                    System.err.println("Error message: " + e.getMessage());
                    // Optionally, you can throw the exception here if you want to stop execution on first error
                    // throw e;
                }
            }
        }

        stmt.close();
        conn.close();
    }
    public static void runScript1() throws Exception {
        Connection conn = getConnection();
        ScriptRunner sr = new ScriptRunner(conn);
        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader("src/main/resources/libraryproject.sql"));
        //Running the script
        sr.runScript(reader);
        conn.close();
    }
}
