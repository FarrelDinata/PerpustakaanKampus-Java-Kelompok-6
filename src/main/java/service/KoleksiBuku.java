/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import model.Buku;

/**
 *
 * @author DELL
 */
public class KoleksiBuku {
    public static final int LANTAI = 3, RAK = 4, POSISI = 5;
 
    // Array 3D [lantai][rak][posisi] berisi judul buku (null = kosong)
    private String[][][] lokasi = new String[LANTAI][RAK][POSISI];
 
    // ArrayList daftar buku
    private ArrayList<Buku> daftar = new ArrayList<>();
    private int nextId = 1;
 
    public boolean isEmpty() {
        return daftar.isEmpty();
    }
 
    // mengembalikan buku baru, atau null jika rak penuh
    public Buku tambah(String judul, int kategori) {
        for (int l = 0; l < LANTAI; l++) {
            for (int r = 0; r < RAK; r++) {
                for (int p = 0; p < POSISI; p++) {
                    if (lokasi[l][r][p] == null) {
                        Buku b = new Buku(nextId++, judul, kategori, l, r, p);
                        daftar.add(b);
                        lokasi[l][r][p] = judul;
                        return b;
                    }
                }
            }
        }
        return null;
    }
 
    public Buku cari(int id) {
        for (Buku b : daftar) {
            if (b.getId() == id) return b;
        }
        return null;
    }
 
    public void hapus(Buku b) {
        lokasi[b.getLantai()][b.getRak()][b.getPosisi()] = null;
        daftar.remove(b);
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
 