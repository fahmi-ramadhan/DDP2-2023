package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;
    
    public Member(String nama, String noHp) {
        this.nama = nama;
        this.noHp = noHp;
        this.id = NotaGenerator.generateId(this.nama, this.noHp);
    }

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
