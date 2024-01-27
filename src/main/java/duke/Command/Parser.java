package duke.Command;

import exceptions.BadInputException;

public class Parser {
  public final String command;
  public final String arguments;

  public Parser(String input) throws BadInputException {
    String[] inputParts = input.split(" ", 2);
    if (inputParts.length < 1) {
      throw new BadInputException(
        "Please enter a command!",
        "list, mark, unmark, todo, deadline, event, bye",
        null,
        null
      );
    }
    this.command = inputParts[0];
    this.arguments = inputParts.length > 1 ? inputParts[1] : null;
  }

  /**
   * Parses the task number from the inputs.
   *
   * @throws BadInputException if the task number is not specified, not an integer, or out of range
   * @return task number
   */
  public int parseTaskID() throws BadInputException {
    if (this.arguments == null || arguments.isEmpty()) {
      throw new BadInputException(
        "Please specify the task number!",
        String.format("%s <task number>", this.command),
        String.format("%s 1", this.arguments),
        null
      );
    }

    int taskID;

    try {
      taskID = Integer.parseInt(this.arguments);
    } catch (NumberFormatException e) {
      throw new BadInputException(
        "Task number must be an integer!",
        String.format("%s <task number>", this.command),
        String.format("%s 1", this.arguments),
        this.arguments
      );
    }

    return taskID;
  }
}
