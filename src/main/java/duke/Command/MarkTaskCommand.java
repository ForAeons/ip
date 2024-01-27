package duke.Command;

public class MarkTaskCommand extends Command{
  public static final String COMMAND_WORD = "done";
  private int taskID;
  public MarkTaskCommand(int taskID) {
    this.taskID = taskID;
  }

  @Override
  public String execute() {
    return "Marked as done: " ;
  }
}
