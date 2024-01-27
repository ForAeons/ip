package database;

import java.sql.*;
import java.util.ArrayList;

public class SQLiteDriverConnection {
  public static Connection connection;
  public static void connect() {
    String connectionUrl = config.config.dbConnectionUrl;
    try {
      connection = DriverManager.getConnection(connectionUrl);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage()); // we should not continue if we cannot connect to the database
    }
  }

  public static void disconnect() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void execute(String sql) {
    try {
      Statement stmt = connection.createStatement();
      stmt.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static ResultSet select(String sql) {
    try {
      Statement stmt = connection.createStatement();
      stmt.execute(sql);
      return stmt.getResultSet();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
