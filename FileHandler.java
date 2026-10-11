import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    private static final String FILE_NAME = "users.txt";

    // Method to save a new User to the file
    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            // Format: ID,Password,Name,Email,Role
            writer.write(user.getId() + "," + user.password + "," + user.getName() + "," + user.email + ","
                    + user.getRole());
            writer.newLine(); // Add a new line for the next record
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // Method to verify login credentials
    public static String verifyLogin(String inputId, String inputPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Ensure the line has enough parts to avoid errors
                if (parts.length == 5) {
                    String id = parts[0];
                    String password = parts[1];
                    String role = parts[4];

                    if (id.equals(inputId) && password.equals(inputPassword)) {
                        return role; // Return the role if login is successful
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null; // Return null if login fails
    }

    // NEW METHOD: Save Student Submission
    public static void saveSubmission(String studentId, String title, String abstractText, String supervisor,
            String type, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("submissions.txt", true))) {
            // Format: StudentID|Title|Abstract|Supervisor|Type|FilePath
            // We use "|" as a separator because titles might contain commas
            writer.write(studentId + "|" + title + "|" + abstractText + "|" + supervisor + "|" + type + "|" + filePath);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving submission: " + e.getMessage());
        }
    }

    // NEW METHOD: Read all submissions for the table
    public static ArrayList<String[]> getAllSubmissions() {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("submissions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split by "|"
                String[] parts = line.split("\\|");
                // We only want to show key info in the table (ID, Title, Supervisor)
                data.add(parts);
            }
        } catch (IOException e) {
            System.out.println("Error reading submissions: " + e.getMessage());
        }
        return data;
    }

    // NEW METHOD: Save Evaluation
    public static void saveEvaluation(String evaluatorId, String studentId, int score1, int score2, int score3,
            int score4, String comments) {
        int total = score1 + score2 + score3 + score4;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("evaluations.txt", true))) {
            // Format: EvaluatorID,StudentID,S1,S2,S3,S4,Total,Comments
            writer.write(evaluatorId + "," + studentId + "," + score1 + "," + score2 + "," + score3 + "," + score4 + ","
                    + total + "," + comments);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving evaluation: " + e.getMessage());
        }
    }

    // NEW METHOD: Read all evaluations for the Coordinator Report
    // Returns a list of rows: {Student ID, Total Score, Evaluator ID, Comments}
    public static ArrayList<String[]> getAllEvaluations() {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("evaluations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // File Format: EvaluatorID,StudentID,S1,S2,S3,S4,Total,Comments
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    // We extract: StudentID, Total Score, EvaluatorID
                    String stuId = parts[1];
                    String total = parts[6];
                    String evalId = parts[0];
                    data.add(new String[] { stuId, total, evalId });
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading evaluations: " + e.getMessage());
        }
        return data;
    }

    // NEW METHOD: Get Student Presentation Type (Oral/Poster)
    // Useful for determining "Best Oral" vs "Best Poster"
    public static String getStudentType(String targetId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("submissions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && parts[0].equals(targetId)) {
                    return parts[4]; // The presentation type is at index 4
                }
            }
        } catch (IOException e) {
            return "Unknown";
        }
        return "Unknown";
    }

    // 1. Get a list of all Evaluator IDs (for the dropdown)
    public static ArrayList<String> getAllEvaluatorIds() {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].trim().equalsIgnoreCase("Evaluator")) {
                    list.add(parts[0]); // Add ID
                }
            }
        } catch (IOException e) {
        }
        return list;
    }

    // 2. Save a Scheduled Session
    public static void saveSession(String date, String venue, String studentId, String evaluatorId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sessions.txt", true))) {
            // Format: Date,Venue,Student,Evaluator
            writer.write(date + "," + venue + "," + studentId + "," + evaluatorId);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving session: " + e.getMessage());
        }
    }

    // 3. Read all Sessions (for the table)
    public static ArrayList<String[]> getAllSessions() {
        ArrayList<String[]> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("sessions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line.split(","));
            }
        } catch (IOException e) {
        }
        return list;
    }
}