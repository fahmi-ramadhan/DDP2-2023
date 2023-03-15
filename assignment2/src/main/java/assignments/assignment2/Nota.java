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

    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        this.idNota = idNotaCounter;
        this.paket = paket;
        this.member = member;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.isReady = false;
        // Increment id nota counter dan member bonus counter setiap kali membuat nota
        this.member.incrementBonusCounter();
        idNotaCounter++;
        // Menentukan sisa hari pengerjaan berdasarkan paket yang dipilih
        if (this.paket.toLowerCase().equals("express")) {
            this.sisaHariPengerjaan = 1;
        } else if (this.paket.toLowerCase().equals("fast")) {
            this.sisaHariPengerjaan = 2;
        } else if (this.paket.toLowerCase().equals("reguler")) {
            this.sisaHariPengerjaan = 3;
        }
    }

    public Member getMember() {
        return this.member;
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

    public void decrementSisaHariPengerjaan() {
        this.sisaHariPengerjaan--;
    }

    public void cetakNota() {
        // Mencetak nota menggunakan method generateNota TP1 yang sudah dimodifikasi
        System.out.println("Berhasil menambahkan nota!");
        System.out.printf("[ID Nota = %d]\n", idNota);
        System.out.println(NotaGenerator.generateNota(this.member.getId(), this.paket, this.berat,
            this.tanggalMasuk, this.member.getBonusCounter()));
        System.out.printf("Status      	: Belum bisa diambil :(\n");
    }
}
