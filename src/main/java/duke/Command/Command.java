package duke.Command;

public abstract class Command {

  public static Command Interpret(String input) {
    Parser parser = new Parser(input);

    switch (parser.command) {
      case "list":
        return new ListTaskCommand();
      case "mark":
        return new MarkTaskCommand(parser.parseTaskID());
      case "unmark":
        return new UnmarkTaskCommand(parser.parseTaskID());
      case "delete":
        return new DeleteTaskCommand(parser.parseTaskID());
      case "todo":
      case "deadline":
      case "event":
        return new AddTaskCommand(parser.command, parser.arguments);
      case "bye":
        return new ExitCommand();
      default:
        return new UnknownCommand(parser.command);
    }
  }

  /**
   * Executes the command.
   *
   * @return the output of the command that will be printed to the user
   */
  public abstract String execute();

  /**
   * Checks if the command is a termination command.
   *
   * @return true if the command is a termination command, false otherwise
   */
  public boolean terminate() {
    return false;
  }
}
