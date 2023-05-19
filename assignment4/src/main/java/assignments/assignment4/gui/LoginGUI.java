package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout

        // Set up main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * */
    private void initGUI() {
        // Membuat GUI components
        idLabel = new JLabel("Masukkan ID Anda:");
        idLabel.setForeground(MainFrame.darkBlue);

        idTextField = MainFrame.createStyledTextField();

        passwordLabel = new JLabel("Masukkan password Anda:");
        passwordLabel.setForeground(MainFrame.darkBlue);
        
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 25));
        passwordField.setBorder(BorderFactory.createLineBorder(MainFrame.darkBlue, 2));
        passwordField.setForeground(MainFrame.darkBlue);
        passwordField.setBackground(MainFrame.white);

        loginButton = MainFrame.createStyledButton("Login", MainFrame.white);
        loginButton.addActionListener(e -> handleLogin());
        
        backButton = MainFrame.createStyledButton("Kembali", MainFrame.white);
        backButton.addActionListener(e -> handleBack());

        // Membuat topPanel dan bottomPanel untuk menampung GUI components
        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(MainFrame.lightBlue);
        bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(MainFrame.darkBlue);

        // Konfigurasi awal constraints untuk GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;

        // Menambahkan komponen ke topPanel
        topPanel.add(new JLabel(), gbc);
        topPanel.add(idLabel, gbc);
        topPanel.add(idTextField, gbc);
        topPanel.add(passwordLabel, gbc);
        topPanel.add(passwordField, gbc);
        topPanel.add(new JLabel(), gbc);
        
        // Menambahkan komponen ke bottomPanel
        bottomPanel.add(loginButton, gbc);
        bottomPanel.add(backButton, gbc);
        
        // Menambahkan topPanel dan bottomPanel ke mainPanel
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 300;
        mainPanel.add(topPanel, gbc);
        gbc.ipady = 110;
        mainPanel.add(bottomPanel, gbc);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        idTextField.setText("");
        passwordField.setText("");
        MainFrame.getInstance().navigateTo("HOME");
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        if (!MainFrame.getInstance().login(idTextField.getText(), new String(passwordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, "ID atau Password Invalid.", "Invalid ID or Password", JOptionPane.ERROR_MESSAGE);
        }
        idTextField.setText("");
        passwordField.setText("");
    }
}
