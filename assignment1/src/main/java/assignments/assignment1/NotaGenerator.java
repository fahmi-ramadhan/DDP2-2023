package assignments.assignment1;

// Import modul
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        while (true) {
            // Mencetak menu yang tersedia dan meminta input menu yang dipilih
            printMenu();
            System.out.print("Pilihan : ");
            String pilihanMenu = input.nextLine();
            System.out.println("================================");
            // Keluar dari program ketika input menu yang dipilih adalah 0
            if (pilihanMenu.equals("0")) {
                System.out.println("Terima kasih telah menggunakan NotaGenerator!");
                break;
            }
            
            if (pilihanMenu.equals("1") || pilihanMenu.equals("2")) {
                // Meminta input nama dan nomor HP untuk membuat ID
                System.out.println("Masukkan nama Anda:");
                String nama = input.nextLine();
                System.out.println("Masukkan nomor handphone Anda:");
                String nomorHP;
                while (true) {
                    nomorHP = input.nextLine();
                    if (nomorHP.matches("\\d+")) break;
                    else System.out.println("Nomor hp hanya menerima digit");
                }
                String id = generateId(nama, nomorHP);

                if (pilihanMenu.equals("1")) {
                    // Jika input menu yang dipilih adalah 1, tampilkan ID-nya
                    System.out.println("ID Anda : " + id);
                } else if (pilihanMenu.equals("2")) {
                    // Jika input menu yang dipilih adalah 2, minta detail tambahan dan buatkan notanya
                    System.out.println("Masukkan tanggal terima:");
                    String tanggalTerima = input.nextLine();
                    String paket;
                    while (true) {
                        // Meminta input paket laundry
                        System.out.println("Masukkan paket laundry:");
                        paket = input.nextLine();
                        if (paket.equals("?")) {
                            showPaket();  // Menampilkan paket yang tersedia
                        }
                        else if (!paket.equalsIgnoreCase("express") &&
                                 !paket.equalsIgnoreCase("fast") &&
                                 !paket.equalsIgnoreCase("reguler")) {
                            // Jika paket tidak diketahui, tampilkan panduan untuk mencari tahu jenis paket
                            System.out.printf("Paket %s tidak diketahui\n", paket);
                            System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                        } else {
                            break;  // Jika input paket valid, keluar dari loop
                        }
                    }
                    
                    // Meminta input berat cucian dalam kilogram dan memastikan input berupa bilangan bulat positif
                    System.out.println("Masukkan berat cucian Anda [Kg]:");
                    int berat;
                    while (true) {
                        String beratCucian = input.nextLine();
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
                    
                    // Membuat nota dan mencetaknya
                    System.out.println("Nota Laundry");
                    System.out.println(generateNota(id, paket, berat, tanggalTerima, 1));
                } 
            } else {
                // Jika input menu yang dipilih tidak valid, tampilkan pesan dan kembali ke awal loop
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
    }

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket.
     */
    public static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP){
        // Membuat string gabungan nama depan dan nomor HP untuk menghitung checksum
        if (nama.contains(" ")) nama = nama.split(" ")[0];
        String namaDanNomorHP = String.format("%s-%s", nama.toUpperCase(), nomorHP);
        // Menghitung checksum
        int checksum = 0;
        for (char ch : namaDanNomorHP.toCharArray()) {
            if (Character.isLetter(ch)) {
                checksum += ch - 'A' + 1;
            } else if (Character.isDigit(ch)) {
                checksum += Character.getNumericValue(ch);
            } else {
                checksum += 7;
            }
        }
        // Mengembalikan ID
        return String.format("%s-%02d", namaDanNomorHP, checksum % 100);
    }

    /**
     * Method untuk membuat Nota.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */

    public static String generateNota(String id, String paket, int berat, String tanggalTerima, int bonusCounter) {
        // Inisialisasi variabel
        int harga = 0;
        int totalHarga = 0;
        int lamaHariPaket = 0;
        
        // Menentukan harga dan waktu cucian berdasarkan paket yang dipilih
        if (paket.toLowerCase().equals("express")) {
            harga = 12000;
            totalHarga = harga * berat;
            lamaHariPaket = 1;
        } else if (paket.toLowerCase().equals("fast")) {
            harga = 10000;
            totalHarga = harga * berat;
            lamaHariPaket = 2;
        } else if (paket.toLowerCase().equals("reguler")) {
            harga = 7000;
            totalHarga = harga * berat;
            lamaHariPaket = 3;
        }

        String kalkulasiHarga;
        if (bonusCounter % 3 == 0) {
            kalkulasiHarga = String.format("%d kg x %d = %d = %d (Discount member 50%%!!!)",
            berat, harga, totalHarga, totalHarga / 2);
        } else {
            kalkulasiHarga = String.format("%d kg x %d = %d", berat, harga, totalHarga);
        }

        // Menghitung tanggal selesai cucian
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(tanggalTerima, formatter);
        LocalDate newDate = date.plusDays(lamaHariPaket);
        String tanggalSelesai = newDate.format(formatter);
        // Mengembalikan nota laundry
        return String.format(
                "ID    : %s\n" +
                "Paket : %s\n" +
                "Harga :\n" +
                "%s\n" +
                "Tanggal Terima  : %s\n" +
                "Tanggal Selesai : %s",
                id, paket, kalkulasiHarga, tanggalTerima, tanggalSelesai);
    }
}
