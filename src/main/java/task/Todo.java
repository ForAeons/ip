public class Todo extends Task {
  public static final String type = "T";
  public Todo(String description) {
    super(description);
  }

  @Override
  public String toString() {
    return String.format("[%s]%s", type, super.toString());
  }
}
