package duke.Command;

public class UnmarkTaskCommand extends Command{
  public static final String COMMAND_WORD = "unmark";
  private int taskID;
  public UnmarkTaskCommand(int taskID) {
    this.taskID = taskID;
  }

  @Override
  public String execute() {
    return "Unmarked as done: " ;
  }
}
