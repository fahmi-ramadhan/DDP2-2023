package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    private static int idNotaCounter;
    private int idNota;
    private String paket;
    private Member member;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;

    // TODO: tambahkan attributes yang diperlukan untuk class ini
    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        // TODO: buat constructor untuk class ini
        this.idNota = idNotaCounter;
        this.paket = paket;
        this.member = member;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.isReady = false;
        this.member.incrementBonusCounter();
        idNotaCounter++;
        if (this.paket.toLowerCase().equals("express")) {
            this.sisaHariPengerjaan = 1;
        } else if (this.paket.toLowerCase().equals("fast")) {
            this.sisaHariPengerjaan = 2;
        } else if (this.paket.toLowerCase().equals("reguler")) {
            this.sisaHariPengerjaan = 3;
        }
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    public Member getMember() {
        return this.member;
    }

    public String getTanggalMasuk() {
        return this.tanggalMasuk;
    }

    public int getIdNota() {
        return this.idNota;
    }

    public boolean isReady() {
        if (this.sisaHariPengerjaan == 0) {
            this.isReady = true;
        }
        return this.isReady;
    }

    public int getSisaHariPengerjaan() {
        return this.sisaHariPengerjaan;
    }

    public void decrementSisaHariPengerjaan() {
        this.sisaHariPengerjaan--;
    }

    public void cetakNota() {
        System.out.println("Berhasil menambahkan nota!");
        System.out.printf("[ID Nota = %d]\n", idNota);
        System.out.println(NotaGenerator.generateNota(member.getId(), paket, berat, tanggalMasuk, member.getBonusCounter()));
        System.out.printf("Status      	: Belum bisa diambil :(\n");
    }
}
