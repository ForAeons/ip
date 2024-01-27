package duke.Command;

import java.sql.SQLException;
import exceptions.BadTaskInputException;
import task.Task;
import task.TaskManager;

public class AddTaskCommand extends Command {
  private final String command;
  private final String description;
  public AddTaskCommand(String command, String description) {
    this.command = command;
    this.description = description;
  }

  @Override
  public String execute(TaskManager tm) {
    try {
      Task task = tm.addTask(this.command, this.description);
      return "Got it. I've added this task:\n"
        + "   " + task + "\n"
        + String.format("Now you have %d tasks in the list.\n", tm.getNumberOfTasks());
    } catch (SQLException | BadTaskInputException e) {
      return e.getMessage();
    }
  }
}
