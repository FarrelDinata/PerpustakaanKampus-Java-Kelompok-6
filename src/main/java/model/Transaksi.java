/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import service.StatistikPeminjaman;

/**
 *
 * @author DELL
 */
public class Transaksi {
    private String jenis;      // "PINJAM" atau "KEMBALI"
    private String mahasiswa;
    private Buku buku;
    private int hari;          // indeks hari (Senin = 0)

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
