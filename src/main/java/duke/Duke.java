package duke;

import java.sql.SQLException;
import java.util.Scanner;

import duke.Command.Command;
import duke.Command.Parser;
import exceptions.BadInputException;
import exceptions.BadTaskInputException;
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

  private void addTask(String command, String arguments) throws BadInputException {
    try {
      Task task = this.taskManager.addTask(command, arguments);
      this.printIndentedln("Got it. I've added this task:");
      this.printIndentedln("  " + task);
      this.printIndentedln(String.format("Now you have %d tasks in the list.", taskManager.getNumberOfTasks()));
    } catch (SQLException | BadTaskInputException e) {
      System.out.println(e.getMessage());
    }
  }

  private  void deleteTask(int taskID) {
    try {
      Task task = this.taskManager.deleteTask(taskID);
      this.printIndentedln("Noted. I've removed this task:");
      this.printIndentedln("  " + task);
      this.printIndentedln(String.format("Now you have %d tasks in the list.", taskManager.getNumberOfTasks()));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  private void listTasks() {
    if (this.taskManager.getNumberOfTasks() == 0) {
      this.printIndentedln("No tasks added yet!");
      return;
    }

    try {
      for (Task task : this.taskManager.getTasks()) {
        this.printIndentedln(String.format("%d. %s", task.taskID, task));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  private void markTaskAsDone(int taskID) {
    try {
      this.taskManager.markTaskAsDone(taskID);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return;
    }

    try {
      Task task = this.taskManager.getTask(taskID);
      this.printIndentedln("Nice! I've marked this task as done:");
      this.printIndentedln("  " + task);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  private  void unmarkTaskAsDone(int taskID) {
    try {
      this.taskManager.unmarkTaskAsDone(taskID);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return;
    }

    try {
      Task task = this.taskManager.getTask(taskID);
      this.printIndentedln("Ok, I've marked this task as not done yet:");
      this.printIndentedln("  " + task);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  private void evaluateInput(String input) throws RuntimeException {
    Parser parser = new Parser(input);

    switch (parser.command) {
      case "list":
        this.listTasks();
        break;
      case "mark":
        this.markTaskAsDone(parser.parseTaskID());
        break;
      case "unmark":
        this.unmarkTaskAsDone(parser.parseTaskID());
        break;
      case "delete":
        this.deleteTask(parser.parseTaskID());
        break;
      case TaskManager.todo:
      case TaskManager.deadline:
      case TaskManager.event:
        this.addTask(parser.command, parser.arguments);
        break;
      default:
        throw new UnknownCommandException(
          "I'm sorry, but I don't know what that means :-(",
          parser.command,
          "list", "mark", "unmark", "todo", "deadline", "event", "bye"
        );
    }
  }

  private void REPL() {
    Scanner sc = new Scanner(System.in);

    while (true) {
      String input = sc.nextLine();

      Command c = Command.Interpret(input);

      if (c.terminate()) {
        break;
      }

      String output = c.execute();
      view.PrettyPrinter.print(output);
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
