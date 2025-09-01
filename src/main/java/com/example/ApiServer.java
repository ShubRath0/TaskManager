package com.example;

//REST API
import static spark.Spark.*;
import com.google.gson.Gson;

// OTHER
import java.util.List;

/**
 * REST API server for Task Manager using Spark Java with exception handling.
 */
public class ApiServer {

    private static final Gson gson = new Gson();

    public ApiServer() {
        System.out.println("Starting REST API");
        port(4567);

        Logger.info("Starting Task Manager REST API");

        // Global exception handler
        exception(Exception.class, (e, req, res) -> {
            res.type("application/json");
            res.status(500);
            res.body(gson.toJson("Internal server error"));
        });

        // GET all tasks
        get("/tasks", (req, res) -> {
            res.type("application/json");
            try {
                List<TaskItem> tasks = TaskRepository.getAllTasks();
                res.status(200);
                return gson.toJson(tasks);
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("Error retrieving tasks: " + e.getMessage());
            }
        });

        // POST a new task
        post("/tasks", (req, res) -> {
            res.type("application/json");
            try {
                TaskItem task = gson.fromJson(req.body(), TaskItem.class);

                if (task.getName() == null || task.getName().isEmpty()) {
                    res.status(400);
                    return gson.toJson("Task name is required");
                }
                if (TaskRepository.containsTask(task)) {
                    res.status(400);
                    return gson.toJson("Task already exists");
                }

                Logger.info("POST Body: " + req.body());
                TaskRepository.addTask(task);
                res.status(201);
                return gson.toJson(task);
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("Error adding task: " + e.getMessage());
            }
        });

        // PUT update a task
        put("/tasks", (req, res) -> {
            res.type("application/json");
            try {
                TaskItem task = gson.fromJson(req.body(), TaskItem.class);

                if (!TaskRepository.containsTask(task)) {
                    res.status(404);
                    return gson.toJson("Task not found");
                }

                Logger.info("POST Body: " + req.body());
                TaskRepository.updateTask(task);
                res.status(200);
                return gson.toJson(task);
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("Error updating task: " + e.getMessage());
            }
        });

        // DELETE a task
        delete("/tasks", (req, res) -> {
            res.type("application/json");
            try {
                TaskItem task = gson.fromJson(req.body(), TaskItem.class);

                if (!TaskRepository.containsTask(task)) {
                    res.status(404);
                    return gson.toJson("Task not found");
                }

                Logger.info("POST Body: " + req.body());
                TaskRepository.removeTask(task);
                res.status(200);
                return gson.toJson(task);
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("Error deleting task: " + e.getMessage());
            }
        });

        // Health check
        get("/health", (req, res) -> {
            res.type("application/json");
            return gson.toJson("API is running");
        });
    }
}
