package DSA_Individual;

import javax.swing.*;
import java.awt.*;

class Task {
    private String title;
    private String description;
    private boolean completed;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        this.completed = true;
    }
}

class Node {
    Task task;
    Node next;

    Node(Task task) {
        this.task = task;
        this.next = null;
    }
}

class ToDoList {
    private Node head;

    public ToDoList() {
        this.head = null;
    }

    public void addToDo(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void markToDoAsCompleted(String title) {
        Node current = head;
        while (current != null) {
            if (current.task.getTitle().equalsIgnoreCase(title)) {
                current.task.markCompleted();
                return;
            }
            current = current.next;
        }
        System.out.println("Task not found: " + title);
    }

    public void viewToDoList() {
        Node current = head;
        int taskCount = 1;
        while (current != null) {
            System.out.println(taskCount + ". " + current.task.getTitle() + " - " + (current.task.isCompleted() ? "Completed" : "Incomplete"));
            current = current.next;
            taskCount++;
        }
    }
}

public class ToDoListApp extends JFrame {
    private ToDoList toDoList;
    private JTextField titleField, descriptionField;
    private JList<String> taskList;
    private DefaultListModel<String> listModel;

    public ToDoListApp() {
        setTitle("To-Do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        
        setLocationRelativeTo(null);

        toDoList = new ToDoList();

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();

        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(e -> addTask());

        JButton completeButton = new JButton("Mark as Completed");
        completeButton.addActionListener(e -> markTaskAsCompleted());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(completeButton);

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(taskList);

        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void addTask() {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();

        if (!title.isEmpty() && !description.isEmpty()) {
            Task task = new Task(title, description);
            toDoList.addToDo(task);
            listModel.addElement(task.getTitle());
            titleField.setText("");
            descriptionField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a title and description.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markTaskAsCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedTitle = listModel.get(selectedIndex);
            toDoList.markToDoAsCompleted(selectedTitle);
            listModel.set(selectedIndex, selectedTitle + " (Completed)");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as completed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoListApp().setVisible(true));
    }
}