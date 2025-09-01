import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.List;

public class App {

    // UTILITY
    private static final Scanner scnr = new Scanner(System.in);
    private static final TaskManager manager = new TaskManager();

    // VARIABLES
    private static boolean running = true;

    // FINAL VARIABLES
    private static final int MIN_OPTION = 1;
    private static final int MAX_OPTION = 5;
    private static final char RETURN_KEY = 'r';
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    // entry point
    public static void main(String[] args) {

        while (running) {
            printMenu();
            int choice = readInt("Choose an option.");

            switch (choice) {
                case 1 -> addTask();
                case 2 -> removeTask();
                case 3 -> completeTask();
                case 4 -> viewTasks();
                case 5 -> endProgram();
                default -> System.out.println("Invalid choice.");
            }
        }

    }

    // Prints every choice
    private static void printMenu() {
        System.out.println("\nTask Manager Menu:");
        System.out.println("1. Add Task");
        System.out.println("2. Remove Task");
        System.out.println("3. Complete Task");
        System.out.println("4. View Tasks");
        System.out.println("5. Exit");
    }

    // Gets the input choice from user input
    private static int readInt(String prompt) {
        System.out.println(prompt);
        while (true) {
            try {
                int choice = scnr.nextInt();
                scnr.nextLine();
                if (choice >= MIN_OPTION && choice <= MAX_OPTION) {
                    return choice;
                } else {
                    System.out.println("Choice out of range. Please try again.");
                }
            } catch (Exception e) {
                scnr.nextLine();
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Prints out every task
    private static void viewTasks() {
        List<TaskItem> tasks = getTaskList();
        if (tasks.isEmpty()) {
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, tasks.get(i));
        }
    }

    // Checks that the user selection is in range of the task list
    private static boolean isInRange(List<TaskItem> tasks, int selection) {
        return selection >= 0 && selection < tasks.size();
    }

    // Gets a task from user input
    private static TaskItem getTask(List<TaskItem> tasks, String action) {
        while (true) {
            try {
                System.out.printf("Enter the number of the task to %s (or %c to return).%n", action, RETURN_KEY);
                viewTasks();

                String input = scnr.nextLine();
                if (input.equalsIgnoreCase(String.valueOf(RETURN_KEY)))
                    return null;

                int selection = Integer.parseInt(input) - 1;
                if (isInRange(tasks, selection)) {
                    return tasks.get(selection);
                } else {
                    System.out.println("Number out of range. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // Retrieves the task list; may be empty if no tasks exist
    public static List<TaskItem> getTaskList() {
        List<TaskItem> tasks = manager.getTaskList();
        if (tasks.isEmpty()) {
            System.out.println("No tasks were found. Please create a task.");
        }
        return tasks;
    }

    // Adds a task to TaskManager
    private static void addTask() {
        System.out.println("Enter a name/description for the task.");
        String name = "";

        while(name.isEmpty() || manager.checkName(name)) {
            name = scnr.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Task name cannot be empty!");
            }
            if (manager.checkName(name)) {
                System.out.println("A task with that name already exists!");
            }
        }

        LocalDate dueDate = null;
        while (dueDate == null) {
            try {
                System.out.println("Enter a due date in MM-dd-yyyy for the task.");
                String input = scnr.nextLine();
                dueDate = LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        TaskItem task = new TaskItem(name, dueDate.format(formatter));
        System.out.println(manager.addTask(task) ? "Task successfully added!" : "Task could not be added.");
    }

    // Removes a task from TaskManager
    private static void removeTask() {
        List<TaskItem> tasks = getTaskList();
        if (tasks.isEmpty())
            return;

        TaskItem task = getTask(tasks, "remove");
        if (task == null)
            return;

        System.out.println((manager.removeTask(task)) ? "Task removed successfully" : "Task could not be removed.");
    }

    // Mark a task as completed
    private static void completeTask() {
        List<TaskItem> tasks = getTaskList();
        if (tasks.isEmpty())
            return;

        TaskItem task = getTask(tasks, "complete");
        if (task == null)
            return;

        System.out
                .println((manager.completeTask(task)) ? "Task completed successfully" : "Task could not be completed.");
    }

    // End the program
    private static void endProgram() {
        System.out.println("Thank you for trying out my program! Goodbye!");
        scnr.close();
        running = false;
    }

}
