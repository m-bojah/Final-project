import javax.swing.*;
import javax.swing.table.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class TaskManagerGUI extends JFrame {
    private final CardLayout  cardLayout;
    private final JPanel mainPanel;

    // Login panel components
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;

    // Register panel components
    private JTextField regNameField, regEmailField;
    private JPasswordField regPasswordField;

    // Task panel components
    private JTable taskTable;
    private DefaultTableModel taskTableModel;
    private JTextField taskDescriptionField;
    private JDateChooser deadlineChooser;
    private JComboBox<String> statusComboBox;

    private User currentUser;
    private final UserDAO userDAO = new UserDAO();
    private final TaskDAO taskDAO = new TaskDAO();

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public TaskManagerGUI() {
        super("Task Manager");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initLoginPanel();
        initRegisterPanel();
        initTaskPanel();

        add(mainPanel);

        cardLayout.show(mainPanel, "login");
    }

    private void initLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints c = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loginEmailField = new JTextField(25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loginPasswordField = new JPasswordField(25);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        JButton goToRegisterButton = new JButton("Register");
        goToRegisterButton.setBackground(new Color(40, 167, 69));
        goToRegisterButton.setForeground(Color.WHITE);
        goToRegisterButton.setFocusPainted(false);

        c.insets = new Insets(15, 15, 15, 15);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2; loginPanel.add(titleLabel, c);

        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1; loginPanel.add(emailLabel, c);
        c.gridx = 1; c.gridy = 1; loginPanel.add(loginEmailField, c);

        c.gridx = 0; c.gridy = 2; loginPanel.add(passLabel, c);
        c.gridx = 1; c.gridy = 2; loginPanel.add(loginPasswordField, c);

        c.gridx = 0; c.gridy = 3; loginPanel.add(loginButton, c);
        c.gridx = 1; c.gridy = 3; loginPanel.add(goToRegisterButton, c);

        mainPanel.add(loginPanel, "login");

        loginButton.addActionListener(e -> doLogin());
        goToRegisterButton.addActionListener(e -> {
            clearRegisterFields();
            cardLayout.show(mainPanel, "register");
        });
    }

    private void initRegisterPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints c = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        regNameField = new JTextField(25);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        regEmailField = new JTextField(25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        regPasswordField = new JPasswordField(25);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(40, 167, 69));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);

        JButton goToLoginButton = new JButton("Back to Login");
        goToLoginButton.setBackground(new Color(0, 123, 255));
        goToLoginButton.setForeground(Color.WHITE);
        goToLoginButton.setFocusPainted(false);

        c.insets = new Insets(15, 15, 15, 15);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2; registerPanel.add(titleLabel, c);

        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1; registerPanel.add(nameLabel, c);
        c.gridx = 1; c.gridy = 1; registerPanel.add(regNameField, c);

        c.gridx = 0; c.gridy = 2; registerPanel.add(emailLabel, c);
        c.gridx = 1; c.gridy = 2; registerPanel.add(regEmailField, c);

        c.gridx = 0; c.gridy = 3; registerPanel.add(passLabel, c);
        c.gridx = 1; c.gridy = 3; registerPanel.add(regPasswordField, c);

        c.gridx = 0; c.gridy = 4; registerPanel.add(registerButton, c);
        c.gridx = 1; c.gridy = 4; registerPanel.add(goToLoginButton, c);

        mainPanel.add(registerPanel, "register");

        registerButton.addActionListener(e -> doRegister());
        goToLoginButton.addActionListener(e -> {
            clearLoginFields();
            cardLayout.show(mainPanel, "login");
        });
    }

    private void initTaskPanel() {
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBackground(Color.WHITE);

        // Top panel for logout
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.WHITE);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        topPanel.add(logoutButton);
        taskPanel.add(topPanel, BorderLayout.NORTH);

        // Center - task table
        taskTableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Deadline", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        taskTable = new JTable(taskTableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                String status = (String) getModel().getValueAt(row, 3);
                String deadlineStr = (String) getModel().getValueAt(row, 2);
                Date deadline = null;
                try { deadline = sdf.parse(deadlineStr); } catch (Exception ignored) {}

                if ("Completed".equals(status)) {
                    c.setBackground(new Color(198, 239, 206)); // greenish
                } else if (deadline != null && deadline.before(stripTime(new Date()))) {
                    c.setBackground(new Color(255, 199, 206)); // reddish overdue
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        taskPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for task controls
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();

        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taskDescriptionField = new JTextField(15);

        JLabel deadlineLabel = new JLabel("Deadline:");
        deadlineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        deadlineChooser = new JDateChooser();
        deadlineChooser.setDateFormatString("yyyy-MM-dd");
        deadlineChooser.setMinSelectableDate(new Date());

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
        statusComboBox.setEnabled(false); // status can be changed only via Change Status button

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setBackground(new Color(0, 123, 255));
        addTaskButton.setForeground(Color.WHITE);
        addTaskButton.setFocusPainted(false);

        JButton changeStatusButton = new JButton("Change Status");
        changeStatusButton.setBackground(new Color(23, 162, 184));
        changeStatusButton.setForeground(Color.WHITE);
        changeStatusButton.setFocusPainted(false);

        JButton deleteTaskButton = new JButton("Delete Completed Task");
        deleteTaskButton.setBackground(new Color(220, 53, 69));
        deleteTaskButton.setForeground(Color.WHITE);
        deleteTaskButton.setFocusPainted(false);

        c.insets = new Insets(8, 8, 8, 8);
        c.gridx = 0; c.gridy = 0; bottomPanel.add(descLabel, c);
        c.gridx = 1; c.gridy = 0; bottomPanel.add(taskDescriptionField, c);

        c.gridx = 2; c.gridy = 0; bottomPanel.add(deadlineLabel, c);
        c.gridx = 3; c.gridy = 0; bottomPanel.add(deadlineChooser, c);

        c.gridx = 4; c.gridy = 0; bottomPanel.add(statusLabel, c);
        c.gridx = 5; c.gridy = 0; bottomPanel.add(statusComboBox, c);

        c.gridx = 6; c.gridy = 0; bottomPanel.add(addTaskButton, c);

        c.gridx = 0; c.gridy = 1; bottomPanel.add(changeStatusButton, c);
        c.gridx = 1; c.gridy = 1; bottomPanel.add(deleteTaskButton, c);

        taskPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(taskPanel, "tasks");

        addTaskButton.addActionListener(e -> addTask());
        changeStatusButton.addActionListener(e -> changeStatus());
        deleteTaskButton.addActionListener(e -> deleteTask());
        logoutButton.addActionListener(e -> logout());
    }

    private void doLogin() {
        String email = loginEmailField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please fill all fields");
            return;
        }

        User user = userDAO.login(email, password);
        if (user != null) {
            currentUser = user;
            loadTasks();
            clearTaskInputFields();
            cardLayout.show(mainPanel, "tasks");
        } else {
            showError("Invalid email or password");
        }
    }

    private void doRegister() {
        String name = regNameField.getText().trim();
        String email = regEmailField.getText().trim();
        String password = new String(regPasswordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showError("Please fill all fields");
            return;
        }

        if (!isValidEmail(email)) {
            showError("Invalid email format");
            return;
        }

        boolean success = userDAO.register(new User(name, email, password));
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful! You can now login.");
            clearRegisterFields();
            cardLayout.show(mainPanel, "login");
        } else {
            showError("Registration failed! Email might already be used.");
        }
    }

    private void loadTasks() {
        List<Task> tasks = taskDAO.getTasksByUser(currentUser.getId());
        taskTableModel.setRowCount(0);
        for (Task t : tasks) {
            taskTableModel.addRow(new Object[]{
                    t.getId(),
                    t.getDescription(),
                    sdf.format(t.getDeadline()),
                    t.getStatus()
            });
        }
    }

    private void addTask() {
        String desc = taskDescriptionField.getText().trim();
        Date deadline = deadlineChooser.getDate();

        if (desc.isEmpty()) {
            showError("Description cannot be empty");
            return;
        }
        if (deadline == null) {
            showError("Please select a deadline");
            return;
        }
        if (deadline.before(stripTime(new Date()))) {
            showError("Deadline cannot be in the past");
            return;
        }

        Task task = new Task(currentUser.getId(), desc, deadline, "Pending");
        if (taskDAO.addTask(task)) {
            loadTasks();
            clearTaskInputFields();
        } else {
            showError("Failed to add task");
        }
    }

    private void changeStatus() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Select a task to change status");
            return;
        }
        int taskId = (int) taskTableModel.getValueAt(selectedRow, 0);
        String currentStatus = (String) taskTableModel.getValueAt(selectedRow, 3);

        String[] options = {"Pending", "In Progress", "Completed"};
        String newStatus = (String) JOptionPane.showInputDialog(this, "Select new status:", "Change Status",
                JOptionPane.PLAIN_MESSAGE, null, options, currentStatus);

        if (newStatus == null || newStatus.equals(currentStatus)) {
            return; // canceled or no change
        }

        if (currentStatus.equals("In Progress") && newStatus.equals("Pending")) {
            showError("Cannot move from In Progress back to Pending");
            return;
        }
        if (currentStatus.equals("Completed")) {
            showError("Cannot change status of Completed tasks");
            return;
        }

        if (taskDAO.updateTaskStatus(taskId, newStatus)) {
            loadTasks();
        } else {
            showError("Failed to update status");
        }
    }

    private void deleteTask() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Select a task to delete");
            return;
        }
        int taskId = (int) taskTableModel.getValueAt(selectedRow, 0);
        String status = (String) taskTableModel.getValueAt(selectedRow, 3);

        if (!"Completed".equals(status)) {
            showError("Only Completed tasks can be deleted");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this completed task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (taskDAO.deleteTask(taskId)) {
                loadTasks();
            } else {
                showError("Failed to delete task");
            }
        }
    }

    private void logout() {
        currentUser = null;
        clearLoginFields();
        cardLayout.show(mainPanel, "login");
    }

    private void clearLoginFields() {
        loginEmailField.setText("");
        loginPasswordField.setText("");
    }

    private void clearRegisterFields() {
        regNameField.setText("");
        regEmailField.setText("");
        regPasswordField.setText("");
    }

    private void clearTaskInputFields() {
        taskDescriptionField.setText("");
        deadlineChooser.setDate(null);
        statusComboBox.setSelectedIndex(0);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.\\w+$";
        return Pattern.matches(emailRegex, email);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private Date stripTime(Date date) {
        try {
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return date;
        }
    }

    public static void main(String[] args) {
        // Ensure JDateChooser is available on classpath: toedter-calendar-x.x.x.jar
        SwingUtilities.invokeLater(() -> {
            TaskManagerGUI gui = new TaskManagerGUI();
            gui.setVisible(true);
        });
    }
}