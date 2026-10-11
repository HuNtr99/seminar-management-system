import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    // UI Components
    private JTextField txtId;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginFrame() {
        // 1. Frame Setup
        setTitle("Seminar Management System - Login");
        setSize(900, 600); // Wider size to match the laptop view
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); // Split the window into 2 equal columns

        // 2. LEFT PANEL (The Green Visual Side)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(232, 248, 245)); // Mint Green color
        leftPanel.setLayout(new GridBagLayout()); // Use GridBag to center the text/image
        
        // Create content for left panel
        JPanel leftContent = new JPanel();
        leftContent.setOpaque(false);
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));

        // Placeholder for the illustration (You can replace "null" with an ImageIcon path)
        JLabel lblIllustration = new JLabel("", SwingConstants.CENTER);
        lblIllustration.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblIllustration.setForeground(new Color(100, 160, 140));
        lblIllustration.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitleLeft = new JLabel("Seminar Management System ");
        lblTitleLeft.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitleLeft.setForeground(new Color(50, 60, 70));
        lblTitleLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubTitle = new JLabel("Unleash Your Academic Success");
        lblSubTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubTitle.setForeground(new Color(100, 100, 100));
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add to left content
        leftContent.add(lblIllustration);
        leftContent.add(Box.createVerticalStrut(20)); // Spacing
        leftContent.add(lblTitleLeft);
        leftContent.add(Box.createVerticalStrut(10));
        leftContent.add(lblSubTitle);
        
        leftPanel.add(leftContent); // Add centered content to left panel

        // 3. RIGHT PANEL (The White Form Side)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(null); // Keeping null layout for precise positioning of form elements

        // -- Logo/Header --
        JLabel lblBrand = new JLabel("LOGIN");
        lblBrand.setFont(new Font("Serif", Font.BOLD, 20));
        lblBrand.setForeground(new Color(50, 60, 70));
        lblBrand.setBounds(190, 90, 200, 30);
        rightPanel.add(lblBrand);

        // -- Username Field --
        JLabel lblId = new JLabel("Username");
        lblId.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblId.setForeground(Color.GRAY);
        lblId.setBounds(50, 130, 150, 20);
        rightPanel.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(50, 155, 340, 40);
        styleTextField(txtId); // Helper method to make it look nice
        rightPanel.add(txtId);

        // -- Password Field --
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblPass.setForeground(Color.GRAY);
        lblPass.setBounds(50, 210, 150, 20);
        rightPanel.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(50, 235, 340, 40);
        styleTextField(txtPass);
        rightPanel.add(txtPass);

        // -- Forgot Password Link --
        JLabel lblForgot = new JLabel();
        lblForgot.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblForgot.setForeground(new Color(100, 160, 140)); // Greenish text
        lblForgot.setBounds(290, 280, 100, 20);
        rightPanel.add(lblForgot);


        // -- Sign In Button --
        btnLogin = new JButton("Sign in");
        btnLogin.setBounds(100, 330, 240, 45);
        btnLogin.setBackground(Color.WHITE);
        btnLogin.setForeground(Color.DARK_GRAY);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 12));
        rightPanel.add(btnLogin);
        


        // 4. ADD PANELS TO FRAME
        add(leftPanel);
        add(rightPanel);

        // *** NEW LINE: ENABLE ENTER KEY ***
        getRootPane().setDefaultButton(btnLogin);
        
        // 5. BUTTON ACTION (Original Logic)
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                String pass = new String(txtPass.getPassword());
                
                // --- YOUR ORIGINAL LOGIC FROM FileHandler ---
                String role = FileHandler.verifyLogin(id, pass); //

                if (role != null) {
                    JOptionPane.showMessageDialog(null, "Login Successful! Role: " + role);
                    
                    if (role.equalsIgnoreCase("Student")) {
                        new StudentDashboard(id).setVisible(true);
                    } else if (role.equalsIgnoreCase("Coordinator")) {
                        new CoordinatorDashboard().setVisible(true);
                    } else if (role.equalsIgnoreCase("Evaluator")) {
                        new EvaluatorDashboard(id).setVisible(true);
                    }
                    
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid ID or Password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    // Helper method to style text fields with padding
    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            new EmptyBorder(5, 10, 5, 10) // Padding: Top, Left, Bottom, Right
        ));
    }

    public static void main(String[] args) {
        // Optional: Set system look and feel for cleaner components
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        new LoginFrame();
    }
}