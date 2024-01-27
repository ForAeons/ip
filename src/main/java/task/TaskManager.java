package task;

import java.sql.*;
import java.util.ArrayList;
import exceptions.BadTaskInputException;

public class TaskManager {
  public Task addTask(String type, String details) throws BadTaskInputException {
    switch (type) {
      case "todo":
        if (details == null || details.isEmpty()) {
          throw new BadTaskInputException(
            "Details of a todo cannot be empty.",
            "todo <description>",
            "todo read book",
           details
          );
        }

        return this.addTodoTask(details);

      case "deadline":
        String[] deadlineDetails = details.split(" /by ");

        if (deadlineDetails.length < 2) {
          throw new BadTaskInputException(
            "Details of a deadline must include a deadline.",
            "deadline <description> /by <deadline>",
            "deadline return book /by Sunday",
            details
          );
        }

        return this.addDeadlineTask(deadlineDetails[0], deadlineDetails[1]);

      case "event":
        String[] eventDetails = details.split(" /from | /to ");

        if (eventDetails.length < 3) {
          throw new BadTaskInputException(
            "Details of an event must include a start date and an end date.",
            "event <description> /from <start date> /to <end date>",
            "event project meeting /from Mon 2-4pm /to Mon 4-5pm",
            details);
        }

        return this.addEventTask(eventDetails[0], eventDetails[1], eventDetails[2]);

      default:
        throw new IllegalArgumentException("Invalid task type: " + type + "\n");
    }
  }

  public Task addTodoTask(String description) {
    String sql = "INSERT INTO tasks (type, description) VALUES (?, ?)";
    try {
      database.DB.execute(sql, Todo.type, description);
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
    }
    return new Todo(description);
  }

  public Task addDeadlineTask(String description, String deadline) {
    String sql = "INSERT INTO tasks (type, description, deadline) VALUES (?, ?, ?)";
    try {
      database.DB.execute(sql, Deadline.type, description, deadline);
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
    }
    return new Deadline(description, deadline);
  }

  public Task addEventTask(String description, String startDate, String endDate) {
    String sql = "INSERT INTO tasks (type, description, startDate, endDate) VALUES (?, ?, ?, ?)";
    try {
      database.DB.execute(sql, Event.type, description, startDate, endDate);
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
    }
    return new Event(description, startDate, endDate);
  }

  /**
   * Marks the task as done.
   *
   * @param taskID the index of the task to be marked as done
   * @return true if the task is successfully marked as done, false otherwise
   */
  public boolean markTaskAsDone(int taskID) {
    boolean isDone = false;

    String sql = "SELECT isDone FROM tasks WHERE id = ?";
    try {
      ResultSet rs = database.DB.select(sql, String.valueOf(taskID + 1));
      if (rs.next()) {
        isDone = rs.getBoolean("isDone");
      }
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }

    if (isDone) {
      return false;
    }

    sql = "UPDATE tasks SET isDone = 1 WHERE id = ?";
    try {
      database.DB.execute(sql, String.valueOf(taskID + 1));
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }

    return true;
  }

  /**
   * Unmarks the task as done.
   *
   * @param taskID the index of the task to be unmarked as done
   * @return true if the task is successfully unmarked as done, false otherwise
   */
  public boolean unmarkTaskAsDone(int taskID) {
    boolean isDone = false;

    String sql = "SELECT isDone FROM tasks WHERE id = ?";
    try {
      ResultSet rs = database.DB.select(sql, String.valueOf(taskID + 1));
      if (rs.next()) {
        isDone = rs.getBoolean("isDone");
      }
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }

    if (!isDone) {
      return false;
    }

    sql = "UPDATE tasks SET isDone = 0 WHERE id = ?";
    try {
      database.DB.execute(sql, String.valueOf(taskID + 1));
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }

    return true;
  }

  public Task deleteTask(int taskID) {
    Task task = this.getTask(taskID);

    String sql = "DELETE FROM tasks WHERE id = ?";
    try {
      database.DB.execute(sql, String.valueOf(taskID + 1));
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
    }

    return task;
  }

  public Task getTask(int taskNumber) {
    String sql = "SELECT * FROM tasks WHERE id = ?";

    try {
      ResultSet rs = database.DB.select(sql, String.valueOf(taskNumber + 1));
      if (rs.next()) {
        return this.sqlRowToTask(rs);
      }
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  public int getNumberOfTasks() {
    String sql = "SELECT COUNT(*) FROM tasks";
    try {
      ResultSet rs = database.DB.select(sql);
      if (rs.next()) {
        return rs.getInt(1);
      }
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
    }

    return -1;
  }

  public ArrayList<Task> getTasks() {
    ArrayList<Task> tasks = new ArrayList<>();

    String sql = "SELECT * FROM tasks";
    try {
      ResultSet rs = database.DB.select(sql);
      while (rs.next()) {
        tasks.add(this.sqlRowToTask(rs));
      }
      return tasks;
    } catch (java.sql.SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  private Task sqlRowToTask(ResultSet rs) throws SQLException {
    String type = rs.getString("type");
    String description = rs.getString("description");
    String deadline = rs.getString("deadline");
    String startDate = rs.getString("startDate");
    String endDate = rs.getString("endDate");
    boolean isDone = rs.getBoolean("isDone");

    switch (type) {
      case Todo.type:
        return new Todo(
          description,
          isDone
        );
      case Deadline.type:
        return new Deadline(
          description,
          deadline,
          isDone
        );
      case Event.type:
        return new Event(
          description,
          startDate,
          endDate,
          isDone
        );
      default:
        throw new IllegalArgumentException("Invalid task type: " + type);
    }
  }
}
