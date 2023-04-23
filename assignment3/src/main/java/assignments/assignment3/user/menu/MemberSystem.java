package assignments.assignment3.user.menu;
import assignments.assignment3.nota.Nota;
import static assignments.assignment3.nota.NotaManager.*;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice) {
            case 1 -> generateNota();
            case 2 -> displayNota();
            case 3 -> logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        Member[] newMemberList = new Member[memberList.length + 1];
        for (int i = 0; i < memberList.length; i++) {
            newMemberList[i] = memberList[i];
        }
        newMemberList[newMemberList.length - 1] = member;
        memberList = newMemberList;
    }

    public void generateNota() {
        System.out.println("Masukan paket laundry:");
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
        String paket = in.nextLine();
        System.out.println("Masukan berat cucian anda [Kg]:");
        int berat = in.nextInt(); in.nextLine();
        if (berat < 2) {
            berat = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }
        System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?");
        System.out.println("Hanya tambah 1000 / kg :0");
        System.out.print("[Ketik x untuk tidak mau]: ");
        boolean disetrika = !in.nextLine().equals("x");
        System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!");
        System.out.println("Cuma 2000 / 4kg, kemudian 500 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        boolean diantar = !in.nextLine().equals("x");

        Nota nota = new Nota(loginMember, berat, paket, fmt.format(cal.getTime()));
        if (disetrika) nota.addService(new SetrikaService());
        if (diantar) nota.addService(new AntarService());
        loginMember.addNota(nota);
        NotaManager.addNota(nota);
        System.out.println("Nota berhasil dibuat!");
    }

    public void displayNota() {
        for (Nota nota : loginMember.getNotaList()) {
            System.out.println(nota);
        }
    }
}