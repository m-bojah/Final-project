import java.util.Date;

public class Task {
    private final int id;
    private final int userId;
    private final String description;
    private final Date deadline;
    private final String status;

    public Task(int id, int userId, String description, Date deadline, String status) {
        this.id = id; this.userId = userId; this.description = description; this.deadline = deadline; this.status = status;
    }

    public Task(int userId, String description, Date deadline, String status) {
        this(-1, userId, description, deadline, status);
    }

    // Getters/setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getDescription() { return description; }
    public Date getDeadline() { return deadline; }
    public String getStatus() { return status; }

}
