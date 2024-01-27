package task;

import task.Task;

public class Deadline extends Task {
  public static final String type = "D";
  private final String deadline;

  public Deadline(String description, String deadline) {
    super(description);
    this.deadline = deadline;
  }

  public Deadline(String description, String deadline, boolean isDone) {
    super(description, isDone);
    this.deadline = deadline;
  }

  @Override
  public String toString() {
    return String.format("[%s]%s (by: %s)", type, super.toString(), this.deadline);
  }
}
