package duke.Command;

public class ListTaskCommand extends Command {
  public static final String COMMAND_WORD = "list";

  @Override
  public String execute() {
    return "Here are the tasks in your list:\n" +
      "1. [T][ ] read book\n" +
      "2. [D][ ] return book (by: June 6th)\n" +
      "3. [E][ ] project meeting (at: Aug 6th 2-4pm)";
  }
}
