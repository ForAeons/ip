package task;

import static org.junit.jupiter.api.Assertions.*;

import duke.Parser;
import org.junit.jupiter.api.Test;

public class TaskTest {
  @Test
  public void testTaskToString() {
    Task task = new Task(1, "test task");
    assertEquals("[ ] test task",task.toString());

    Task task2 = new Task(1,"test task", true);
    assertEquals("[X] test task", task2.toString());
  }

  @Test
  public void testTaskGetDetails() {
    Task task = new Task(1,"test task");
    assertEquals("test task", task.getDetails());
  }

  @Test void testTaskToggleDone() {
    Task task = new Task(1,"test task");
    task.toggleDone();
    assertEquals("[X] test task", task.toString());
    task.toggleDone();
    assertEquals("[ ] test task", task.toString());
  }

  @Test
  public void testTodoToString() {
    Todo todo = new Todo(1,"test todo");
    assertEquals("[T][ ] test todo", todo.toString());

    Todo todo2 = new Todo(1,"test todo", true);
    assertEquals("[T][X] test todo", todo2.toString());
  }

  @Test
  public void testDeadlineToString() {
    Deadline deadline = new Deadline(1,"test deadline", Parser.parseDate("2020-08-23"));
    assertEquals("[D][ ] test deadline (by: 2020-08-23)", deadline.toString());

    Deadline deadline2 = new Deadline(1,"test deadline", Parser.parseDate("2020-08-23"), true);
    assertEquals("[D][X] test deadline (by: 2020-08-23)", deadline2.toString());
  }

  @Test
  public void testEventToString() {
    Event event = new Event(1,"test event", Parser.parseDate("2020-08-23"), Parser.parseDate("2020-08-25"));
      assertEquals("[E][ ] test event (from: 2020-08-23 to: 2020-08-25)", event.toString());

    Event event2 = new Event(1,"test event", Parser.parseDate("2020-08-23"), Parser.parseDate("2020-08-25"), true);
    assertEquals("[E][X] test event (from: 2020-08-23 to: 2020-08-25)", event2.toString());
  }
}
