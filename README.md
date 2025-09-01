# Task Manager App

A simple console-based Task Manager in Java with REST API support.  
This application allows users to create, view, complete, and remove tasks with due dates, and now stores tasks persistently using SQLite.

## Features

- Add tasks with a name and due date.
- Prevent duplicate task names.
- View all tasks with their status (Complete/Incomplete).
- Mark tasks as completed.
- Remove tasks from the list.
- Input validation for numbers, dates, and task names.
- Persistent storage using SQLite (tasks.db) so tasks are saved between sessions.
- **REST API** support to manage tasks programmatically:
- `GET /tasks` — list all tasks (no body required)  
- `POST /tasks` — add a new task  
  **Request body (JSON):** Provide `name` and `dueDate`. You can optionally include `completed`. Example:
   ```json
   {
   "name": "Finish Homework",
   "dueDate": "09-02-2025",
   "completed": false
   }
   ```

  - `PUT /tasks` — Update an existing task by name  
   **Description:** Specify the name of the task and include any fields (dueDate or completed) you want to update. Example:
   ```json
   {
      "name": "Finish Homework",
      "dueDate": "09-02-2025",
      "completed": false
   }
   ```

- `DELETE /tasks` — remove a task
   **Request body (JSON):** Just provide the name of the task to delete. Example:
   ```json
   {
      "name": "Finish Homework"
   }
   ```

## Requirements

- Java 8 or higher
- Maven 3 or higher
- Terminal/Command Prompt to run the console version
- HTTP client (browser, Postman, curl, etc.) for API interaction

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/ShubRath0/TaskManager.git
   ```

2. Build the project with Maven

   ```bash
   mvn clean compile
   ```

3. Run the console application (this also starts the REST API server):

   ```bash
   mvn exec:java "-Dexec.mainClass=com.example.App"
   ```

4. Access the API at http://localhost:4567 and use endpoints to manage tasks

## Why this project is cool

- Uses SQLite for persistent storage
- Implements a clean separation of concerns (TaskItem, TaskRepository, TaskManager)
- Logging with Logger class
- Now supports programmatic task management via REST API
