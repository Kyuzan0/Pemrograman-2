/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kyuza
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Direkomendasikan menggunakan driver terbaru
    private static final String database = "jdbc:mysql://localhost:3306/dbaplikasigajikaryawan";
    private static final String user = "root";
    private static final String password = "";

    private Connection connection; // Deklarasi variabel connection
    private String pesanKesalahan; // Deklarasi variabel pesanKesalahan

    public String getPesanKesalahan() {
        return pesanKesalahan;
    }

    public Connection getConnection() { // Perbaikan nama method dan tipe kembalian
        connection = null;
        pesanKesalahan = "";

        try {
            Class.forName(driver); // Memuat driver JDBC
            connection = DriverManager.getConnection(database, user, password); // Membuat koneksi
        } catch (ClassNotFoundException ex) {
            pesanKesalahan = "Driver database tidak ditemukan: " + ex.getMessage();
            // Sebaiknya log error juga di sini, contoh: System.err.println(pesanKesalahan);
        } catch (SQLException ex) {
            pesanKesalahan = "Koneksi ke database gagal: " + ex.getMessage();
            // Sebaiknya log error juga di sini, contoh: System.err.println(pesanKesalahan);
        }
        return connection;
    }
}
