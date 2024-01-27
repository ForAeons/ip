package task;

import task.Task;

public class Todo extends Task {
  public static final String type = "T";
  public Todo(String description) {
    super(description);
  }

  public Todo(String description, boolean isDone) {
    super(description, isDone);
  }

  @Override
  public String toString() {
    return String.format("[%s]%s", type, super.toString());
  }
}
