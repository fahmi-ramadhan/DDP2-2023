package assignments.assignment4.gui.member;

import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class AbstractMemberGUI extends JPanel implements Loginable{
    private JLabel welcomeLabel;
    private JLabel loggedInAsLabel;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    protected Member loggedInMember;
    private final SystemCLI systemCLI;

    public AbstractMemberGUI(SystemCLI systemCLI) {
        super(new BorderLayout());
        this.systemCLI = systemCLI;

        // Set up main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    private void initGUI() {
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

        // Membuat GUI components dan menambahkannya ke panel yang sesuai
        welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(MainFrame.darkBlue);

        topPanel.add(new JLabel(), gbc);
        topPanel.add(welcomeLabel, gbc);
        
        JButton[] buttons = createButtons();
        ActionListener[] listeners = createActionListeners();

        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];
            button.addActionListener(listeners[i]);
            topPanel.add(button, gbc);
        }

        topPanel.add(new JLabel(), gbc);

        JButton logoutButton = MainFrame.createStyledButton("Logout", MainFrame.white);
        logoutButton.addActionListener(e -> MainFrame.getInstance().logout());

        loggedInAsLabel = new JLabel();
        loggedInAsLabel.setForeground(MainFrame.white);

        bottomPanel.add(logoutButton, gbc);
        bottomPanel.add(loggedInAsLabel, gbc);
        
        // Menambahkan topPanel dan bottomPanel ke mainPanel
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 300;
        mainPanel.add(topPanel, gbc);
        gbc.ipady = 112;
        mainPanel.add(bottomPanel, gbc);
    }

    /**
     * Method untuk login pada panel.
     * <p>
     * Method ini akan melakukan pengecekan apakah ID dan passowrd yang telah diberikan dapat login
     * pada panel ini. <p>
     * Jika bisa, member akan login pada panel ini, method akan:<p>
     *  - mengupdate Welcome & LoggedInAs label.<p>
     *  - mengupdate LoggedInMember sesuai dengan instance pemilik ID dan password.
     *
     * @param id -> ID anggota yang akan diautentikasi.
     * @param password -> password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     * */
    public boolean login(String id, String password) {
        Member authMember = systemCLI.authUser(id, password);
        if (authMember != null) {
            loggedInMember = authMember;
            welcomeLabel.setText(String.format("Welcome! %s", loggedInMember.getNama()));
            loggedInAsLabel.setText(String.format("Logged in as %s", loggedInMember.getId()));
            return true;
        }
        return false;
    }

    /**
     * Method untuk logout pada panel ini.
     * Akan mengubah loggedInMember menjadi null.
     * */
    public void logout() {
        loggedInMember = null;
    }

    /**
     * Method ini mensupply buttons apa saja yang akan dimuat oleh panel ini.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     * Harus diimplementasikan sesuai class yang menginherit class ini.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    protected abstract JButton[] createButtons();

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons().
     * Harus diimplementasikan sesuai class yang menginherit class ini.
     *
     * @return Array of ActionListener.
     * */
    protected abstract ActionListener[] createActionListeners();
}
