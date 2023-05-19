package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{
                MainFrame.createStyledButton("Saya ingin laundry", MainFrame.darkBlue),
                MainFrame.createStyledButton("Lihat detail nota saya", MainFrame.darkBlue)
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        String detailNota = "";

        for (Nota nota : loggedInMember.getNotaList()) {
            detailNota += nota + "\n\n";
        }

        if (detailNota.isEmpty()) {
            detailNota += "Belum pernah laundry di CuciCuci, hiks :'(";
        }

        JTextArea detailNotaArea = new JTextArea(detailNota);
        detailNotaArea.setBackground(MainFrame.lightBlue);
        detailNotaArea.setForeground(MainFrame.darkBlue);
        detailNotaArea.setFont(new Font("Arial", Font.BOLD, 12));
        detailNotaArea.setEditable(false);

        JScrollPane detailNotaPane = new JScrollPane(detailNotaArea);
        detailNotaPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, detailNotaPane, "Detail Nota", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        MainFrame.getInstance().navigateTo("CREATE_NOTA");
    }
}
