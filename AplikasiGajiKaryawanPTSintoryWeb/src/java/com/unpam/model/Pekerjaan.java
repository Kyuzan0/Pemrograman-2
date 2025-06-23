/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.model;

import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.PreparedStatement;
import java.sql.SQLException;  
import java.sql.Statement;  
import com.unpam.view.PesanDialog;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kyuza
 */
public class Pekerjaan {
    
    private String kodePekerjaan, namaPekerjaan; 
    private int jumlahTugas; 
    private String pesan; 
    private Object[][] list; 
    private final Koneksi koneksi = new Koneksi();  
    private final PesanDialog pesanDialog = new PesanDialog(); 

public String getKodePekerjaan() {  
    return kodePekerjaan;
    }

public void setKodePekerjaan(String kodePekerjaan) {  
    this.kodePekerjaan = kodePekerjaan; 
    } 

public String getNamaPekerjaan() {  
    return namaPekerjaan; 
    } 

public void setNamaPekerjaan(String namaPekerjaan) {  
    this.namaPekerjaan = namaPekerjaan; 
    }

public int getJumlahTugas() { 
    return jumlahTugas; 
    } 

public void setJumlahTugas(int jumlahTugas) {  
    this.jumlahTugas = jumlahTugas; 
    }

public String getPesan() {  
    return pesan; 
    } 

public Object[][] getList() {  
    return list; 
    }

public void setList(Object[][] list) {  
    this.list = list; 
    } 

public boolean simpan() {
        boolean adaKesalahan = false;
        
        // Query SQL dengan placeholder (?) untuk keamanan
        String sql = "INSERT INTO tbpekerjaan (kodePekerjaan, namaPekerjaan, jumlahTugas) VALUES (?, ?, ?)";

        // Menggunakan try-with-resources untuk manajemen koneksi otomatis
        try (Connection connection = koneksi.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            if (connection == null) {
                adaKesalahan = true;
                pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
                return false; // Langsung keluar jika koneksi gagal
            }
            
            // Mengatur parameter query untuk mencegah SQL Injection
            ps.setString(1, this.kodePekerjaan);
            ps.setString(2, this.namaPekerjaan);
            ps.setInt(3, this.jumlahTugas);

            // Eksekusi query update
            int jumlahSimpan = ps.executeUpdate();

            if (jumlahSimpan < 1) {
                adaKesalahan = true;
                pesan = "Gagal menyimpan data pekerjaan";
            }

        } catch (SQLException e) {
            adaKesalahan = true;
            pesan = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
            // Untuk debugging, baik untuk mencetak stack trace
            e.printStackTrace();
        }

        return !adaKesalahan;
    }

public boolean bacaData(int mulai, int jumlah){
    boolean adaKesalahan = false;
    Connection connection;
    list = new Object[0][0];

    if ((connection = koneksi.getConnection()) != null){
        String SQLStatemen;
        Statement sta;
        ResultSet rset;

        try {
            SQLStatemen = "select kodepekerjaan, namapekerjaan from tbpekerjaan"
                        + " limit " + mulai + "," + jumlah;
            sta = connection.createStatement();
            rset = sta.executeQuery(SQLStatemen);

            List<Object[]> tempList = new ArrayList<>();
            while (rset.next()) {
                Object[] row = new Object[2];
                row[0] = rset.getString("kodepekerjaan");
                row[1] = rset.getString("namapekerjaan");
                tempList.add(row);
            }

            list = new Object[tempList.size()][2];
            for (int i = 0; i < tempList.size(); i++) {
                list[i] = tempList.get(i);
            }

            rset.close();
            sta.close();
            connection.close();
        } catch (SQLException e) {
            adaKesalahan = true;
            System.out.println("Kesalahan SQL: " + e.getMessage());
        }
    } else {
        adaKesalahan = true;
        System.out.println("Koneksi gagal.");
    }

    return !adaKesalahan;
}


}
