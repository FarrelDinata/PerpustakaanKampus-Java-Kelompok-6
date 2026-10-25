# LAPORAN PRAKTIKUM & DOKUMENTASI SISTEM MANAJEMEN PERPUSTAKAAN KAMPUS

> *Tugas Pemrograman Berbasis Objek*
> 
> **Mata Kuliah:** Pemrograman Berorientasi Objek
>
> **Dosen Pengampu:** M. Ibadurrahman Arrasyid Supriyanto M.Kom.
> 
> **Program Studi:** Sistem Informasi
> 
> **Angkatan:** 2025
>
> **Kelompok:** 6
> 
> Implementasi Integrasi 6 Struktur Data Utama dalam Sistem Manajemen Perpustakaan.

---

## Identitas Kelompok

**Anggota Kelompok:**
  1. **Claudya Yusfa Ariyani** - `2509116043`
  2. **Aji Rifqi Suryana** - `2509116054`
  3. **Muhammad Dzikri Andhika** - `2509116103`
  4. **Farrel Fazzadinata Sumarna** - `2509116111`

---

## 1. Deskripsi Studi Kasus & Tujuan

### 1.1 Deskripsi Studi Kasus
Perpustakaan kampus membutuhkan sistem informasi interaktif berbasis Java untuk mengelola berbagai aktivitas operasional. Operasional tersebut meliputi pengelompokan jenis buku, pencatatan statistik peminjaman harian, pemetaan lokasi fisik penyimpanan buku di dalam rak perpustakaan, manajemen katalog buku secara dinamis, pencatatan riwayat transaksi dengan fitur pembatalan (*undo*), serta pengelolaan antrean mahasiswa yang ingin meminjam buku.

### 1.2 Tujuan Pembuatan Program
1. Mengintegrasikan 6 struktur data fundamental (**Array 1D, Array 2D, Array 3D, ArrayList, Stack, dan Queue**) ke dalam satu aplikasi berbasis objek (*Object-Oriented Programming*).
2. Menyediakan antarmuka interaktif berbasis teks (*CLI*) menggunakan kelas `java.util.Scanner`.
3. Memudahkan pustakawan dalam melayani peminjaman, pengembalian, pemantauan stok lokasi, dan pembatalan transaksi secara efisien.

---

## 2. Penerapan 6 Struktur Data

| No | Struktur Data | Implementasi dalam Program | Fungsi & Manfaat |
| :-: | --- | --- | --- |
| 1 | **Array 1D** | `KATEGORI[]` & `HARI[]` | Menyimpan daftar label kategori buku dan nama hari secara statis. |
| 2 | **Array 2D** | `data[kategori][hari]` | Menyimpan matriks jumlah frekuensi peminjaman buku berdasarkan kategori dan hari. |
| 3 | **Array 3D** | `lokasi[lantai][rak][posisi]` | Representasi fisik 3-dimensi lokasi penyimpanan buku (`3 Lantai x 4 Rak x 5 Posisi`). |
| 4 | **ArrayList** | `daftar` (pada `KoleksiBuku`) | Menyimpan katalog objek `Buku` secara dinamis (tambah, tampilkan, hapus). |
| 5 | **Stack (LIFO)** | `riwayat` (pada `RiwayatTransaksi`) | Menyimpan rekam jejak transaksi peminjaman/pengembalian dengan fitur *Undo*. |
| 6 | **Queue (FIFO)** | `antrean` (pada `AntreanPeminjam`) | Mengelola barisan antrean mahasiswa yang akan melayani transaksi peminjaman buku. |

---

## 3. Struktur Kode & Penjelasan Detail per File

### 3.1 `Buku.java`
Mengemas data entitas buku beserta properti lokasinya.

```java
public class Buku {
    private int id;
    private String judul;
    private int kategori;   // Indeks Array 1D Kategori
    private int lantai, rak, posisi; // Indeks Array 3D Lokasi
    private boolean dipinjam = false;

    public Buku(int id, String judul, int kategori, int lantai, int rak, int posisi) {
        this.id = id;
        this.judul = judul;
        this.kategori = kategori;
        this.lantai = lantai;
        this.rak = rak;
        this.posisi = posisi;
    }

    public int getId() { return id; }
    public String getJudul() { return judul; }
    public int getKategori() { return kategori; }
    public int getLantai() { return lantai; }
    public int getRak() { return rak; }
    public int getPosisi() { return posisi; }
    public boolean isDipinjam() { return dipinjam; }
    public void setDipinjam(boolean dipinjam) { this.dipinjam = dipinjam; }

    // Mengembalikan format string lokasi fisik: Contoh L1-R2-P3
    public String getLokasi() {
        return "L" + (lantai + 1) + "-R" + (rak + 1) + "-P" + (posisi + 1);
    }
}
```

