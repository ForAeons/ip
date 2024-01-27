import java.util.Scanner;
import exceptions.BadInputException;
import exceptions.UnknownCommandException;
import task.*;

public class Duke {
  private static final String logo = " ____        _        \n"
    + "|  _ \\ _   _| | _____ \n"
    + "| | | | | | | |/ / _ \\\n"
    + "| |_| | |_| |   <  __/\n"
    + "|____/ \\__,_|_|\\_\\___|\n";
  private final String chatBotName;
  private final TaskManager taskManager = new TaskManager();

  public Duke (String name) {
    this.chatBotName = name;
  }

  private void printIndentedln(String message) {
    System.out.println("    " + message);
  }

  private void printHorizontalln() {
    this.printIndentedln("____________________________________________________________\n");
  }

  private void greet() {
    this.printHorizontalln();
    this.printIndentedln("Hello! I'm " + this.chatBotName);
    this.printIndentedln("What can I do for you?");
    this.printHorizontalln();
  }

  private void exitMessage() {
    this.printHorizontalln();
    this.printIndentedln("Bye. Hope to see you again soon!");
    this.printHorizontalln();
  }

  private void addTask(String... inputs) throws BadInputException {
    String type = inputs[0];

    StringBuilder details = new StringBuilder();
    for (int i = 1; i < inputs.length; i++) {
      details.append(inputs[i]);
      if (i != inputs.length - 1) {
        details.append(" ");
      }
    }

    Task task = this.taskManager.addTask(type, details.toString());

    this.printIndentedln("Got it. I've added this task:");
    this.printIndentedln("  " + task);
    this.printIndentedln(String.format("Now you have %d tasks in the list.", taskManager.getNumberOfTasks()));
  }

  private  void deleteTask(String... inputs) {
    int taskIndex = parseTaskNumber(inputs);

    Task task = this.taskManager.deleteTask(taskIndex);

    this.printIndentedln("Noted. I've removed this task:");
    this.printIndentedln("  " + task);
    this.printIndentedln(String.format("Now you have %d tasks in the list.", taskManager.getNumberOfTasks()));
  }

  private void listTasks() {
    if (this.taskManager.getNumberOfTasks() == 0) {
      this.printIndentedln("No tasks added yet!");
      return;
    }

    int i = 1;
    for (Task task : this.taskManager.getTasks()) {
      this.printIndentedln(String.format("%d. %s", i++, task));
    }
  }

  /**
   * Parses the task number from the inputs.
   *
   * @param inputs the inputs for the mark command
   * @throws BadInputException if the task number is not specified, not an integer, or out of range
   * @return task number
   */
  private int parseTaskNumber(String[] inputs) throws BadInputException {
    if (inputs.length < 2) {
      throw new BadInputException(
        "Please specify the task number!",
        String.format("%s <task number>", inputs[0]),
        String.format("%s 1", inputs[0]),
        null
      );
    }

    int taskIndex;

    try {
      int taskNumber = Integer.parseInt(inputs[1]);
      taskIndex = taskNumber - 1;
    } catch (NumberFormatException e) {
      throw new BadInputException(
        "Task number must be an integer!",
        String.format("%s <task number>", inputs[0]),
        String.format("%s 1", inputs[0]),
        inputs[1]
      );
    }

    if (taskIndex < 0 || taskIndex >= this.taskManager.getNumberOfTasks()) {
      throw new BadInputException(
        "Task number out of range!",
        String.format("%s <task number>", inputs[0]),
        String.format("%s 1", inputs[0]),
        inputs[1]
      );
    }

    return taskIndex;
  }

  private void markTaskAsDone(String[] inputs) {
    int taskIndex = this.parseTaskNumber(inputs);

    boolean success = this.taskManager.markTaskAsDone(taskIndex);
    if (!success) {
      printIndentedln("Task is already done!");
      return;
    }

    this.printIndentedln("Nice! I've marked this task as done:");
    this.printIndentedln("  " + taskManager.getTask(taskIndex));
  }

  private  void unmarkTaskAsDone(String[] inputs) {
    int taskIndex = this.parseTaskNumber(inputs);

    boolean success = this.taskManager.unmarkTaskAsDone(taskIndex);
    if (!success) {
      this.printIndentedln("Task is not done yet!");
      return;
    }

    this.printIndentedln("Ok, I've marked this task as not done yet:");
    this.printIndentedln("  " + taskManager.getTask(taskIndex));
  }

  private void evaluateInputs(String[] inputs) throws RuntimeException {
    if (inputs.length == 0) {
      throw new BadInputException(
        "Please enter a command!",
        "list, mark, unmark, todo, deadline, event, bye",
        null,
        null
      );
    }

    String command = inputs[0];

    System.out.println(command);

    switch (command) {
      case "list":
        System.out.println("running list");
        this.listTasks();
        break;
      case "mark":
        System.out.println("running mark");
        this.markTaskAsDone(inputs);
        break;
      case "unmark":
        System.out.println("running unmark");
        this.unmarkTaskAsDone(inputs);
        break;
      case "delete":
        System.out.println("running delete");
        this.deleteTask(inputs);
        break;
      case "todo":
      case "deadline":
      case "event":
        System.out.println("running add");
        this.addTask(inputs);
        break;
      default:
        throw new UnknownCommandException(
          "I'm sorry, but I don't know what that means :-(",
          inputs[0],
          "list", "mark", "unmark", "todo", "deadline", "event", "bye"
        );
    }
  }

  private void REPL() {
    Scanner sc = new Scanner(System.in);

    while (true) {
      String input = sc.nextLine();

      if (input.equals("bye")) {
        break;
      }

      String[] inputs = input.split(" ", 2);

      this.printHorizontalln();

      try {
        this.evaluateInputs(inputs);
      } catch (RuntimeException e) {
        System.out.printf("Something went wrong:\n%s", e.getMessage());
      }

      this.printHorizontalln();
    }

    sc.close();
  }

  public void run() {
    System.out.println(logo);

    this.greet();

    this.REPL();

    this.exitMessage();
  }
}
