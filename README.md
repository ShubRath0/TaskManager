# Task Manager App

A simple console-based Task Manager in Java.  
This application allows users to create, view, complete, and remove tasks with due dates, and now stores tasks persistently using SQLite.

## Features

- Add tasks with a name and due date.
- Prevent duplicate task names.
- View all tasks with their status (Complete/Incomplete).
- Mark tasks as completed.
- Remove tasks from the list.
- Input validation for numbers, dates, and task names.
- Persistent storage using SQLite (tasks.db) so tasks are saved between sessions.

## Requirements

- Java 8 or higher
- Terminal/Command Prompt to run the application

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/ShubRath0/Task-Manager-App.git
   ```

2. The required SQLite JDBC library (sqlite-jdbc.jar) is already included in the /lib folder, so you don’t need to download anything extra.

3. Compile the project:

   ```bash
   javac -cp "lib/*" -d bin src/*.java
   ```

4. Run the application:
   ```bash
   java -cp "bin;lib/*" App
   ```

## Why this project is cool

- Uses SQLite for persistent storage
- Implements a clean separation of concerns (TaskItem, TaskRepository, TaskManager)
- Logging with Logger class