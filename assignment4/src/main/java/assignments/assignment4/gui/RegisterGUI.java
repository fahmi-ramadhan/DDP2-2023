package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout
        this.loginManager = loginManager;

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
        nameLabel = new JLabel("Masukkan nama Anda:");
        nameLabel.setForeground(MainFrame.darkBlue);

        nameTextField = MainFrame.createStyledTextField();

        phoneLabel = new JLabel("Masukkan nomor handphone Anda:");
        phoneLabel.setForeground(MainFrame.darkBlue);

        phoneTextField = MainFrame.createStyledTextField();

        passwordLabel = new JLabel("Masukkan password Anda:");
        passwordLabel.setForeground(MainFrame.darkBlue);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 25));
        passwordField.setBorder(BorderFactory.createLineBorder(MainFrame.darkBlue, 2));
        passwordField.setForeground(MainFrame.darkBlue);
        passwordField.setBackground(MainFrame.white);

        registerButton = MainFrame.createStyledButton("Login", MainFrame.white);
        registerButton.addActionListener(e -> handleRegister());

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
        topPanel.add(nameLabel, gbc);
        topPanel.add(nameTextField, gbc);
        topPanel.add(phoneLabel, gbc);
        topPanel.add(phoneTextField, gbc);
        topPanel.add(passwordLabel, gbc);
        topPanel.add(passwordField, gbc);
        topPanel.add(new JLabel(), gbc);
        
        // Menambahkan komponen ke bottomPanel
        bottomPanel.add(registerButton, gbc);
        bottomPanel.add(backButton, gbc);
        
        // Menambahkan topPanel dan bottomPanel ke mainPanel
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 300;
        mainPanel.add(topPanel, gbc);
        gbc.ipady = 144;
        mainPanel.add(bottomPanel, gbc);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        MainFrame.getInstance().navigateTo("HOME");
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        // Assign variabel-variabel info member dari input user
        String nama = nameTextField.getText().trim();
        String noHp = phoneTextField.getText();
        String password = new String(passwordField.getPassword());
        Member registeredMember = loginManager.register(nama, noHp, password);

        // Cek apakah ada field yang kosong
        if (nama.isEmpty() || noHp.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field di atas wajib diisi!",
            "Empty Field", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        // Cek apakah nomor hp valid
        else if (!noHp.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Nomor handphone harus berisi angka!",
            "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            phoneTextField.setText("");
            return;
        } 
        // Cek apakah gagal register karena user sudah ada
        else if(registeredMember == null){
            JOptionPane.showMessageDialog(this, String.format("User dengan nama %s dan nomor hp %s sudah ada!", nama, noHp),
            "Registration Failed", JOptionPane.ERROR_MESSAGE);
        } 
        // Jika berhasil register
        else {
            JTextArea text = new JTextArea(String.format("Berhasil membuat user dengan ID %s!", registeredMember.getId()));
            text.setBackground(MainFrame.lightBlue);
            text.setForeground(MainFrame.darkBlue);
            text.setFont(new Font("Arial", Font.BOLD, 12));
            text.setEditable(false);
            JOptionPane.showMessageDialog(this, text, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        }
        
        // Reset semua field dan pergi ke panel HOME
        nameTextField.setText("");
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        MainFrame.getInstance().navigateTo("HOME");
    }
}
