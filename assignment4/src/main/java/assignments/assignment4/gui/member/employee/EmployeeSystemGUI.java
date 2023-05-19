package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{
                MainFrame.createStyledButton("It's nyuci time", MainFrame.darkBlue),
                MainFrame.createStyledButton("Display List Nota", MainFrame.darkBlue)
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
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void cuci() {
        String notaString = "";

        JOptionPane.showMessageDialog(this, String.format("Stand back! %s beginning to nyuci!", loggedInMember.getNama()),
        "Nyuci Time", JOptionPane.INFORMATION_MESSAGE);

        for (Nota nota : NotaManager.notaList) {
            notaString += nota.kerjakan() + "\n";
        }

        if (notaString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to Cuci here", "Nyuci Results", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, notaString, "Nyuci Results", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void displayNota() {
        String notaString = "";

        for (Nota nota : NotaManager.notaList) {
            notaString += nota.getNotaStatus() + "\n";
        }

        if (notaString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada nota", "List Nota", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, notaString, "List Nota", JOptionPane.INFORMATION_MESSAGE);
    }
}
