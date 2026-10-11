import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class CoordinatorDashboard extends JFrame {
    // UI Components
    private JTabbedPane tabbedPane;
    private JTable reportTable, sessionTable;
    private DefaultTableModel reportModel, sessionModel;
    
    // Inputs for Session
    private JTextField txtDate, txtVenue;
    private JComboBox<String> cmbStudent, cmbEvaluator;

    // Colors
    private Color mintGreen = new Color(232, 248, 245);
    private Color darkColor = new Color(50, 60, 70);
    private Color accentGreen = new Color(100, 160, 140);

    public CoordinatorDashboard() {
        setTitle("Mastery Hub - Coordinator Hub");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- SIDEBAR ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(mintGreen);
        sidebar.setPreferredSize(new Dimension(250, 700));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(40, 20, 20, 20));

        JLabel lblTitle = new JLabel("Coordinator Hub");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(darkColor);
        
        JButton btnLogout = new JButton("Logout");
        styleButton(btnLogout);
        btnLogout.setBackground(new Color(200, 80, 80));
        btnLogout.setMaximumSize(new Dimension(200, 40));
        
        sidebar.add(lblTitle);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);
        add(sidebar, BorderLayout.WEST);

        // --- MAIN CONTENT (TABS) ---
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Create the two main panels
        JPanel reportPanel = createReportPanel();
        JPanel sessionPanel = createSessionPanel();

        tabbedPane.addTab("Evaluation Reports & Awards", reportPanel);
        tabbedPane.addTab("Session Management", sessionPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // --- LOGOUT ACTION ---
        btnLogout.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

// --- TAB 1: REPORTS & AWARDS ---
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel lblHeader = new JLabel("Seminar Evaluation Results");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblHeader.setForeground(darkColor);
        lblHeader.setBounds(30, 20, 300, 30);
        panel.add(lblHeader);

        // --- NEW EXPORT BUTTON ---
        JButton btnExport = new JButton("Export Report to CSV");
        btnExport.setBounds(600, 20, 180, 30); // Top right corner
        styleButton(btnExport);
        btnExport.setBackground(new Color(0, 102, 204)); // Professional Blue
        panel.add(btnExport);
        // -------------------------

        // Table
        String[] columns = {"Student ID", "Type", "Total Score", "Evaluator ID"};
        reportModel = new DefaultTableModel(columns, 0);
        reportTable = new JTable(reportModel);
        styleTable(reportTable);
        loadReportData();
        
        JScrollPane scroll = new JScrollPane(reportTable);
        scroll.setBounds(30, 60, 750, 300);
        panel.add(scroll);

        // Award Buttons (Keep your existing code here)
        JButton btnOral = new JButton("Best Oral Presenter");
        btnOral.setBounds(30, 400, 200, 50);
        styleButton(btnOral);
        btnOral.setBackground(accentGreen);
        panel.add(btnOral);

        JButton btnPoster = new JButton("Best Poster Presenter");
        btnPoster.setBounds(250, 400, 200, 50);
        styleButton(btnPoster);
        btnPoster.setBackground(accentGreen);
        panel.add(btnPoster);
        
        JButton btnChoice = new JButton("People's Choice Award");
        btnChoice.setBounds(470, 400, 200, 50);
        styleButton(btnChoice);
        btnChoice.setBackground(new Color(255, 153, 51)); 
        panel.add(btnChoice);

        // Logic
        btnOral.addActionListener(e -> findWinner("Oral"));
        btnPoster.addActionListener(e -> findWinner("Poster"));
        btnChoice.addActionListener(e -> findPeoplesChoice());
        
        // --- NEW EXPORT ACTION ---
        btnExport.addActionListener(e -> exportToCSV());
        // -------------------------

        return panel;
    }

    // --- TAB 2: SESSION MANAGEMENT ---
    private JPanel createSessionPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel lblHeader = new JLabel("Create & Assign Sessions");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblHeader.setForeground(darkColor);
        lblHeader.setBounds(30, 20, 300, 30);
        panel.add(lblHeader);

        // Form Inputs
        JLabel lblDate = new JLabel("Date (DD/MM/YYYY):");
        lblDate.setBounds(30, 70, 150, 20);
        panel.add(lblDate);
        txtDate = new JTextField();
        txtDate.setBounds(30, 95, 180, 30);
        panel.add(txtDate);

        JLabel lblVenue = new JLabel("Venue:");
        lblVenue.setBounds(230, 70, 150, 20);
        panel.add(lblVenue);
        txtVenue = new JTextField();
        txtVenue.setBounds(230, 95, 180, 30);
        panel.add(txtVenue);

        JLabel lblStu = new JLabel("Assign Student:");
        lblStu.setBounds(30, 140, 150, 20);
        panel.add(lblStu);
        
        // Load Students for Dropdown
        cmbStudent = new JComboBox<>();
        ArrayList<String[]> subs = FileHandler.getAllSubmissions();
        for(String[] s : subs) cmbStudent.addItem(s[0]); // Add Student IDs
        cmbStudent.setBounds(30, 165, 180, 30);
        panel.add(cmbStudent);

        JLabel lblEval = new JLabel("Assign Evaluator:");
        lblEval.setBounds(230, 140, 150, 20);
        panel.add(lblEval);

        // Load Evaluators for Dropdown
        cmbEvaluator = new JComboBox<>();
        ArrayList<String> evals = FileHandler.getAllEvaluatorIds();
        for(String id : evals) cmbEvaluator.addItem(id);
        cmbEvaluator.setBounds(230, 165, 180, 30);
        panel.add(cmbEvaluator);

        JButton btnSave = new JButton("Create Schedule");
        btnSave.setBounds(450, 165, 150, 30);
        styleButton(btnSave);
        btnSave.setBackground(darkColor);
        panel.add(btnSave);

        // Schedule Table
        JLabel lblList = new JLabel("Current Schedule:");
        lblList.setBounds(30, 220, 200, 20);
        panel.add(lblList);

        String[] cols = {"Date", "Venue", "Student", "Evaluator"};
        sessionModel = new DefaultTableModel(cols, 0);
        sessionTable = new JTable(sessionModel);
        styleTable(sessionTable);
        loadSessionData();

        JScrollPane scroll = new JScrollPane(sessionTable);
        scroll.setBounds(30, 250, 750, 250);
        panel.add(scroll);

        // Logic
        btnSave.addActionListener(e -> {
            String d = txtDate.getText();
            String v = txtVenue.getText();
            String s = (String) cmbStudent.getSelectedItem();
            String ev = (String) cmbEvaluator.getSelectedItem();
            
            if(d.isEmpty() || v.isEmpty() || s == null || ev == null) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            } else {
                FileHandler.saveSession(d, v, s, ev);
                loadSessionData(); // Refresh table
                JOptionPane.showMessageDialog(this, "Session Scheduled!");
            }
        });

        return panel;
    }

    // --- HELPER METHODS ---

    private void loadReportData() {
        reportModel.setRowCount(0); // Clear existing
        ArrayList<String[]> evals = FileHandler.getAllEvaluations();
        for (String[] row : evals) {
            String type = FileHandler.getStudentType(row[0]);
            reportModel.addRow(new String[]{row[0], type, row[1], row[2]});
        }
    }

    private void loadSessionData() {
        sessionModel.setRowCount(0);
        ArrayList<String[]> sessions = FileHandler.getAllSessions();
        for (String[] row : sessions) {
            sessionModel.addRow(row);
        }
    }

    private void findWinner(String category) {
        int max = -1;
        String winner = "None";
        for(int i=0; i<reportModel.getRowCount(); i++) {
            if(reportModel.getValueAt(i, 1).toString().equalsIgnoreCase(category)) {
                int score = Integer.parseInt(reportModel.getValueAt(i, 2).toString());
                if(score > max) { max = score; winner = reportModel.getValueAt(i,0).toString(); }
            }
        }
        JOptionPane.showMessageDialog(this, "Best " + category + ": " + winner + " (Score: " + max + ")");
    }
    
    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report As");
        
        // Default file name
        fileChooser.setSelectedFile(new java.io.File("Seminar_Report.csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            
            try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(fileToSave))) {
                // 1. Write Headers
                for (int i = 0; i < reportModel.getColumnCount(); i++) {
                    writer.write(reportModel.getColumnName(i) + (i == reportModel.getColumnCount() - 1 ? "" : ","));
                }
                writer.newLine();

                // 2. Write Rows
                for (int i = 0; i < reportModel.getRowCount(); i++) {
                    for (int j = 0; j < reportModel.getColumnCount(); j++) {
                        Object value = reportModel.getValueAt(i, j);
                        writer.write(value + (j == reportModel.getColumnCount() - 1 ? "" : ","));
                    }
                    writer.newLine();
                }
                
                JOptionPane.showMessageDialog(this, "Report exported successfully to:\n" + fileToSave.getAbsolutePath());
                
            } catch (java.io.IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }
    
    // Logic for People's Choice (Simulated by highest Presentation Score or Total Score if no voting data)
    private void findPeoplesChoice() {
        // For this lab, we can assume People's Choice goes to the highest overall scorer regardless of category
        int max = -1;
        String winner = "None";
        for(int i=0; i<reportModel.getRowCount(); i++) {
            int score = Integer.parseInt(reportModel.getValueAt(i, 2).toString());
            if(score > max) { max = score; winner = reportModel.getValueAt(i,0).toString(); }
        }
        JOptionPane.showMessageDialog(this, "People's Choice Award goes to: " + winner + "\nWith a high score of: " + max);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(darkColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
    }
    
    private void styleTable(JTable table) {
        table.setRowHeight(25);
        table.setShowGrid(false);
        JTableHeader header = table.getTableHeader();
        header.setBackground(darkColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
    }
}