* **Penjelasan Baris Demi Baris:**
  * `private int id, String judul, int kategori, int lantai, rak, posisi`: Atribut enkapsulasi untuk menyimpan metadata buku dan lokasi indeksnya.
  * `public Buku(...)`: Konstruktor untuk inisialisasi awal saat buku ditambahkan.
  * `getLokasi()`: Mengonversi indeks berbasis 0 (`lantai`, `rak`, `posisi`) menjadi format teks yang mudah dibaca user (berbasis 1).

---

### 3.2 `StatistikPeminjaman.java` (Array 1D & Array 2D)
Mengelola statistik peminjaman menggunakan Array 1D (label) dan Array 2D (frekuensi).

```java
import java.time.LocalDate;

public class StatistikPeminjaman {
    // Array 1D: Label Statis Kategori dan Hari
    public static final String[] KATEGORI =
            {"Pemrograman", "Matematika", "Jaringan Komputer", "Basis Data"};
    public static final String[] HARI =
            {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};

    // Array 2D: Rows (Kategori) x Columns (Hari)
    private int[][] data = new int[KATEGORI.length][HARI.length];

    public static int hariIni() {
        return LocalDate.now().getDayOfWeek().getValue() - 1; // Senin = 0, Minggu = 6
    }

    public void tambah(int kategori, int hari) {
        data[kategori][hari]++;
    }

    public void kurangi(int kategori, int hari) {
        if (data[kategori][hari] > 0) data[kategori][hari]--;
    }

    public void tampilKategori() {
        for (int i = 0; i < KATEGORI.length; i++) {
            System.out.println((i + 1) + ". " + KATEGORI[i]);
        }
    }

    public void tampilTabel() {
        System.out.printf("%-20s", "Kategori");
        for (String h : HARI) System.out.printf("%-8s", h);
        System.out.printf("%-6s%n", "Total");

        for (int i = 0; i < KATEGORI.length; i++) {
            System.out.printf("%-20s", KATEGORI[i]);
            int total = 0;
            for (int j = 0; j < HARI.length; j++) {
                System.out.printf("%-8d", data[i][j]);
                total += data[i][j];
            }
            System.out.printf("%-6d%n", total);
        }
    }
}
```

* **Penjelasan Baris Demi Baris:**
  * `public static final String[] KATEGORI`: **Array 1D** penyimpan daftar nama kategori buku.
  * `public static final String[] HARI`: **Array 1D** penyimpan nama-nama hari.
  * `private int[][] data`: **Array 2D** yang menyimpan angka jumlah peminjaman per baris (kategori) dan per kolom (hari).
  * `tambah()` / `kurangi()`: Menambah atau mengurangi counter pada sel matriks `data[kategori][hari]`.
  * `tampilTabel()`: Menggunakan nested loop (`for` bertingkat) untuk mencetak matriks 2D ke dalam bentuk tabel rekap statistik beserta kalkulasi total per baris.

---

### 3.3 `KoleksiBuku.java` (ArrayList & Array 3D)
Mengelola koleksi dinamis dengan ArrayList dan pemetaan ruang dengan Array 3D.

