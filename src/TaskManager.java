import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Holds and manages all task items
 */
public class TaskManager {

    private List<TaskItem> taskList = new ArrayList<>();

    /**
     * Adds a new task to TaskManager
     * 
     * @param task the TaskItem to add
     * @return true if successfully added
     */
    public boolean addTask(TaskItem task) {
        validateTask(task);

        if (checkName(task.getName())) {
            return false;
        } else {
            return taskList.add(task);
        }
    }

    /**
     * Checks if a name of a task already exists in the task list
     * 
     * @param name name of the task
     * @return true if a match is found
     */
    public boolean checkName(String name) {
        for (TaskItem t : taskList) {
            if (t.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a task from TaskManager
     * 
     * @param task the TaskItem to remove
     * @return true if successfully removed
     */
    public boolean removeTask(TaskItem task) {
        validateTask(task);
        return taskList.remove(task);
    }

    /**
     * Marks a task as completed
     * 
     * @param task the TaskItem to mark as completed
     * @return true if task successfully marked as completed, false if it was
     *         already completed or not found
     */
    public boolean completeTask(TaskItem task) {
        validateTask(task);
        if (!taskList.contains(task) || task.isCompleted()) {
            return false;
        }
        task.complete();
        return true;
    }

    /**
     * Validates that a task isnt null
     * 
     * @param task the TaskItem to validate
     */
    private void validateTask(TaskItem task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
    }

    /**
     * Returns an unmodifiable list of all tasks managed by this TaskManager.
     * 
     * @return an unmodifiable list of TaskItem objects
     */
    public List<TaskItem> getTaskList() {
        return Collections.unmodifiableList(taskList);
    }
}
