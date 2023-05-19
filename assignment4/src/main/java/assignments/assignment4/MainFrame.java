package assignments.assignment4;
import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment4.gui.HomeGUI;
import assignments.assignment4.gui.LoginGUI;
import assignments.assignment4.gui.RegisterGUI;
import assignments.assignment4.gui.member.Loginable;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;
import assignments.assignment4.gui.member.member.CreateNotaGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame{
    private static MainFrame instance;
    private final MemberSystem memberSystem = new MemberSystem();
    private final EmployeeSystem employeeSystem = new EmployeeSystem();
    private final CardLayout cards = new CardLayout();
    private final JPanel mainPanel = new JPanel(cards);
    private final LoginManager loginManager = new LoginManager(employeeSystem, memberSystem);
    private final HomeGUI homeGUI = new HomeGUI();
    private final RegisterGUI registerGUI = new RegisterGUI(loginManager);
    private final LoginGUI loginGUI = new LoginGUI(loginManager);
    private final EmployeeSystemGUI employeeSystemGUI = new EmployeeSystemGUI(employeeSystem);
    private final MemberSystemGUI memberSystemGUI = new MemberSystemGUI(memberSystem);
    private final CreateNotaGUI createNotaGUI = new CreateNotaGUI(memberSystemGUI);
    private final Loginable[] loginablePanel = new Loginable[]{employeeSystemGUI, memberSystemGUI};
    public static final Color lightBlue = new Color(175, 211, 226);
    public static final Color darkBlue = new Color(20, 108, 148);
    public static final Color white = new Color(241, 246, 249);

    private MainFrame(){
        super("CuciCuciSystem");
        // for context dari 2 employee baru ini : https://ristek.link/karyawan-baru-cucicuci
        employeeSystem.addEmployee(new Employee[]{
                new Employee("delta Epsilon Huha Huha", "ImplicitDiff"),
                new Employee("Regret", "FansBeratKanaArima")
        });
        
        // Konfigurasi UI untuk message dialog
        UIManager.put("OptionPane.background", MainFrame.lightBlue);
        UIManager.put("Panel.background", MainFrame.lightBlue);
        UIManager.put("OptionPane.messageForeground", MainFrame.darkBlue);
        UIManager.put("Button.foreground", MainFrame.darkBlue);
        UIManager.put("Button.border", BorderFactory.createLineBorder(MainFrame.darkBlue, 2));
        UIManager.put("Button.background", MainFrame.lightBlue);

        // Konfigurasi main frame
        ImageIcon icon = new ImageIcon(getClass().getResource("images/appIcon.png"));
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 432);
        setVisible(true);

        initGUI();
        cards.show(mainPanel, HomeGUI.KEY);
        add(mainPanel);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * */
    private void initGUI() {
        mainPanel.add(homeGUI, HomeGUI.KEY);
        mainPanel.add(registerGUI, RegisterGUI.KEY);
        mainPanel.add(loginGUI, LoginGUI.KEY);
        mainPanel.add(employeeSystemGUI, EmployeeSystemGUI.KEY);
        mainPanel.add(memberSystemGUI, MemberSystemGUI.KEY);
        mainPanel.add(createNotaGUI, CreateNotaGUI.KEY);
    }

    /**
     * Method untuk mendapatkan instance MainFrame.
     * Instance Class MainFrame harus diambil melalui method ini agar menjamin hanya terdapat satu Frame pada program.
     *
     * @return instance dari class MainFrame
     * */
    public static MainFrame getInstance(){
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    /**
     * Method untuk pergi ke panel sesuai dengan yang diberikan pada param.
     *
     * @param page -> key dari halaman yang diinginkan.
     * */
    public void navigateTo(String page){
        cards.show(mainPanel, page);
    }

    /**
     * Method untuk login pada sistem.
     * Jika gagal login akan mengembalikan boolean false dan jika berhasil login: <p>
     * - return boolean true <p>
     * - menampilkan halaman yang sesuai <p>
     *
     * @param id -> ID dari pengguna
     * @param password -> password dari pengguna
     * @return boolean yang menandakan apakah login berhasil atau gagal.
     * */
    public boolean login(String id, String password){
        for (Loginable panel : loginablePanel) {
            if (panel.login(id, password)) {
                navigateTo(panel.getPageName());
                return true;
            }
        }
        return false;
    }

    /**
     * Method untuk logout dari sistem, kemudian menampilkan halaman Home.
     * */
    public void logout(){
        for (Loginable panel : loginablePanel) {
            panel.logout();
        }
        navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk membuat button yang sudah stylize
     * 
     * @param text -> text pada button
     * @param color -> warna text dan border pada button
     * @return button yang sudah stylize tapi belum memiliki action listener
     * */
    public static JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(color);
        button.setPreferredSize(new Dimension(130, 25));
        button.setBorder(BorderFactory.createLineBorder(color, 2));
        return button;
    }

    /**
     * Method untuk membuat text field yang sudah stylize
     * 
     * @return textfield yang sudah stylize
     */
    public static JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 25));
        textField.setBorder(BorderFactory.createLineBorder(MainFrame.darkBlue, 2));
        textField.setFont(new Font("Arial", Font.BOLD, 12));
        textField.setForeground(MainFrame.darkBlue);
        textField.setBackground(MainFrame.white);
        return textField;
    }

    public static void main(String[] args) {
        // menampilkan GUI kalian.
        // Jika ingin tau lebih lanjut mengapa menggunakan SwingUtilities.invokeLater
        // silakan akses https://stackoverflow.com/questions/6567870/what-does-swingutilities-invokelater-do
        // Tapi in general kalian tidak usah terlalu overthinking line ini selain fungsi utamanya adalah menampilkan GUI
        SwingUtilities.invokeLater(MainFrame::getInstance);
    }
}
