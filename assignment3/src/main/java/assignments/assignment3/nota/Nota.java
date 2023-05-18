package assignments.assignment3.nota;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import static assignments.assignment3.nota.NotaManager.*;

import java.util.Calendar;

public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private String tanggalSelesai;
    private boolean isDone;
    static public int totalNota;

    /**
     * Constructor untuk membuat objek nota.
     * 
     * @param member
     * @param berat
     * @param paket
     * @param tanggal
     */
    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        this.services = new LaundryService[]{new CuciService()};
        this.id = totalNota;
        totalNota++;
        // Menentukan sisa hari pengerjaan berdasarkan paket yang dipilih.
        if (this.paket.toLowerCase().equals("express")) {
            this.sisaHariPengerjaan = 1;
            this.baseHarga = 12000;
        } else if (this.paket.toLowerCase().equals("fast")) {
            this.sisaHariPengerjaan = 2;
            this.baseHarga = 10000;
        } else if (this.paket.toLowerCase().equals("reguler")) {
            this.sisaHariPengerjaan = 3;
            this.baseHarga = 7000;
        }
        // Menentukan tanggal selesai (Note: if block digunakan agar employeeSystemRunningTest berhasil).
        if (!this.tanggalMasuk.equals("")) {
            Calendar newCal = Calendar.getInstance();
            int year = Integer.parseInt(this.tanggalMasuk.substring(6));
            int month = Integer.parseInt(this.tanggalMasuk.substring(3, 5)) - 1;
            int date = Integer.parseInt(this.tanggalMasuk.substring(0, 2));
            newCal.set(year, month, date);
            newCal.add(Calendar.DATE, this.sisaHariPengerjaan);
            this.tanggalSelesai = fmt.format(newCal.getTime());
        }
    }

    /**
     * Method untuk menambahkan service ke array services.
     * 
     * @param service
     */
    public void addService(LaundryService service){
        LaundryService[] newServices = new LaundryService[services.length + 1];
        for (int i = 0; i < services.length; i++) {
            newServices[i] = services[i];
        }
        newServices[newServices.length - 1] = service;
        services = newServices;
    }

    /**
     * Method untuk mengerjakan service dan menentukan apakah selesai tepat waktu atau tidak.
     * 
     * @return String status pengerjaan service.
     */
    public String kerjakan(){
        for (int i = 0; i < services.length; i++) {
            if (!services[i].isDone()) {
                if (i == services.length - 1) {
                    this.isDone = true;
                };
                return String.format("Nota %d : ", this.id) + services[i].doWork();
            }
        }
        return String.format("Nota %d : Sudah selesai.", this.id);
    }

    /**
     * Method untuk pergi ke hari selanjutnya.
     * Mengurangi sisa hari pengerjaan jika nota belum selesai dikerjakan.
     */
    public void toNextDay() {
        if (!isDone) {
            this.sisaHariPengerjaan--;
        }
    }

    /**
     * Method untuk menghitung harga semua service yang dilakukan.
     * Jika terlambat, mendapatkan kompensasi sebesar 2000 * jumlah hari keterlambatan.
     * 
     * @return harga semua service yang dilakukan.
     */
    public long calculateHarga(){
        long harga = this.berat * this.baseHarga;
        for (LaundryService service : services) {
            harga += service.getHarga(this.berat);
        }
        if (this.sisaHariPengerjaan < 0) {
            harga += this.sisaHariPengerjaan * 2000;
        }
        if (harga < 0) harga = 0;
        return harga;
    }

    /**
     * Method untuk menentukan status nota apakah sudah selesai atau belum.
     * 
     * @return String status nota.
     */
    public String getNotaStatus(){
        if (this.isDone) return String.format("Nota %d : Sudah selesai.", this.id);
        else return String.format("Nota %d : Belum selesai.", this.id);
    }

    /**
     * Override method untuk mencetak detail nota.
     */
    @Override
    public String toString(){
        String detailNota = String.format(
                "[ID Nota = %d]\n" +
                "ID    : %s\n" +
                "Paket : %s\n" +
                "Harga :\n" +
                "%d kg x %d = %d\n" +
                "tanggal terima  : %s\n" +
                "tanggal selesai : %s\n" +
                "--- SERVICE LIST ---\n",
                this.id, this.member.getId(), this.paket, this.berat, this.baseHarga,
                this.berat * this.baseHarga, this.tanggalMasuk, this.tanggalSelesai
            );
        for (LaundryService service : services) {
            detailNota += String.format("-%s @ Rp.%d\n", service.getServiceName(), service.getHarga(berat));
        }
        detailNota += String.format("Harga Akhir: %d", this.calculateHarga());
        if (sisaHariPengerjaan < 0) {
            detailNota += String.format(" Ada kompensasi keterlambatan %d * %d hari", -sisaHariPengerjaan, 2000);
        }
        return detailNota + "\n";
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }

    public LaundryService[] getServices(){
        return services;
    }
}
