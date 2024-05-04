
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoListApplication extends JFrame {

    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private JButton editButton;

    public ToDoListApplication() {
        super("To-Do List Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Initialize list model and list
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setCellRenderer(new TaskListRenderer());

        // Register a ListSelectionListener to enable/disable the "Edit Task" button when the selection changes
        taskList.addListSelectionListener(e -> editButton.setEnabled(taskList.getSelectedIndex() != -1));

        JScrollPane scrollPane = new JScrollPane(taskList);

        JPanel controlPanel = new JPanel(new BorderLayout());
        JTextField taskInput = new JTextField();
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(e -> {
            String description = taskInput.getText().trim();
            if (!description.isEmpty()) {
                // Prompt user for priority when adding a task
                String priorityInput = JOptionPane.showInputDialog(this, "Enter priority for task:", "Priority", JOptionPane.QUESTION_MESSAGE);
                int priority = 0;
                try {
                    priority = Integer.parseInt(priorityInput);
                } catch (NumberFormatException ex) {
                    // Handle invalid input or cancelation
                    return;
                }
                listModel.addElement(new Task(description, priority));
                taskInput.setText("");
                saveTasks();
            }
        });
        controlPanel.add(taskInput, BorderLayout.CENTER);
        controlPanel.add(addButton, BorderLayout.EAST);

        JButton completeButton = new JButton("Mark Completed");
        completeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                Task selectedTask = taskList.getSelectedValue();
                if (selectedTask != null) {
                    selectedTask.setCompleted(true);
                    listModel.setElementAt(selectedTask, selectedIndex);
                    saveTasks();
                }
            }
        });

        JButton clearButton = new JButton("Clear Completed");
        clearButton.addActionListener(e -> {
            List<Task> completedTasks = new ArrayList<>();
            for (int i = 0; i < listModel.size(); i++) {
                Task task = listModel.getElementAt(i);
                if (task != null && task.isCompleted()) {
                    completedTasks.add(task);
                }
            }
            for (Task task : completedTasks) {
                listModel.removeElement(task);
            }
            saveTasks();
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveTasks());

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadTasks());

        // Edit Button
        editButton = new JButton("Edit Task");
        editButton.setEnabled(false);
        editButton.addActionListener(e -> {
            Task selectedTask = taskList.getSelectedValue();
            if (selectedTask != null) {
                // Prompt user to edit task description and priority
                String newDescriptionInput = JOptionPane.showInputDialog(this, "Enter new description for task:", "Edit Task", JOptionPane.QUESTION_MESSAGE);
                String newPriorityInput = JOptionPane.showInputDialog(this, "Enter new priority for task:", "Edit Priority", JOptionPane.QUESTION_MESSAGE);
                
                // Validate input and update task
                if (newDescriptionInput != null && newPriorityInput != null) {
                    selectedTask.setDescription(newDescriptionInput);
                    try {
                        int newPriority = Integer.parseInt(newPriorityInput);
                        selectedTask.setPriority(newPriority);
                        listModel.setElementAt(selectedTask, taskList.getSelectedIndex());
                        saveTasks();
                    } catch (NumberFormatException ex) {
                        // Handle invalid priority input
                        JOptionPane.showMessageDialog(this, "Invalid priority input!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Add buttons to the control panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(completeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(editButton);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.NORTH);
    }

    // Method to load tasks from file
    private void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\dolist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String description = parts[0];
                    boolean completed = Boolean.parseBoolean(parts[1]);
                    int priority = Integer.parseInt(parts[2]);
                    listModel.addElement(new Task(description, priority));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to save tasks to file
    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\dolist.txt"))) {
            for (int i = 0; i < listModel.size(); i++) {
                Task task = listModel.getElementAt(i);
                writer.write(task.getDescription() + "," + task.isCompleted() + "," + task.getPriority());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Task class representing a single task
    private static class Task implements Serializable {
        private String description;
        private boolean completed;
        private int priority;

        public Task(String description, int priority) {
            this.description = description;
            this.priority = priority;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return description + " [Priority: " + priority + "]" + (completed ? " [Completed]" : "");
        }
    }

    // Custom cell renderer for the task list
    private class TaskListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Task task = (Task) value;
            if (task != null) {
                label.setForeground(task.isCompleted() ? Color.GRAY : Color.BLACK);
            }
            return label;
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoListApplication().setVisible(true));
    }
}
