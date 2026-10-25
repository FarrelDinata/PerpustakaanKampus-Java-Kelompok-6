/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDate;
/**
 *
 * @author DELL
 */
public class StatistikPeminjaman {
    // Array 1D
    public static final String[] KATEGORI =
            {"Pemrograman", "Matematika", "Jaringan Komputer", "Basis Data"};
    public static final String[] HARI =
            {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
 
    // Array 2D [kategori][hari]
    private int[][] data = new int[KATEGORI.length][HARI.length];
 
    public static int hariIni() {
        return LocalDate.now().getDayOfWeek().getValue() - 1; // Senin = 0
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
 