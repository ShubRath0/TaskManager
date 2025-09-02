package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    @BeforeEach
    void setup() {
        TaskRepository.setDatabase("jdbc:sqlite::memory:");
        TaskRepository.deleteAllData();
    }

    // ------------------------
    // Helper methods
    // ------------------------
    private TaskManager managerWithTask(TaskItem task) {
        TaskManager manager = new TaskManager();
        manager.addTask(task);
        return manager;
    }

    private TaskItem testTask() {
        return new TaskItem("Test", "09-01-2025");
    }

    // ------------------------
    // Normal tests
    // ------------------------
    @Test
    public void addTask_shouldIncreaseSizeByOne() {
        TaskManager manager = new TaskManager();
        TaskItem task = testTask();
        int initialSize = manager.getTaskList().size();

        manager.addTask(task);

        assertEquals(initialSize + 1, manager.getTaskList().size());
    }

    @Test
    public void addDuplicateTask_shouldNotIncreaseSize() {
        TaskItem task = testTask();
        TaskManager manager = managerWithTask(task);
        int sizeAfterFirstAdd = manager.getTaskList().size();

        manager.addTask(task);

        assertEquals(sizeAfterFirstAdd, manager.getTaskList().size());
    }

    @Test
    public void addAndRemoveTask_sizeRemainsSame() {
        TaskItem task = testTask();
        TaskManager manager = new TaskManager();

        manager.addTask(task);
        assertEquals(1, manager.getTaskList().size());

        manager.removeTask(task);
        assertEquals(0, manager.getTaskList().size());
    }

    @Test
    public void checkName_findSimilarNameInManager() {
        TaskItem task = testTask();
        TaskManager manager = managerWithTask(task);

        boolean found = manager.checkName(task.getName());
        assertTrue(found);
    }

    @Test
    public void completeTask_markTaskAsCompleted() {
        TaskItem task = testTask();
        TaskManager manager = managerWithTask(task);

        assertFalse(task.isCompleted());

        manager.completeTask(task);

        assertTrue(task.isCompleted());
    }

    // ------------------------
    // Edge-case tests
    // ------------------------
    @Test
    void addNullTask_shouldNotChangeSize() {
        TaskManager manager = new TaskManager();
        int initialSize = manager.getTaskList().size();

        assertThrows(IllegalArgumentException.class, () -> manager.addTask(null));
        assertEquals(initialSize, manager.getTaskList().size());
    }

    @Test
    void addTask_emptyName_shouldNotBeAllowed() {
        TaskManager manager = new TaskManager();

        assertThrows(IllegalArgumentException.class, () -> {
            TaskItem emptyNameTask = new TaskItem("", "09-01-2025");
            manager.addTask(emptyNameTask);
        });

        assertEquals(0, manager.getTaskList().size());
    }

    @Test
    void addTask_invalidDateFormat_shouldNotBeAllowed() {
        TaskManager manager = new TaskManager();

        assertThrows(IllegalArgumentException.class, () -> {
            TaskItem badDateTask = new TaskItem("InvalidDate", "2025-13-40");
            manager.addTask(badDateTask);
        });

        assertEquals(0, manager.getTaskList().size());
    }

    @Test
    void removeNonExistentTask_shouldNotChangeSize() {
        TaskManager manager = new TaskManager();
        TaskItem task = testTask();

        manager.removeTask(task); // Not added yet
        assertEquals(0, manager.getTaskList().size());
    }

    @Test
    void completeNonExistentTask_shouldNotThrow() {
        TaskManager manager = new TaskManager();
        TaskItem task = testTask();

        assertDoesNotThrow(() -> manager.completeTask(task));
        assertFalse(task.isCompleted());
    }
}
