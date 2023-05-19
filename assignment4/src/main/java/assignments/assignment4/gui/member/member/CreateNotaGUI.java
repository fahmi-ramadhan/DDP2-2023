package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        super(new BorderLayout());
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

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
        paketLabel = new JLabel("Paket Laundry:");
        paketLabel.setForeground(MainFrame.darkBlue);
        
        paketComboBox = new JComboBox<>(new String[] {"Express", "Fast", "Reguler"});
        paketComboBox.setForeground(MainFrame.darkBlue);
        paketComboBox.setBackground(MainFrame.white);
        paketComboBox.setBorder(BorderFactory.createLineBorder(MainFrame.darkBlue, 2));
        
        showPaketButton = new JButton("Show Paket");
        showPaketButton.setBackground(MainFrame.white);
        showPaketButton.setForeground(MainFrame.darkBlue);
        showPaketButton.setBorder(BorderFactory.createLineBorder(MainFrame.darkBlue, 3));
        showPaketButton.setPreferredSize(new Dimension(80, 29));
        showPaketButton.addActionListener(e -> showPaket());
        
        beratLabel = new JLabel("Berat Cucian (Kg):");
        beratLabel.setForeground(MainFrame.darkBlue);
        
        beratTextField = MainFrame.createStyledTextField();
        beratTextField.setBorder(BorderFactory.createLineBorder(MainFrame.darkBlue, 3));
        
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000 / kg)");
        setrikaCheckBox.setBackground(MainFrame.lightBlue);
        setrikaCheckBox.setForeground(MainFrame.darkBlue);
        
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000 / 4kg pertama, kemudian 500 / kg)");
        antarCheckBox.setBackground(MainFrame.lightBlue);
        antarCheckBox.setForeground(MainFrame.darkBlue);
        
        createNotaButton = MainFrame.createStyledButton("Buat Nota", MainFrame.white);
        createNotaButton.addActionListener(e -> createNota());
        
        backButton = MainFrame.createStyledButton("Kembali", MainFrame.white);
        backButton.addActionListener(e -> handleBack());

        // Membuat topPanel dan bottomPanel untuk menampung GUI components
        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(MainFrame.lightBlue);
        bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(MainFrame.darkBlue);

        // Konfigurasi awal constraints untuk GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Menambakan komponen ke topPanel
        gbc.gridheight = 4;
        topPanel.add(new JLabel(), gbc);

        gbc.gridx = 4;
        topPanel.add(new JLabel(), gbc);

        gbc.gridheight = 1;
        gbc.gridx = 1;
        topPanel.add(new JLabel(), gbc);

        gbc.gridy = 1;
        topPanel.add(paketLabel, gbc);

        gbc.gridx = 2;
        topPanel.add(paketComboBox, gbc);

        gbc.gridx = 3;
        topPanel.add(showPaketButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        topPanel.add(beratLabel, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        topPanel.add(beratTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = 3;
        topPanel.add(setrikaCheckBox, gbc);
        topPanel.add(antarCheckBox, gbc);
        topPanel.add(new JLabel(), gbc);

        // Menambahkan komponen ke bottomPanel
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        bottomPanel.add(createNotaButton, gbc);

        gbc.gridy = 1;
        bottomPanel.add(backButton, gbc);

        // Menambahkan topPanel dan bottomPanel ke mainPanel
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.ipady = 300;
        mainPanel.add(topPanel, gbc);

        gbc.gridy = 1;
        gbc.ipady = 192;
        mainPanel.add(bottomPanel, gbc);
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        try {
            // Inisiasi dan cek variabel untuk membuat nota
            Member loggedInMember = memberSystemGUI.getLoggedInMember();
            String paket = (String) paketComboBox.getSelectedItem();
            int berat = Integer.parseInt(beratTextField.getText());
            if (berat < 1) throw new NumberFormatException();
            if (berat < 2) {
                berat = 2;
                JOptionPane.showMessageDialog(this, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            // Membuat Nota
            Nota nota = new Nota(loggedInMember, berat, paket, fmt.format(cal.getTime()));

            // Menambahkan service tambahan pada nota jika ada
            boolean disetrika = setrikaCheckBox.isSelected();
            boolean diantar = antarCheckBox.isSelected();
            if (disetrika) nota.addService(new SetrikaService());
            if (diantar) nota.addService(new AntarService());

            // Menambahkan nota ke notaList pada NotaManager dan instance Member
            loggedInMember.addNota(nota);
            NotaManager.addNota(nota);
            JOptionPane.showMessageDialog(this, "Nota berhasil dibuat!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            // Handle Exception jika berat cucian bukan bilangan bulat positif
            JOptionPane.showMessageDialog(this, "Berat cucian harus berupa bilangan bulat positif",
            "Error", JOptionPane.ERROR_MESSAGE);
            return;

        } finally {
            // Reset combo box, text field, dan check box
            paketComboBox.setSelectedItem("Express");
            beratTextField.setText("");
            setrikaCheckBox.setSelected(false);
            antarCheckBox.setSelected(false);
        }
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo("MEMBER");
    }
}