```java
import java.util.ArrayList;

public class KoleksiBuku {
    public static final int LANTAI = 3, RAK = 4, POSISI = 5;

    // Array 3D: [Lantai][Rak][Posisi] berisi Judul Buku (null jika kosong)
    private String[][][] lokasi = new String[LANTAI][RAK][POSISI];

    // ArrayList: Menampung objek Buku secara dinamis
    private ArrayList<Buku> daftar = new ArrayList<>();
    private int nextId = 1;

    public boolean isEmpty() { return daftar.isEmpty(); }

    public Buku tambah(String judul, int kategori) {
        // Mencari slot kosong pada Array 3D
        for (int l = 0; l < LANTAI; l++) {
            for (int r = 0; r < RAK; r++) {
                for (int p = 0; p < POSISI; p++) {
                    if (lokasi[l][r][p] == null) {
                        Buku b = new Buku(nextId++, judul, kategori, l, r, p);
                        daftar.add(b);               // Masukkan ke ArrayList
                        lokasi[l][r][p] = judul;     // Tandai pada Array 3D
                        return b;
                    }
                }
            }
        }
        return null; // Rak Penuh
    }

    public Buku cari(int id) {
        for (Buku b : daftar) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    public void hapus(Buku b) {
        lokasi[b.getLantai()][b.getRak()][b.getPosisi()] = null; // Kosongkan Array 3D
        daftar.remove(b);                                       // Hapus dari ArrayList
    }

    public void tampilBuku() {
        if (daftar.isEmpty()) {
            System.out.println("Belum ada buku.");
            return;
        }
        System.out.printf("%-4s %-30s %-20s %-10s %-10s%n",
                "ID", "Judul", "Kategori", "Lokasi", "Status");
        for (Buku b : daftar) {
            System.out.printf("%-4d %-30s %-20s %-10s %-10s%n",
                    b.getId(), b.getJudul(), StatistikPeminjaman.KATEGORI[b.getKategori()],
                    b.getLokasi(), b.isDipinjam() ? "Dipinjam" : "Tersedia");
        }
    }

    public void tampilPeta() {
        for (int l = 0; l < LANTAI; l++) {
            System.out.println("Lantai " + (l + 1));
            for (int r = 0; r < RAK; r++) {
                System.out.print("  Rak " + (r + 1) + ": ");
                for (int p = 0; p < POSISI; p++) {
                    String isi = lokasi[l][r][p];
                    System.out.print("[" + (isi == null ? "-" : isi) + "] ");
                }
                System.out.println();
            }
        }
    }
}
```

* **Penjelasan Baris Demi Baris:**
  * `private String[][][] lokasi`: **Array 3D** yang berfungsi menggambarkan tata letak fisik rak perpustakaan.
  * `private ArrayList<Buku> daftar`: **ArrayList** untuk penanganan data buku yang ukurannya fleksibel saat penambahan/penghapusan.
  * `tambah()`: Menggunakan 3 tingkat `for-loop` untuk menyisir sel Array 3D yang bernilai `null`. Jika ditemukan slot kosong, objek `Buku` dibuat, dimasukkan ke `ArrayList`, dan koordinatnya ditandai di Array 3D.
  * `hapus()`: Menghapus elemen dari `ArrayList` sekaligus menyetel kembali koordinat terkait pada Array 3D menjadi `null`.

---

### 3.4 `Transaksi.java` & `RiwayatTransaksi.java` (Stack)
Mengelola riwayat transaksi dengan konsep Last-In-First-Out (LIFO).

```java
import java.util.Stack;

public class Transaksi {
    private String jenis, mahasiswa;
    private Buku buku;
    private int hari;

    public Transaksi(String jenis, String mahasiswa, Buku buku, int hari) {
        this.jenis = jenis;
        this.mahasiswa = mahasiswa;
        this.buku = buku;
        this.hari = hari;
    }

    public String getJenis() { return jenis; }
    public String getMahasiswa() { return mahasiswa; }
    public Buku getBuku() { return buku; }
    public int getHari() { return hari; }

    @Override
    public String toString() {
        return jenis + " | " + mahasiswa + " | " + buku.getJudul()
                + " | " + StatistikPeminjaman.HARI[hari];
    }
}

public class RiwayatTransaksi {
    private Stack<Transaksi> riwayat = new Stack<>();

    public void catat(Transaksi t) { riwayat.push(t); }
    public boolean isEmpty() { return riwayat.isEmpty(); }

    public void tampilSemua() {
        if (riwayat.isEmpty()) {
            System.out.println("Riwayat kosong.");
            return;
        }
        for (int i = riwayat.size() - 1; i >= 0; i--) {
            System.out.println((riwayat.size() - i) + ". " + riwayat.get(i));
        }
    }

    public Transaksi lihatTerakhir() {
        return riwayat.isEmpty() ? null : riwayat.peek();
    }

    public Transaksi batalkan() {
        return riwayat.isEmpty() ? null : riwayat.pop();
    }
}
```

* **Penjelasan Baris Demi Baris:**
  * `private Stack<Transaksi> riwayat`: Struktur data **Stack** berbasis LIFO.
  * `riwayat.push(t)`: Menambahkan transaksi baru ke tumpukan paling atas.
  * `riwayat.peek()`: Melihat transaksi teratas tanpa menghapusnya.
  * `riwayat.pop()`: Mengambil sekaligus menghapus transaksi teratas (digunakan untuk fitur *undo* / pembatalan transaksi).

---

### 3.5 `AntreanPeminjam.java` (Queue)
Mengelola giliran mahasiswa dengan konsep First-In-First-Out (FIFO).

