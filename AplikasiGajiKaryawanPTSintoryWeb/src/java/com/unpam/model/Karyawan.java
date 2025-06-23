/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.model;

import java.sql.Connection;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.unpam.view.PesanDialog;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kyuza
 */

//class karyawan
public class Karyawan {
    private String ktp, nama, password;
    private int ruang;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    // Deklarasi PesanDialog yang benar, pastikan kelas PesanDialog ada
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getRuang() {
        return ruang;
    }

    public void setRuang(int ruang) {
        this.ruang = ruang;
    }

    public boolean hapus(String ktp) {
        boolean berhasil = false;
        try {
            String sql = "DELETE FROM karyawan WHERE ktp=?";
            // eksekusi database di sini (PreparedStatement dsb.)
            berhasil = true;
        } catch (Exception e) {
            this.pesan = e.getMessage();
        }
        return berhasil;
    }
    
public boolean baca(String ktp) {
    boolean berhasil = false;
    Connection connection = null;
    try {
        connection = koneksi.getConnection(); // gunakan koneksi yang sudah ada

        String sql = "SELECT * FROM tbkaryawan WHERE ktp=?"; // GANTI INI!
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, ktp);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            this.ktp = rs.getString("ktp");
            this.nama = rs.getString("nama");
            this.ruang = rs.getInt("ruang");
            this.password = rs.getString("password");
            berhasil = true;
        }

        rs.close();
        ps.close();
        connection.close();
    } catch (Exception e) {
        this.pesan = "ERR: " + e.getMessage();
    }

    return berhasil;
}



    
    public String getPesan() {
        return pesan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object[][] getList() {
        return list;
    }

    public void setList(Object[][] list) {
        this.list = list;
    }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection = null; // Inisialisasi connection ke null
        PreparedStatement preparedStatement = null; // Inisialisasi preparedStatement ke null
        // ResultSet rset = null; // rset tidak digunakan untuk operasi insert ini, jadi bisa dihilangkan jika hanya untuk simpan

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatemen = ""; // Deklarasi di luar try agar bisa diakses di catch
            try {
                SQLStatemen = "insert into tbkaryawan(ktp, nama, ruang, password) values (?,?,?,?)";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, ktp);
                preparedStatement.setString(2, nama);
                preparedStatement.setInt(3, ruang);
                preparedStatement.setString(4, password);
                int jumlahSimpan = preparedStatement.executeUpdate();

                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data karyawan (tidak ada baris yang terpengaruh).";
                } else {
                    pesan = "Data karyawan berhasil disimpan."; // Pesan sukses
                }
            } catch (SQLException ex) {
                adaKesalahan = true;
                // Pesan error lebih spesifik
                pesan = "Gagal menyimpan data karyawan ke tabel tbkaryawan.\nKesalahan SQL: " + ex.getMessage() + "\nQuery: " + SQLStatemen;
                // Sebaiknya log error juga di sini, contoh: ex.printStackTrace();
            } finally {
                // Selalu tutup PreparedStatement dan Connection di blok finally
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    } 
                } catch (SQLException ex) {
                    // Bisa ditambahkan logging jika penutupan gagal, namun biasanya error utama sudah ditangkap
                    pesan += "\nKesalahan saat menutup koneksi/statement: " + ex.getMessage();
                    adaKesalahan = true; // Tandai ada kesalahan jika penutupan juga gagal
                }
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server database.\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    
    public boolean bacaData(int mulai, int jumlah){
    boolean adaKesalahan = false;
    Connection connection;
    list = new Object[0][0];

    if ((connection = koneksi.getConnection()) != null){
        String SQLStatemen;
        PreparedStatement preparedStatement;
        ResultSet rset;

        try {
            SQLStatemen = "select ktp, nama from tbkaryawan"
                + " limit " + mulai + "," + jumlah;
            preparedStatement = connection.prepareStatement(SQLStatemen);
            rset = preparedStatement.executeQuery();

            // Contoh pengisian list (bisa disesuaikan dengan jumlah kolom dan kebutuhan)
            List<Object[]> tempList = new ArrayList<>();
            while (rset.next()) {
                Object[] row = new Object[2];
                row[0] = rset.getString("ktp");
                row[1] = rset.getString("nama");
                tempList.add(row);
            }

            list = new Object[tempList.size()][2];
            for (int i = 0; i < tempList.size(); i++) {
                list[i] = tempList.get(i);
            }

            rset.close();
            preparedStatement.close();
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
