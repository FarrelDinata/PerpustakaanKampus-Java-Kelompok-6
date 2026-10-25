/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.Stack;
import model.Transaksi;

/**
 *
 * @author DELL
 */
public class RiwayatTransaksi {
    private Stack<Transaksi> riwayat = new Stack<>();
 
    public void catat(Transaksi t) {
        riwayat.push(t);
    }
 
    public boolean isEmpty() {
        return riwayat.isEmpty();
    }
 
    public void tampilSemua() {
        if (riwayat.isEmpty()) {
            System.out.println("Riwayat kosong.");
            return;
        }
        // dari atas (terbaru) ke bawah (terlama)
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