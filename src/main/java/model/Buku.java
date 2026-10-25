/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package model;

/**
 *
 * @author DELL
 */
// Kelas utama: menu interaktif (Scanner) yang menghubungkan keenam struktur data
public class Buku {
    private int id;
    private String judul;
    private int kategori;   // indeks pada array kategori
    private int lantai, rak, posisi;
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

    // contoh hasil: L1-R2-P3
    public String getLokasi() {
        return "L" + (lantai + 1) + "-R" + (rak + 1) + "-P" + (posisi + 1);
    }
}