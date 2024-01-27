package task;

public class Task {
  protected final String description;
  protected boolean isDone = false;

  public Task(String description) {
    this.description = description;
  }

  public Task(String description, boolean isDone) {
    this.description = description;
    this.isDone = isDone;
  }

  public String getDetails() {
    return this.description;
  }

  public String getStatusIcon() {
    return this.isDone ? "X" : " ";
  }

  public void toggleDone() {
    this.isDone = !this.isDone;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s", this.getStatusIcon(), this.getDetails());
  }
}
