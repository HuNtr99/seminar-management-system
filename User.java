public class User {
    protected String id;
    protected String password;
    protected String name;
    protected String email;
    protected String role; // "Student", "Evaluator", or "Coordinator"

    public User(String id, String password, String name, String email, String role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public boolean login(String inputId, String inputPassword) {
        return this.id.equals(inputId) && this.password.equals(inputPassword);
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    
    @Override
    public String toString() {
        return id + "," + password + "," + name + "," + email + "," + role;
    }
}