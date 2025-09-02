package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskRepository is a helper class for interacting with the tasks database
 * 
 * Provides methods to:
 * -Add new task
 * -Remove tasks by ID
 * -Update task completion status
 * -Delete all tasks
 * -Retrieve all tasks
 * 
 * This class uses SQLite via JDBC and handles connections automatically
 */
public class TaskRepository {
    private static String databaseUrl = "jdbc:sqlite:tasks.db";

    private TaskRepository() {
    }

    private static void ensureTableExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "dueDate TEXT," +
                "completed INTEGER DEFAULT 0" +
                ")";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            Logger.info("Table 'tasks' ensured in database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setDatabase(String url) {
        databaseUrl = url;
        ensureTableExists();
    }

    /**
     * Adds a task to the 'tasks' database
     * 
     * @param task The TaskItem to add
     * @return number of rows added
     */
    public static int addTask(TaskItem task) {
        String SQL = "INSERT INTO tasks(name, dueDate, completed) VALUES(?, ?, ?)";
        int rows = executeUpdate(SQL, task.getName(), task.getDueDate(), task.isCompleted());
        Logger.info(rows + " task(s) added.");
        return rows;
    }

    /**
     * Removes a task from the 'tasks' database
     * 
     * @param task The TaskItem to remove
     * @return number of rows removed
     */
    public static int removeTask(TaskItem task) {
        String SQL = "DELETE FROM tasks WHERE name = ?";
        int rows = executeUpdate(SQL, task.getName());
        Logger.info(rows + "task(s) removed from database.");
        return rows;
    }

    /**
     * Updates the completion status of a task in the 'tasks' database
     * 
     * @param task The TaskItem to update
     * @return number of rows updated
     */
    public static int updateTask(TaskItem task) {
        String SQL = "UPDATE tasks SET completed = ? WHERE name = ?";
        int rows = executeUpdate(SQL, task.isCompleted(), task.getName());
        Logger.info(rows + " task(s) updated from database.");
        return rows;
    }

    /**
     * Checks if a task exists in the database
     * 
     * @param task The TaskItem to check
     * @return true if it exists, else false
     */
    public static boolean containsTask(TaskItem task) {
        String SQL = "SELECT COUNT(*) FROM tasks WHERE name = ?";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, task.getName());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }

        } catch (SQLException e) {
            Logger.error("Failed to check if task exists: " + task.getName(), e);
        }

        return false;
    }

    /**
     * Deletes all tasks from the database
     * 
     * @return number of rows deleted
     */
    public static int deleteAllData() {
        String SQL = "DELETE FROM tasks";
        int rowsDeleted = executeUpdate(SQL);
        Logger.info(rowsDeleted + " task(s) deleted from database.");
        return rowsDeleted;
    }

    /**
     * Gets the connection to the 'tasks' database
     * 
     * @return the connection
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    /**
     * Executes an update statement (INSERT, UPDATE, DELETE)
     * 
     * @param sql    The SQL query with ? placeholders
     * @param params Values to bind to the placeholders
     * @return number of rows affected
     */
    private static int executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                if (param instanceof String) {
                    pstmt.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) param);
                } else if (param instanceof Boolean) {
                    pstmt.setInt(i + 1, (Boolean) param ? 1 : 0);
                }
            }

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            Logger.error("SQL execution failed: " + sql, e);
            return 0;
        }
    }

    /**
     * Retrieves all tasks from the database
     * 
     * @return a List of TaskItem objects; empty if none found
     */
    public static List<TaskItem> getAllTasks() {
        List<TaskItem> tasks = new ArrayList<>();
        String SQL = "SELECT * FROM tasks";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                TaskItem task = new TaskItem(
                        rs.getString("name"),
                        rs.getString("dueDate"),
                        rs.getInt("completed") == 1);
                tasks.add(task);
            }

        } catch (SQLException e) {
            Logger.error("Failed to retrieve tasks", e);
        }

        return tasks;
    }

    public static TaskItem getTaskByName(String name){
        String SQL = "SELECT * FROM tasks WHERE name = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, name);

            try(ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new TaskItem(
                        rs.getString("name"),
                        rs.getString("dueDate"),
                        rs.getInt("completed") == 1
                    );
                }
            } 

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
