package database;

import java.io.File;
import java.sql.*;

public class DB {
  public static Connection conn;
  public static void connect() throws SQLException {
    String connectionUrl = config.config.dbConnectionUrl;

    String[] connectionUrlParts = connectionUrl.split("/");
    String databaseFolderName = connectionUrlParts[1];

    java.io.File databaseFolder = new java.io.File("."+File.separator+databaseFolderName);
    if (!databaseFolder.exists()) {
      databaseFolder.mkdir();
    }

    conn = DriverManager.getConnection(connectionUrl);
  }

  public static void disconnect() {
    try {
      conn.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void execute(String sql) throws SQLException {
    Statement stmt = conn.createStatement();
    stmt.execute(sql);
  }

  public static void execute(String sql, String... values) throws SQLException {
    PreparedStatement pstmt = conn.prepareStatement(sql);
    for (int i = 0; i < values.length; i++) {
      pstmt.setString(i + 1, values[i]);
    }
    pstmt.executeUpdate();
  }

  public static ResultSet select(String sql) throws SQLException {
    Statement stmt = conn.createStatement();
    stmt.execute(sql);
    return stmt.getResultSet();
  }

  public static ResultSet select(String sql, String... values) throws SQLException {
    PreparedStatement pstmt = conn.prepareStatement(sql);
    for (int i = 0; i < values.length; i++) {
      pstmt.setString(i + 1, values[i]);
    }
    pstmt.execute();
    return pstmt.getResultSet();
  }

  public static void AutoMigrate() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
      "type TEXT NOT NULL," +
      "description TEXT NOT NULL," +
      "deadline TEXT," +
      "startDate TEXT," +
      "endDate TEXT," +
      "isDone INTEGER DEFAULT 0" +
      ");";

    DB.execute(sql);
  }
}
