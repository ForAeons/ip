package duke.command;

import java.sql.SQLException;

import java.util.ArrayList;

import database.TaskOrm;

public class FindTaskCommand extends Command {
  public static final String COMMAND_WORD = "find";

  private final String searchTerm;

  public FindTaskCommand(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  @Override
  public String execute() {
    TaskOrm tm = new TaskOrm();
    try {
      ArrayList<task.Task> tasks = tm.list(this.searchTerm);

      if (tasks.isEmpty()) {
        return "No tasks found!\n";
      }

      StringBuilder sb = new StringBuilder();
      for (task.Task task : tasks) {
        sb.append(String.format("%d. %s\n", task.taskID, task));
      }
      return sb.toString();
    } catch (SQLException e) {
      return e.getMessage();
    }
  }
}