```java
import java.util.LinkedList;
import java.util.Queue;

public class AntreanPeminjam {
    private Queue<String> antrean = new LinkedList<>();

    public void tambah(String nama) { antrean.offer(nama); }
    public int ukuran() { return antrean.size(); }
    public boolean isEmpty() { return antrean.isEmpty(); }
    public String lihatDepan() { return antrean.peek(); }
    public String layani() { return antrean.poll(); }

    public void tampil() {
        if (antrean.isEmpty()) {
            System.out.println("Antrean kosong.");
            return;
        }
        int no = 1;
        for (String nama : antrean) {
            System.out.println(no++ + ". " + nama);
        }
    }
}
```

* **Penjelasan Baris Demi Baris:**
  * `private Queue<String> antrean`: **Queue** diimplementasikan menggunakan `LinkedList`.
  * `antrean.offer(nama)`: Memasukkan mahasiswa baru ke ekor antrean (enqueue).
  * `antrean.poll()`: Mengambil dan mengeluarkan mahasiswa dari kepala antrean (dequeue).

---

### 3.6 `Main.java`
Main Driver yang menyatukan seluruh logika aplikasi dalam menu interaktif berbasis `Scanner`.

```java
import java.util.Scanner;

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
                case 1: statistik.tampilKategori(); break;
                case 2: statistik.tampilTabel(); break;
                case 3: koleksi.tampilPeta(); break;
                case 4: menuBuku(); break;
                case 5: menuRiwayat(); break;
                case 6: menuAntrean(); break;
                case 0: System.out.println("Terima kasih!"); break;
                default: System.out.println("Menu tidak valid!");
            }
        } while (pilih != 0);
    }
    // ... Sub-menu interaktif ...
}
```

---

## 4. Screenshot Eksekusi Program & Penjelasan Fitur

*(Catatan: Simpan file gambar screenshot hasil eksekusi program Anda ke dalam folder `assets/` pada repositori ini agar gambar dapat otomatis tampil di preview GitHub)*

### 4.1 Menu Utama & Kategori Buku (Array 1D)


<img width="228" height="167" alt="image" src="https://github.com/user-attachments/assets/501f04db-c592-4b35-bcf9-042f9192e826" />


* **Penjelasan Eksekusi Fitur:**
  * Saat pengguna memilih **Menu 1**, sistem menjalankan method `statistik.tampilKategori()`.
  * Fitur ini menyisir elemen **Array 1D** `KATEGORI[]` dari indeks `0` hingga `3` dan menampilkan daftar bidang keilmuan buku yang tersedia di perpustakaan secara berurutan.

---

### 4.2 Matriks Statistik Peminjaman (Array 2D)


<img width="375" height="175" alt="image" src="https://github.com/user-attachments/assets/5756abf1-dbf2-4c8f-9064-6fb223b50193" />


* **Penjelasan Eksekusi Fitur:**
  * Pengguna memilih **Menu 2** untuk mencetak rekapitulasi data peminjaman harian.
  * Sistem mengakses **Array 2D** `data[kategori][hari]`, menghitung jumlah frekuensi peminjaman per sel matriks, serta melakukan akumulasi total per baris kategori secara otomatis.

---

### 4.3 Pemetaan Lokasi Penyimpanan Buku (Array 3D)


<img width="233" height="282" alt="image" src="https://github.com/user-attachments/assets/21ef3efd-651c-4b11-a7aa-032308779e7d" />


* **Penjelasan Eksekusi Fitur:**
  * Pengguna memilih **Menu 3** untuk melihat tata letak fisik rak buku.
  * Sistem memproses **Array 3D** `lokasi[lantai][rak][posisi]` berukuran `3x4x5`. Slot yang belum diisi menampilkan tanda `[-]`, sedangkan slot yang terisi akan menampilkan judul buku sesuai dengan koordinat persisnya (misal: Lantai 1, Rak 1, Posisi 1).

---

### 4.4 Manajemen Katalog Buku (ArrayList)


<img width="252" height="241" alt="image" src="https://github.com/user-attachments/assets/060a5d2b-2179-481b-a473-89278334c162" />


* **Penjelasan Eksekusi Fitur:**
  * Pengguna memilih **Menu 4** kemudian **Sub-Menu 1** untuk menambahkan buku baru.
  * Buku baru dimasukkan ke dalam **ArrayList** `daftar` secara dinamis, lalu sistem otomatis mencari slot fisik pertama yang kosong pada **Array 3D** dan menempatkan judul buku tersebut di koordinat tersebut (`L1-R1-P1`).

---

### 4.5 Pengelolaan Antrean Mahasiswa (Queue - FIFO)

