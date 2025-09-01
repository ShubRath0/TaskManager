import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a single task with a name, due date, and completion status
 */
public class TaskItem {

    private final String name;
    private final LocalDate dueDate;
    private boolean completed = false;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    /**
     * Creates a new task item with a name and due date
     * 
     * @param name    - The name or description of the task
     * @param dueDate - The due date of the task in MM-dd-yyyy format
     * @throws IllegalArgumentException if the due date is not MM-dd-yyyy format
     */
    public TaskItem(String name, String dueDate) throws IllegalArgumentException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Task name cannot be empty.");
        }
        this.name = name;

        try {
            this.dueDate = LocalDate.parse(dueDate, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Due date must be in MM-dd-yyyy format.");
        }
    }

    /**
     * Gets the name or description of the task
     * 
     * @return name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the due date of the task in MM-dd-yyyy format
     * 
     * @return the due date of the task
     */
    public String getDueDate() {
        return dueDate.format(FORMATTER);
    }

    /**
     * Checks if the task has been completed
     * 
     * @return true if the task is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Marks task as completed
     */
    public void complete() {
        completed = true;
    }

    /**
     * Returns the completion status of this task
     * 
     * @return "Complete" or "Incomplete"
     */
    public String getCompletionStatus() {
        return completed ? "Complete" : "Incomplete";
    }

    /**
     * Prints the task name, due date, and completion status
     */
    @Override
    public String toString() {
        return String.format("Task Name: %s | Due Date: %s | Completed: %s", name, getDueDate(), getCompletionStatus());
    }
}
