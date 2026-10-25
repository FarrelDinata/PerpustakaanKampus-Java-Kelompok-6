/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;
import model.Buku;
import model.Transaksi;
import service.AntreanPeminjam;
import service.KoleksiBuku;
import service.RiwayatTransaksi;
import service.StatistikPeminjaman;
/**
 *
 * @author DELL
 */
public class Main {
    static Scanner sc = new Scanner(System.in);
    static StatistikPeminjaman statistik = new StatistikPeminjaman();
    static KoleksiBuku koleksi = new KoleksiBuku();
    static RiwayatTransaksi riwayat = new RiwayatTransaksi();
    static AntreanPeminjam antrean = new AntreanPeminjam();
 
    static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }
 
    public static void main(String[] args) {
        int pilih;
        do {
            System.out.println("\n===== SISTEM MANAJEMEN PERPUSTAKAAN KAMPUS =====");
            System.out.println("1. Lihat Kategori Buku (Array 1D)");
            System.out.println("2. Statistik Peminjaman (Array 2D)");
            System.out.println("3. Peta Lokasi Penyimpanan (Array 3D)");
            System.out.println("4. Kelola Buku (ArrayList)");
            System.out.println("5. Riwayat Transaksi (Stack)");
            System.out.println("6. Antrean Peminjam (Queue)");
            System.out.println("0. Keluar");
            pilih = bacaInt("Pilih menu: ");
 
            switch (pilih) {
                case 1:
                    System.out.println("\n--- Daftar Kategori Buku ---");
                    statistik.tampilKategori();
                    break;
                case 2:
                    System.out.println("\n--- Jumlah Peminjaman per Kategori per Hari ---");
                    statistik.tampilTabel();
                    break;
                case 3:
                    System.out.println("\n--- Peta Lokasi Penyimpanan ('-' = kosong) ---");
                    koleksi.tampilPeta();
                    break;
                case 4: menuBuku(); break;
                case 5: menuRiwayat(); break;
                case 6: menuAntrean(); break;
                case 0: System.out.println("Terima kasih!"); break;
                default: System.out.println("Menu tidak valid!");
            }
        } while (pilih != 0);
    }
 
    // ===== ArrayList =====
    static void menuBuku() {
        System.out.println("\n--- Kelola Buku ---");
        System.out.println("1. Tambah buku");
        System.out.println("2. Tampilkan semua buku");
        System.out.println("3. Hapus buku");
        int p = bacaInt("Pilih: ");
        switch (p) {
            case 1: tambahBuku(); break;
            case 2: koleksi.tampilBuku(); break;
            case 3: hapusBuku(); break;
            default: System.out.println("Pilihan tidak valid!");
        }
    }
 
    static void tambahBuku() {
        System.out.print("Judul buku: ");
        String judul = sc.nextLine().trim();
        if (judul.isEmpty()) {
            System.out.println("Judul tidak boleh kosong!");
            return;
        }
        statistik.tampilKategori();
        int k = bacaInt("Pilih kategori (nomor): ") - 1;
        if (k < 0 || k >= StatistikPeminjaman.KATEGORI.length) {
            System.out.println("Kategori tidak valid!");
            return;
        }
        Buku b = koleksi.tambah(judul, k);
        if (b == null) {
            System.out.println("Rak penuh! Tidak ada lokasi kosong.");
        } else {
            System.out.println("Buku ditambahkan (ID " + b.getId() + ") di lokasi " + b.getLokasi());
        }
    }
 
    static void hapusBuku() {
        if (koleksi.isEmpty()) {
            System.out.println("Belum ada buku.");
            return;
        }
        koleksi.tampilBuku();
        int id = bacaInt("ID buku yang dihapus: ");
        Buku b = koleksi.cari(id);
        if (b == null) {
            System.out.println("Buku tidak ditemukan!");
        } else if (b.isDipinjam()) {
            System.out.println("Buku sedang dipinjam, tidak dapat dihapus!");
        } else {
            koleksi.hapus(b);
            System.out.println("Buku \"" + b.getJudul() + "\" dihapus.");
        }
    }
 
    // ===== Stack =====
    static void menuRiwayat() {
        System.out.println("\n--- Riwayat Transaksi ---");
        System.out.println("1. Lihat semua riwayat (terbaru di atas)");
        System.out.println("2. Lihat transaksi terakhir");
        System.out.println("3. Batalkan transaksi terakhir");
        System.out.println("4. Kembalikan buku (catat transaksi)");
        int p = bacaInt("Pilih: ");
        switch (p) {
            case 1: riwayat.tampilSemua(); break;
            case 2: lihatTerakhir(); break;
            case 3: batalkanTerakhir(); break;
            case 4: kembalikanBuku(); break;
            default: System.out.println("Pilihan tidak valid!");
        }
    }
 
    static void lihatTerakhir() {
        Transaksi t = riwayat.lihatTerakhir();
        if (t == null) {
            System.out.println("Riwayat kosong.");
        } else {
            System.out.println("Transaksi terakhir: " + t);
        }
    }
 
    static void batalkanTerakhir() {
        Transaksi t = riwayat.batalkan();
        if (t == null) {
            System.out.println("Tidak ada transaksi untuk dibatalkan.");
            return;
        }
        Buku b = t.getBuku();
        if (t.getJenis().equals("PINJAM")) {
            b.setDipinjam(false);
            statistik.kurangi(b.getKategori(), t.getHari());
        } else {
            b.setDipinjam(true);
        }
        System.out.println("Transaksi dibatalkan: " + t);
    }
 
    static void kembalikanBuku() {
        if (koleksi.isEmpty()) {
            System.out.println("Belum ada buku.");
            return;
        }
        koleksi.tampilBuku();
        int id = bacaInt("ID buku yang dikembalikan: ");
        Buku b = koleksi.cari(id);
        if (b == null) {
            System.out.println("Buku tidak ditemukan!");
        } else if (!b.isDipinjam()) {
            System.out.println("Buku ini tidak sedang dipinjam.");
        } else {
            System.out.print("Nama mahasiswa: ");
            String nama = sc.nextLine().trim();
            b.setDipinjam(false);
            riwayat.catat(new Transaksi("KEMBALI", nama, b, StatistikPeminjaman.hariIni()));
            System.out.println("Buku dikembalikan dan dicatat di riwayat.");
        }
    }
 
    // ===== Queue =====
    static void menuAntrean() {
        System.out.println("\n--- Antrean Peminjam ---");
        System.out.println("1. Tambah mahasiswa ke antrean");
        System.out.println("2. Lihat antrean");
        System.out.println("3. Layani antrean terdepan (proses peminjaman)");
        int p = bacaInt("Pilih: ");
        switch (p) {
            case 1: tambahAntrean(); break;
            case 2: antrean.tampil(); break;
            case 3: layaniAntrean(); break;
            default: System.out.println("Pilihan tidak valid!");
        }
    }
 
    static void tambahAntrean() {
        System.out.print("Nama mahasiswa: ");
        String nama = sc.nextLine().trim();
        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong!");
            return;
        }
        antrean.tambah(nama);
        System.out.println(nama + " masuk antrean (posisi " + antrean.ukuran() + ").");
    }
 
    static void layaniAntrean() {
        if (antrean.isEmpty()) {
            System.out.println("Antrean kosong.");
            return;
        }
        if (koleksi.isEmpty()) {
            System.out.println("Belum ada buku untuk dipinjam.");
            return;
        }
        System.out.println("Melayani: " + antrean.lihatDepan());
        koleksi.tampilBuku();
        int id = bacaInt("ID buku yang dipinjam: ");
        Buku b = koleksi.cari(id);
        if (b == null) {
            System.out.println("Buku tidak ditemukan!");
        } else if (b.isDipinjam()) {
            System.out.println("Buku sedang dipinjam. Mahasiswa tetap di antrean.");
        } else {
            String nama = antrean.layani();
            int h = StatistikPeminjaman.hariIni();
            b.setDipinjam(true);
            statistik.tambah(b.getKategori(), h);
            riwayat.catat(new Transaksi("PINJAM", nama, b, h));
            System.out.println(nama + " berhasil meminjam \"" + b.getJudul() + "\".");
        }
    }
}
 