![Antrean Queue FIFO](assets/05_queue_antrean.png)

```text
Pilih menu: 6

--- Antrean Peminjam ---
1. Tambah mahasiswa ke antrean
2. Lihat antrean
3. Layani antrean terdepan (proses peminjaman)
Pilih: 1
Nama mahasiswa: Budi
Budi masuk antrean (posisi 1).

Pilih menu: 6
Pilih: 3
Melayani: Budi
ID  Judul                          Kategori             Lokasi     Status    
1   Pemrograman Java               Pemrograman          L1-R1-P1   Tersedia  
ID buku yang dipinjam: 1
Budi berhasil meminjam "Pemrograman Java".
```
* **Penjelasan Eksekusi Fitur:**
  * Pengguna memanfaatkan **Queue** untuk menata antrean pendaftaran transaksi.
  * Mahasiswa "Budi" ditambahkan ke ekor antrean (`offer()`). Saat dilayani (`poll()`), sistem memproses pinjaman sesuai prinsip **First-In-First-Out (FIFO)**, mengubah status keberadaan buku di **ArrayList**, dan menambah hitungan transaksi harian di **Array 2D**.

---

### 4.6 Pencatatan & Pembatalan Transaksi (Stack - LIFO)

![Riwayat Transaksi Stack LIFO](assets/06_stack_riwayat.png)

```text
Pilih menu: 5

--- Riwayat Transaksi ---
1. Lihat semua riwayat (terbaru di atas)
2. Lihat transaksi terakhir
3. Batalkan transaksi terakhir
4. Kembalikan buku (catat transaksi)
Pilih: 1
1. PINJAM | Budi | Pemrograman Java | Minggu

Pilih menu: 5
Pilih: 3
Transaksi dibatalkan: PINJAM | Budi | Pemrograman Java | Minggu
```
* **Penjelasan Eksekusi Fitur:**
  * Ketika transaksi pinjam terjadi, objek transaksi di-`push()` ke dalam **Stack**.
  * Pengguna memilih **Menu 5** -> **Option 3** untuk membatalkan transaksi terakhir (*Undo*). Sistem menjalankan method `pop()` pada **Stack** untuk menghapus transaksi teratas (prinsip **Last-In-First-Out**) lalu secara otomatis mengembalikan status buku menjadi "Tersedia" serta mengurangkan counter peminjaman pada **Array 2D**.

---

## 🧪 5. Pengujian & Analisis Fungsionalitas

| No | Kasus Uji | Langkah Pengujian | Ekspektasi Hasil | Status |
| :-: | --- | --- | --- | :-: |
| 1 | **Tambah Buku** | Memilih Menu 4 -> Submenu 1 (Tambah) | Buku masuk ke ArrayList & lokasi fisik tercatat pada Array 3D | ✅ PASSED |
| 2 | **Antrean Masuk** | Memilih Menu 6 -> Submenu 1 (Tambah) | Nama mahasiswa masuk ke antrean Queue secara berurutan | ✅ PASSED |
| 3 | **Pelayanan Queue** | Memilih Menu 6 -> Submenu 3 (Layani) | Mahasiswa terdepan dilayani, status buku berubah, & statistik Array 2D ter-update | ✅ PASSED |
| 4 | **Undo Transaksi** | Memilih Menu 5 -> Submenu 3 (Batalkan) | Transaksi teratas pada Stack dihapus, status buku & statistik otomatis dikembalikan | ✅ PASSED |
| 5 | **Validasi Input** | Memasukkan input huruf pada pilihan menu | Sistem menangkap `NumberFormatException` dan meminta re-input angka | ✅ PASSED |

---

## 📝 6. Kesimpulan

1. Keenam struktur data (**Array 1D, Array 2D, Array 3D, ArrayList, Stack, dan Queue**) berhasil diintegrasikan secara utuh dan terpadu untuk membangun sistem manajemen operasional perpustakaan kampus.
2. Penggunaan **Stack (LIFO)** terbukti optimal untuk fitur pembatalan transaksi (*undo*), karena selalu memproses data transaksi paling mutakhir yang baru dimasukkan.
3. Penggunaan **Queue (FIFO)** menjamin keadilan layanan pendaftaran peminjaman buku berdasarkan urutan kronologis kedatangan mahasiswa.
4. Pemetaan lokasi penyimpanan secara visual tiga dimensi dimungkinkan melalui penerapan **Array 3D**, sedangkan katalog koleksi buku yang fleksibel berhasil diakomodasi oleh keunggulan ukuran dinamis dari **ArrayList**.
