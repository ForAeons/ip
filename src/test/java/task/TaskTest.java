package task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TaskTest {
  @Test
  public void testTaskToString() {
    Task task = new Task("test task");
    assertEquals("[ ] test task",task.toString());

    Task task2 = new Task("test task", true);
    assertEquals("[X] test task", task2.toString());
  }

  @Test
  public void testTaskGetDetails() {
    Task task = new Task("test task");
    assertEquals("test task", task.getDetails());
  }

  @Test void testTaskToggleDone() {
    Task task = new Task("test task");
    task.toggleDone();
    assertEquals("[X] test task", task.toString());
    task.toggleDone();
    assertEquals("[ ] test task", task.toString());
  }

  @Test
  public void testTodoToString() {
    Todo todo = new Todo("test todo");
    assertEquals("[T][ ] test todo", todo.toString());

    Todo todo2 = new Todo("test todo", true);
    assertEquals("[T][X] test todo", todo2.toString());
  }

  @Test
  public void testDeadlineToString() {
    Deadline deadline = new Deadline("test deadline", "Aug 23 2020");
    assertEquals("[D][ ] test deadline (by: Aug 23 2020)", deadline.toString());

    Deadline deadline2 = new Deadline("test deadline", "Aug 23 2020", true);
    assertEquals("[D][X] test deadline (by: Aug 23 2020)", deadline2.toString());
  }

  @Test
  public void testEventToString() {
    Event event = new Event("test event", "Aug 23 2020", "Aug 24 2020");
    assertEquals("[E][ ] test event (from: Aug 23 2020 to: Aug 24 2020)", event.toString());

    Event event2 = new Event("test event", "Aug 23 2020", "Aug 24 2020", true);
    assertEquals("[E][X] test event (from: Aug 23 2020 to: Aug 24 2020)", event2.toString());
  }
}
