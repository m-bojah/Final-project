# Task Manager Desktop App

This is a simple desktop application for managing tasks, built using Java Swing and MySQL. It allows users to register, log in, and manage tasks with features like setting deadlines using a calendar, changing task statuses, and deleting tasks.

## Features

- User registration and login with email validation
- Add tasks with a title, description, deadline, and status
- Choose deadline using a calendar (past dates are disabled)
- Change task status (Pending, In Progress, Completed)
- Delete any task
- Visual indicators for overdue and completed tasks
- Professional-looking GUI
- Secure database integration using JDBC

## How It Works

1. User starts the app and logs in or registers
2. After login, the task manager panel opens
3. User can add a new task using the form and calendar
4. Status of each task can be changed using a button
5. Completed tasks can be deleted
6. User can log out any time

## Technologies Used

- Java (Swing for GUI)
- MySQL (Database)
- JDBC (Database connection)
- JCalendar (Date picker component)

## How to Run

1. Clone the repository
2. Create a MySQL database named `task_manager`
3. Run the `schema.sql` to create the required tables
4. Update `DBConnection.java` with your MySQL username and password
5. Compile and run `TaskManagerGUI.java`

## File Structure

- `DBConnection.java` – Handles database connection
- `User.java` – Represents a user
- `UserDAO.java` – User-related DB operations
- `Task.java` – Represents a task
- `TaskDAO.java` – Task-related DB operations
- `TaskManagerGUI.java` – The main GUI and logic

## Notes

- Email validation is included during registration
- Users can only see and manage their own tasks
- Deadline picker disables past dates
- The task table is view-only for safety

## Author

Created as a Java final project for university. Aimed to apply object-oriented design and real database integration in a simple but functional desktop application.
