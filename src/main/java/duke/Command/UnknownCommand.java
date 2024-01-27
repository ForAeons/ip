package duke.Command;

public class UnknownCommand extends Command {
  public static final String COMMAND_WORD = "unknown";
  private final String command;
  public UnknownCommand(String command) {
    this.command = command;
  }

  @Override
  public String execute() {
    return String.format("I'm sorry, but I don't know what \"%s\" means.", this.command);
  }
}
