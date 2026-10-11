import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class EvaluatorDashboard extends JFrame {
    private String evaluatorId;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtStudentId, txtScore1, txtScore2, txtScore3, txtScore4;
    private JTextArea txtComments;

    // Colors
    private Color mintGreen = new Color(232, 248, 245);
    private Color darkColor = new Color(50, 60, 70);
    private Color accentGreen = new Color(100, 160, 140);

    public EvaluatorDashboard(String evaluatorId) {
        this.evaluatorId = evaluatorId;

        setTitle("Evaluator Panel");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- LEFT SIDEBAR ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(mintGreen);
        sidebar.setPreferredSize(new Dimension(250, 700));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(40, 20, 20, 20));

        JLabel lblTitle = new JLabel("Evaluator Panel");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(darkColor);

        JLabel lblUser = new JLabel("User: " + evaluatorId);
        lblUser.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUser.setForeground(accentGreen);

        JButton btnLogout = new JButton("Logout");
        styleButton(btnLogout);
        btnLogout.setBackground(new Color(200, 80, 80)); // Reddish for logout
        btnLogout.setMaximumSize(new Dimension(200, 40));
        btnLogout.setForeground(Color.red);

        sidebar.add(lblTitle);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(lblUser);
        sidebar.add(Box.createVerticalGlue()); // Pushes logout to bottom
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        // --- RIGHT CONTENT ---
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(null);

        JLabel lblTableTitle = new JLabel("Assigned Submissions");
        lblTableTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTableTitle.setForeground(darkColor);
        lblTableTitle.setBounds(30, 20, 300, 30);
        contentPanel.add(lblTableTitle);

        // Table Setup
        String[] columns = { "Student ID", "Title", "Abstract", "Supervisor", "Type", "File Path" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        styleTable(table);
        loadSubmissions();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 60, 780, 250);
        contentPanel.add(scrollPane);

        // Grading Section
        JLabel lblGrading = new JLabel("Grading Form");
        lblGrading.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblGrading.setForeground(darkColor);
        lblGrading.setBounds(30, 330, 200, 30);
        contentPanel.add(lblGrading);

        // Selected Student
        JLabel lblSel = new JLabel("Student ID:");
        lblSel.setBounds(30, 370, 100, 20);
        contentPanel.add(lblSel);

        txtStudentId = new JTextField();
        txtStudentId.setEditable(false);
        txtStudentId.setBounds(110, 370, 100, 25);
        contentPanel.add(txtStudentId);

        // Scores
        int yPos = 410;
        txtScore1 = addScoreField(contentPanel, "Problem Clarity (0-10)", 30, yPos);
        txtScore2 = addScoreField(contentPanel, "Methodology (0-10)", 420, yPos);

        yPos = 450;
        txtScore3 = addScoreField(contentPanel, "Results (0-10)", 30, yPos);
        txtScore4 = addScoreField(contentPanel, "Presentation (0-10)", 420, yPos);

        // Comments
        JLabel lblComm = new JLabel("Comments:");
        lblComm.setBounds(30, 500, 100, 20);
        contentPanel.add(lblComm);

        txtComments = new JTextArea();
        txtComments.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollComm = new JScrollPane(txtComments);
        scrollComm.setBounds(110, 500, 500, 60);
        contentPanel.add(scrollComm);

        JButton btnSubmit = new JButton("Submit Evaluation");
        btnSubmit.setBounds(630, 500, 180, 60);
        styleButton(btnSubmit);
        contentPanel.add(btnSubmit);
        btnSubmit.setBackground(accentGreen);
        btnSubmit.setForeground(Color.darkGray);

        add(contentPanel, BorderLayout.CENTER);

        // --- ACTIONS ---
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                txtStudentId.setText(model.getValueAt(table.getSelectedRow(), 0).toString());
            }
        });

        btnSubmit.addActionListener(e -> {
            if (txtStudentId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a student first.");
                return;
            }
            try {
                int s1 = Integer.parseInt(txtScore1.getText());
                int s2 = Integer.parseInt(txtScore2.getText());
                int s3 = Integer.parseInt(txtScore3.getText());
                int s4 = Integer.parseInt(txtScore4.getText());
                FileHandler.saveEvaluation(evaluatorId, txtStudentId.getText(), s1, s2, s3, s4, txtComments.getText());
                JOptionPane.showMessageDialog(this, "Saved!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Scores");
            }
        });

        btnLogout.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    // Helper to add score fields
    private JTextField addScoreField(JPanel p, String label, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 150, 25);
        p.add(lbl);
        JTextField txt = new JTextField("0");
        txt.setBounds(x + 160, y, 50, 25);
        p.add(txt);
        return txt;
    }

    private void loadSubmissions() {
        for (String[] row : FileHandler.getAllSubmissions())
            model.addRow(row);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(darkColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    private void styleTable(JTable table) {
        table.setRowHeight(25);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        JTableHeader header = table.getTableHeader();
        header.setBackground(darkColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
    }
}