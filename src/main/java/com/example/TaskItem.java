package com.example;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a single task with a name, due date, and completion status
 */
public class TaskItem {

    private String name;
    private String dueDate;
    private boolean completed = false;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public TaskItem(){

    }

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
            LocalDate.parse(dueDate, FORMATTER);
            this.dueDate = dueDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Due date must be in MM-dd-yyyy format.");
        }
    }

        /**
     * Creates a new task item with an name, due date, and completion status already set
     * 
     * @param name       - The name or description of the task
     * @param dueDate    - The due date of the task in MM-dd-yyyy format
     * @param completion - The completion status of the task
     * @throws IllegalArgumentException if the due date is not MM-dd-yyyy format
     */
    public TaskItem(String name, String dueDate, boolean completion) throws IllegalArgumentException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Task name cannot be empty.");
        }
        this.name = name;
        this.completed = completion;

        try {
            LocalDate.parse(dueDate, FORMATTER);
            this.dueDate = dueDate;
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
        return dueDate;
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
    private String getCompletionStatus() {
        return completed ? "Y" : "N";
    }

    /**
     * Prints the task name, due date, and completion status
     */
    @Override
    public String toString() {
        return String.format("Task Name: %s | Due Date: %s | Completed: %s", name, getDueDate(), getCompletionStatus());
    }
}
