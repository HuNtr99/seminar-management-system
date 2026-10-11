public class Student extends User {
    private String researchTitle;
    private String researchAbstract;
    private String supervisorName;
    private String presentationType; // "Oral" or "Poster"
    private String presentationFilePath; // Path to the uploaded file

    // Constructor including User details
    public Student(String id, String password, String name, String email) {
        super(id, password, name, email, "Student");
        // Initialize other fields as empty or null initially
        this.researchTitle = "N/A";
        this.researchAbstract = "N/A";
        this.supervisorName = "N/A";
        this.presentationType = "N/A";
    }

    // Method to register seminar details
    public void registerSeminar(String title, String abs, String supervisor, String type) {
        this.researchTitle = title;
        this.researchAbstract = abs;
        this.supervisorName = supervisor;
        this.presentationType = type;
    }

    // Method to simulate file upload
    public void uploadPresentation(String filePath) {
        this.presentationFilePath = filePath;
    }

    // Getters
    public String getResearchTitle() {
        return researchTitle;
    }

    public String getResearchAbstract() {
        return researchAbstract;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public String getPresentationType() {
        return presentationType;
    }

    public String getPresentationFilePath() {
        return presentationFilePath;
    }
}