import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentDashboard extends JFrame {
    private String currentStudentId;
    private JTextField txtTitle, txtSupervisor, txtFilePath;
    private JTextArea txtAbstract;
    private JComboBox<String> cmbType;

    // Colors
    private Color mintGreen = new Color(232, 248, 245);
    private Color darkColor = new Color(50, 60, 70);
    private Color accentGreen = new Color(100, 160, 140);

    public StudentDashboard(String studentId) {
        this.currentStudentId = studentId;

        // Frame Setup
        setTitle("Student Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); // 50-50 Split

        // --- LEFT PANEL (Visuals) ---
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(mintGreen);
        leftPanel.setLayout(new GridBagLayout());

        JPanel leftContent = new JPanel();
        leftContent.setOpaque(false);
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));

        JLabel lblWelcome = new JLabel("Welcome, Student");
        lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblWelcome.setForeground(darkColor);
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblId = new JLabel("ID: " + studentId);
        lblId.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblId.setForeground(accentGreen);
        lblId.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblInstr = new JLabel(
                "<html><br>Please register your<br>research details and<br>upload your presentation.</html>");
        lblInstr.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblInstr.setForeground(Color.GRAY);
        lblInstr.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftContent.add(lblWelcome);
        leftContent.add(lblId);
        leftContent.add(lblInstr);
        leftPanel.add(leftContent);

        // --- RIGHT PANEL (Form) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(null); // Manual positioning

        JLabel lblHeader = new JLabel("Seminar Registration");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblHeader.setForeground(darkColor);
        lblHeader.setBounds(50, 40, 300, 30);
        rightPanel.add(lblHeader);

        // 1. Title
        JLabel lblTitle = new JLabel("Research Title");
        lblTitle.setBounds(50, 90, 150, 20);
        styleLabel(lblTitle);
        rightPanel.add(lblTitle);

        txtTitle = new JTextField();
        txtTitle.setBounds(50, 115, 380, 35);
        styleTextField(txtTitle);
        rightPanel.add(txtTitle);

        // 2. Abstract
        JLabel lblAbstract = new JLabel("Abstract");
        lblAbstract.setBounds(50, 160, 150, 20);
        styleLabel(lblAbstract);
        rightPanel.add(lblAbstract);

        txtAbstract = new JTextArea();
        txtAbstract.setLineWrap(true);
        txtAbstract.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtAbstract.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scroll = new JScrollPane(txtAbstract);
        scroll.setBounds(50, 185, 380, 80);
        rightPanel.add(scroll);

        // 3. Supervisor & Type (Side by Side)
        JLabel lblSup = new JLabel("Supervisor");
        lblSup.setBounds(50, 275, 150, 20);
        styleLabel(lblSup);
        rightPanel.add(lblSup);

        txtSupervisor = new JTextField();
        txtSupervisor.setBounds(50, 300, 180, 35);
        styleTextField(txtSupervisor);
        rightPanel.add(txtSupervisor);

        JLabel lblType = new JLabel("Type");
        lblType.setBounds(250, 275, 100, 20);
        styleLabel(lblType);
        rightPanel.add(lblType);

        String[] types = { "Oral", "Poster" };
        cmbType = new JComboBox<>(types);
        cmbType.setBounds(250, 300, 180, 35);
        cmbType.setBackground(Color.WHITE);
        rightPanel.add(cmbType);

        // 4. File Upload
        JLabel lblFile = new JLabel("Presentation File");
        lblFile.setBounds(50, 345, 150, 20);
        styleLabel(lblFile);
        rightPanel.add(lblFile);

        txtFilePath = new JTextField();
        txtFilePath.setEditable(false);
        txtFilePath.setBounds(50, 370, 270, 35);
        styleTextField(txtFilePath);
        rightPanel.add(txtFilePath);

        JButton btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(330, 370, 100, 35);
        styleButton(btnBrowse);
        btnBrowse.setBackground(accentGreen);
        btnBrowse.setForeground(Color.darkGray);
        rightPanel.add(btnBrowse);

        // 5. Submit Button
        JButton btnSubmit = new JButton("Submit Registration");
        btnSubmit.setBounds(50, 440, 380, 45);
        styleButton(btnSubmit);
        btnSubmit.setBackground(accentGreen);
        btnSubmit.setForeground(Color.darkGray);
        rightPanel.add(btnSubmit);

        // 6. Logout Link
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(350, 10, 90, 20);
        btnLogout.setBorderPainted(true);
        btnLogout.setContentAreaFilled(true);
        btnLogout.setForeground(Color.RED);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(btnLogout);

        // --- ACTIONS ---
        btnBrowse.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                txtFilePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        btnSubmit.addActionListener(e -> {
            if (txtTitle.getText().isEmpty() || txtAbstract.getText().isEmpty() || txtFilePath.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields.");
            } else {
                FileHandler.saveSubmission(currentStudentId, txtTitle.getText(), txtAbstract.getText(),
                        txtSupervisor.getText(), (String) cmbType.getSelectedItem(), txtFilePath.getText());
                JOptionPane.showMessageDialog(null, "Registration Submitted!");
            }
        });

        btnLogout.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        add(leftPanel);
        add(rightPanel);
        setVisible(true);
    }

    // Helper Styles
    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)));
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(Color.GRAY);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(darkColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
    }
}