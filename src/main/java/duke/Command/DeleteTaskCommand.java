package duke.Command;

public class DeleteTaskCommand extends Command{
  public static final String COMMAND_WORD = "delete";
  private int taskID;
  public DeleteTaskCommand(int taskID) {
    this.taskID = taskID;
  }

  @Override
  public String execute() {
    return "Deleted: " ;
  }
}
