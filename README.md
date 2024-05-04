# ToDoListApplication

## Overview
ToDoListApplication is a simple Java Swing application that allows users to manage their to-do tasks. It provides basic functionalities such as adding tasks, marking tasks as completed, clearing completed tasks, saving tasks to a file, and loading tasks from a file.

## Features
- **Add Task:** Users can add new tasks by entering a description and priority.
- **Mark Completed:** Users can mark tasks as completed by selecting them from the list and clicking the "Mark Completed" button.
- **Clear Completed:** Users can clear completed tasks by clicking the "Clear Completed" button.
- **Edit Task:** Users can edit existing tasks by selecting them from the list and clicking the "Edit Task" button. They can update the task description and priority.
- **Save and Load:** Users can save their tasks to a file and load them back into the application.

## Usage
1. **Add Task:** Enter a task description and priority, then click the "Add Task" button.
2. **Mark Completed:** Select a task from the list and click the "Mark Completed" button.
3. **Clear Completed:** Click the "Clear Completed" button to remove all completed tasks from the list.
4. **Edit Task:** Select a task from the list and click the "Edit Task" button. Enter the new description and priority, then click "OK" to save the changes.
5. **Save:** Click the "Save" button to save the current list of tasks to a file.
6. **Load:** Click the "Load" button to load tasks from a file into the application.

## File Format
Tasks are saved to and loaded from a text file in the following format:
task_description,completed,priority


- `task_description`: Description of the task.
- `completed`: Boolean value indicating whether the task is completed (true/false).
- `priority`: Priority of the task.

## Requirements
- Java Development Kit (JDK) 8 or higher
- Apache Maven (for building the project)

## Build and Run
To build the project, run the following Maven command in the project directory:
mvn package
To run the application, use the following command:
java -jar target/ToDoListApplication.jar

## Author
Sushma Dasari
