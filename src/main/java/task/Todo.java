package task;

import task.Task;

public class Todo extends Task {
  public static final String type = "T";
  public Todo(int taskID, String description) {
    super( taskID, description);
  }

  public Todo(int taskID,  String description, boolean isDone) {
    super(taskID, description, isDone);
  }

  @Override
  public String toString() {
    return String.format("[%s]%s", type, super.toString());
  }
}
