/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author DELL
 */
public class AntreanPeminjam {
    private Queue<String> antrean = new LinkedList<>();
 
    public void tambah(String nama) {
        antrean.offer(nama);
    }
 
    public int ukuran() {
        return antrean.size();
    }
 
    public boolean isEmpty() {
        return antrean.isEmpty();
    }
 
    public String lihatDepan() {
        return antrean.peek();
    }
 
    public String layani() {
        return antrean.poll();
    }
 
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
 