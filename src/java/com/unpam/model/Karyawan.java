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

/**
 *
 * @author kyuza
 */
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
}
