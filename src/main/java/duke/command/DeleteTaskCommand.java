package duke.command;

import java.sql.SQLException;

import database.TaskOrm;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteTaskCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    private final int taskID;

    public DeleteTaskCommand(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public String execute() {
        TaskOrm tm = new TaskOrm();
        try {
            task.Task task = tm.delete(taskID);
            return "Noted. I've removed this task:\n"
                    + "  " + task + "\n"
                    + String.format("Now you have %d tasks in the list.\n", tm.count());
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public String explain() {
        return "Deletes the task with the specified index from the task list.\n"
                + "Format: delete <index>\n"
                + "Example: delete 3";
    }
}
