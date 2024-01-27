package duke.Command;

public class AddTaskCommand extends Command{
  public String description;
  public AddTaskCommand(String command, String description) {
    this.description = description;
  }

  @Override
  public String execute() {
    return "Added: " ;
  }
}
