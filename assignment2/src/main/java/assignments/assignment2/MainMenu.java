package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<Nota>();
    private static ArrayList<Member> memberList = new ArrayList<Member>();

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() {
        // Meminta input nama dan nomor HP
        System.out.println("Masukkan nama Anda:");
        String nama = input.nextLine().trim();
        System.out.println("Masukkan nomor handphone Anda:");
        String nomorHP;
        while (true) {
            nomorHP = input.nextLine().trim();
            if (nomorHP.matches("\\d+")) break;
            else System.out.println("Field nomor hp hanya menerima digit.");
        }
        // Inisialisasi variabel untuk menentukan apakah harus membuat user atau tidak
        boolean shouldGenerateUser = true;
        // Mengecek apakah member sudah ada di sistem atau belum
        String memberId = generateId(nama, nomorHP);
        for (Member member : memberList) {
            if (member.getId().equals(memberId)) {
                System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n", member.getNama(), nomorHP);
                shouldGenerateUser = false;
                break;
            }
        }
        // Membuat user dan menambahkannya ke dalam memberList
        if (shouldGenerateUser) {
            Member member = new Member(nama, nomorHP);
            memberList.add(member);
            System.out.printf("Berhasil membuat member dengan ID %s!\n", member.getId());
        }
    }

    private static void handleGenerateNota() {
        // Mencari member berdasarkan id yang diinput
        System.out.println("Masukkan ID member:");
        Member member = null;
        String id = input.nextLine().trim();
        for (Member memberx : memberList) {
            if (memberx.getId().equals(id)) {
                member = memberx;
            }
        }
        // Jika member ditemukan, minta detail tambahan untuk membuat nota
        if (member != null) {
            String paket;
            while (true) {
                // Meminta input paket laundry
                System.out.println("Masukkan paket laundry:");
                paket = input.nextLine().trim();
                if (paket.equals("?")) {
                    showPaket();  // Menampilkan paket yang tersedia
                } else if (!paket.equalsIgnoreCase("express") &&
                            !paket.equalsIgnoreCase("fast") &&
                            !paket.equalsIgnoreCase("reguler")) {
                    // Jika paket tidak diketahui, tampilkan panduan untuk mencari tahu jenis paket
                    System.out.printf("Paket %s tidak diketahui\n", paket);
                    System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                } else {
                    break;  // Jika input paket valid, keluar dari loop
                }
            }
            int berat;
            while (true) {
                // Meminta input berat cucian
                System.out.println("Masukkan berat cucian Anda [Kg]:");
                String beratCucian = input.nextLine().trim();
                if (beratCucian.matches("\\d+")) {
                    berat = Integer.parseInt(beratCucian);
                    if (berat == 1) {  // Jika berat cucian kurang dari 2k kg (1 kg), maka akan dianggap sebagai 2 kg
                        System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                        berat = 2;
                        break;
                    } else if (berat == 0) {  // Jika berat cucian adalah 0 kg, maka kembali ke awal loop
                        System.out.println("Berat harus berupa bilangan bulat positif");
                        continue;
                    } else {
                        break;
                    }
                }
                else System.out.println("Berat harus berupa bilangan bulat positif");
            }
            // Membuat nota, menambahkan ke notaList, serta mencetak notanya
            Nota nota = new Nota(member, paket, berat, fmt.format(cal.getTime()));
            notaList.add(nota);  
            nota.cetakNota();
        }
        // Jika member tidak ditemukan
        else {
            System.out.printf("Member dengan ID %s tidak ditemukan!\n", id);
        }
    }

    private static void handleListNota() {
        // Mencetak status semua nota yang ada di notaList
        System.out.printf("Terdaftar %d nota dalam sistem.\n", notaList.size());
        for (Nota nota : notaList) {
            if (nota.isReady()) {
                System.out.printf("- [%d] Status      	: Sudah dapat diambil!\n", nota.getIdNota());
            } else {
                System.out.printf("- [%d] Status      	: Belum bisa diambil :(\n", nota.getIdNota());
            }
        }
    }

    private static void handleListUser() {
        // Mencetak semua member yang ada dalam sistem
        System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
        for (Member member : memberList) {
            System.out.printf("- %s : %s\n", member.getId(), member.getNama());
        }
    }

    private static void handleAmbilCucian() {
        // Meminta input id nota yang akan diambil
        System.out.println("Masukkan ID nota yang akan diambil:");
        String idNota;
        while (true) {
            idNota = input.nextLine().trim();
            if (idNota.matches("\\d+")) break;
            else System.out.println("ID nota berbentuk angka!");
        }
        // Mencari nota berdasarkan id nota yang diinput
        boolean notaHasFound = false;
        for (Nota nota : notaList) {
            if (nota.getIdNota() == Integer.parseInt(idNota)) {
                // jika nota sudah bisa diambil, hapus nota dari notaList
                if (nota.isReady()) {
                    notaList.remove(nota);
                    System.out.printf("Nota dengan ID %s berhasil diambil!\n", idNota);
                } else {
                    System.out.printf("Nota dengan ID %s gagal diambil!\n", idNota);
                }
                notaHasFound = true;
                break;
            }
        }
        // Jika nota tidak ditemukan
        if (!notaHasFound) {
            System.out.printf("Nota dengan ID %s tidak ditemukan!\n", idNota);
        }
    }

    private static void handleNextDay() {
        // menambahkan satu hari ke kalender dan mengurangi sisa waktu pengerjaan setiap cucian
        cal.add(Calendar.DATE, 1);
        for (Nota nota : notaList) {
            nota.decrementSisaHariPengerjaan();
        }
        System.out.println("Dek Depe tidur hari ini... zzz...");
        // Mencetak setiap nota yang sudah dapat diambil
        for (Nota nota : notaList) {
            if (nota.isReady()) {
                System.out.printf("Laundry dengan nota ID %s sudah dapat diambil!\n", nota.getIdNota());
            }
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    private static void printMenu() {
        // Mencetak pesan selamat datang, tanggal, dan pilihan menu
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }
}
