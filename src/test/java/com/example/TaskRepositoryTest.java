package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class TaskRepositoryTest {

    // ------------------------
    // Temporary directory
    // ------------------------
    @TempDir
    Path TempDir;

    // ------------------------
    // Cleanup
    // ------------------------
    @BeforeEach
    void setup() {
        String dbPath = TempDir.resolve("test.db").toString();
        TaskRepository.setDatabase("jdbc:sqlite:" + dbPath);
        TaskRepository.deleteAllData();
    }

    // ------------------------
    // Helper functions
    // ------------------------
    private TaskItem testTask() {
        TaskItem task = new TaskItem("Test", "09-01-2025", false);
        return task;
    }

    // ------------------------
    // Normal tests
    // ------------------------

    @Test
    public void addTask_shouldIncreaseSizeByOne() {
        int sizeBeforeAdding = TaskRepository.getAllTasks().size();
        assertEquals(0, sizeBeforeAdding);

        TaskItem test = testTask();
        TaskRepository.addTask(test);

        int sizeAfterAdding = TaskRepository.getAllTasks().size();
        assertEquals(1, sizeAfterAdding);
    }

    @Test
    public void removeTask_shouldIncreaseThenDecreaseByOne() {
        int sizeBeforeAdding = TaskRepository.getAllTasks().size();
        assertEquals(0, sizeBeforeAdding);

        TaskItem test = testTask();

        int sizeAfterAdding = TaskRepository.addTask(test);
        assertEquals(1, sizeAfterAdding);

        TaskRepository.removeTask(test);
        int sizeAfterRemoving = TaskRepository.getAllTasks().size();
        assertEquals(0, sizeAfterRemoving);
    }

    @Test
    public void deleteAllData_SizeShouldBeZero() {
        TaskItem task1 = new TaskItem("Test1", "09-01-2025");
        TaskItem task2 = new TaskItem("Test2", "09-01-2025");
        TaskItem task3 = new TaskItem("Test3", "09-01-2025");

        TaskRepository.addTask(task1);
        TaskRepository.addTask(task2);
        TaskRepository.addTask(task3);

        assertEquals(3, TaskRepository.getAllTasks().size());

        TaskRepository.deleteAllData();

        assertEquals(0, TaskRepository.getAllTasks().size());
    }

    @Test
    public void checkIfContains_ShouldReturnTrue() {
        TaskItem task = testTask();
        TaskRepository.addTask(task);
        assertEquals(true, TaskRepository.containsTask(task));
    }

    @Test
    public void updateTask_ShouldBeCompleted() {
        TaskItem task = testTask();
        TaskRepository.addTask(task);

        assertEquals(false, task.isCompleted());

        task.complete();
        TaskRepository.updateTask(task);
        assertEquals(true, TaskRepository.getTaskByName(task.getName()).isCompleted());
    }

    // ------------------------
    // Edge case tests
    // ------------------------

    @Test
    public void addTask_NullName_ShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> new TaskItem(null, "09-01-2025"));
    }

    @Test
    public void addTask_EmptyName_ShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> new TaskItem("", "09-01-2025"));
    }

    @Test
    public void addTask_DuplicateTask_ShouldAllowOrReject() {
        TaskItem task = testTask();
        TaskRepository.addTask(task);
        int rowsAdded = TaskRepository.addTask(task); 
        assertTrue(rowsAdded >= 1);
        assertEquals(2, TaskRepository.getAllTasks().size());
    }

    @Test
    public void removeTask_NotExisting_ShouldReturnZero() {
        TaskItem task = testTask();
        int rowsRemoved = TaskRepository.removeTask(task);
        assertEquals(0, rowsRemoved);
    }

    @Test
    public void deleteAllData_EmptyDB_ShouldReturnZero() {
        int rowsDeleted = TaskRepository.deleteAllData();
        assertEquals(0, rowsDeleted);
        assertEquals(0, TaskRepository.getAllTasks().size());
    }

    @Test
    public void containsTask_NotExisting_ShouldReturnFalse() {
        TaskItem task = new TaskItem("NonExistent", "09-01-2025");
        assertFalse(TaskRepository.containsTask(task));
    }

    @Test
    public void containsTask_NullName_ShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> new TaskItem(null, "09-01-2025"));
    }

    @Test
    public void containsTask_EmptyName_ShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> new TaskItem("", "09-01-2025"));
    }

    @Test
    public void updateTask_NotExisting_ShouldReturnZero() {
        TaskItem task = testTask();
        int rowsUpdated = TaskRepository.updateTask(task);
        assertEquals(0, rowsUpdated);
    }

    @Test
    public void updateTask_NullName_ShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> new TaskItem(null, "09-01-2025"));
    }

    @Test
    public void updateTask_EmptyName_ShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> new TaskItem("", "09-01-2025"));
    }
}
