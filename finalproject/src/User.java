public class User {
    private final int id;
    private final String name;
    private final String email;
    private final String password; // stored as hash ideally (for simplicity plaintext here)

    public User(int id, String name, String email, String password) {
        this.id = id; this.name = name; this.email = email; this.password = password;
    }

    public User(String name, String email, String password) {
        this(-1, name, email, password);
    }

    // Getters and setters
    public int getId() { return id; }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
