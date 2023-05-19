package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton exitButton;
    private JButton toNextDayButton;

    public HomeGUI(){
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
        titleLabel = new JLabel("Selamat Datang di CuciCuci System!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(MainFrame.darkBlue);

        loginButton = MainFrame.createStyledButton("Login", MainFrame.darkBlue);
        loginButton.addActionListener(e -> handleToLogin());

        registerButton = MainFrame.createStyledButton("Register", MainFrame.darkBlue);
        registerButton.addActionListener(e -> handleToRegister());

        exitButton = MainFrame.createStyledButton("Exit", MainFrame.darkBlue);
        exitButton.addActionListener(e -> handleExit());

        toNextDayButton = MainFrame.createStyledButton("Next Day", MainFrame.white);
        toNextDayButton.addActionListener(e -> handleNextDay());

        dateLabel = new JLabel(String.format("Hari ini: %s", NotaManager.fmt.format(NotaManager.cal.getTime())));
        dateLabel.setForeground(MainFrame.white);

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
        topPanel.add(titleLabel, gbc);
        topPanel.add(loginButton, gbc);
        topPanel.add(registerButton, gbc);
        topPanel.add(exitButton, gbc);
        topPanel.add(new JLabel(), gbc);

        // Menambahkan komponen ke bottomPanel
        bottomPanel.add(toNextDayButton, gbc);
        bottomPanel.add(dateLabel, gbc);

        // Menambahkan topPanel dan bottomPanel ke mainPanel
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 300;
        mainPanel.add(topPanel, gbc);
        gbc.ipady = 132;
        mainPanel.add(bottomPanel, gbc);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo("REGISTER");
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo("LOGIN");
    }

    /**
     * Method untuk keluar dari program.
     * Akan dipanggil jika pengguna menekan "exitButton"
     * */
    private static void handleExit() {
        System.exit(0);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        toNextDay();
        dateLabel.setText(String.format("Hari ini: %s", NotaManager.fmt.format(NotaManager.cal.getTime())));
        JOptionPane.showMessageDialog(this, "Kamu tidur hari ini... zzz...",
        "This is Prince Paul's Bubble Party's ability!", JOptionPane.INFORMATION_MESSAGE);
    }
}
