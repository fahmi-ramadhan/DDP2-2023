package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;
    
    public Member(String nama, String noHp) {
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.noHp = noHp;
        this.id = NotaGenerator.generateId(this.nama, this.noHp);
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public String getNoHp() {
        return this.noHp;
    }

    public void incrementBonusCounter() {
        this.bonusCounter++;
    }

    public int getBonusCounter() {
        return this.bonusCounter;
    }
}
