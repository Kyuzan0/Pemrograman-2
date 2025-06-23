/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Gaji;
import com.unpam.model.Karyawan;
import com.unpam.model.Pekerjaan;
import com.unpam.view.MainForm;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author kyuza
 */
@WebServlet(name = "GajiController", urlPatterns = {"/GajiController"})
public class GajiController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
Karyawan karyawan = new Karyawan();
Pekerjaan pekerjaan = new Pekerjaan();
Gaji gaji = new Gaji();
String userName = "";

String tombol = request.getParameter("tombol");
String tombolKaryawan = request.getParameter("tombolKaryawan");
String ktp = request.getParameter("ktp");
String namaKaryawan = request.getParameter("namaKaryawan");
String ruang = request.getParameter("ruang");
String mulaiParameter = request.getParameter("mulai");
String jumlahParameter = request.getParameter("jumlah");
String ktpDipilih = request.getParameter("ktpDipilih");
String tombolPekerjaan = request.getParameter("tombolPekerjaan");
String kodePekerjaan = request.getParameter("kodePekerjaan");
String namaPekerjaan = request.getParameter("namaPekerjaan");
String jumlahTugas = request.getParameter("jumlahTugas");
String kodePekerjaanDipilih = request.getParameter("kodePekerjaanDipilih");
String gajiBersih = request.getParameter("gajiBersih");
String gajiKotor = request.getParameter("gajiKotor");
String tunjangan = request.getParameter("tunjangan");

if (tombol == null) tombol = "";
if (tombolKaryawan == null) tombolKaryawan = "";
if (ktp == null) ktp = "";
if (namaKaryawan == null) namaKaryawan = "";
if (ruang == null) ruang = "";
if (ktpDipilih == null) ktpDipilih = "";
if (tombolPekerjaan == null) tombolPekerjaan = "";
if (kodePekerjaan == null) kodePekerjaan = "";
if (namaPekerjaan == null) namaPekerjaan = "";
if (jumlahTugas == null) jumlahTugas = "";
if (kodePekerjaanDipilih == null) kodePekerjaanDipilih = "";
if (gajiBersih == null) gajiBersih = "";
if (gajiKotor == null) gajiKotor = "";
if (tunjangan == null) tunjangan = "";

int mulai = 0, jumlah = 10;
try {
    mulai = Integer.parseInt(mulaiParameter);
} catch (NumberFormatException ex) {}
try {
    jumlah = Integer.parseInt(jumlahParameter);
} catch (NumberFormatException ex) {}

String keterangan = "<br>";
try {
    userName = session.getAttribute("userName").toString();
} catch (Exception ex) {}

if (!((userName == null) || userName.equals(""))) {
    if (tombolKaryawan.equals("Cari")) {
        if (!ktp.equals("")) {
            if (karyawan.baca(ktp)) {
                ktp = karyawan.getKtp();
                namaKaryawan = karyawan.getNama();
                ruang = Integer.toString(karyawan.getRuang());
                keterangan = "<br>";
            } else {
                namaKaryawan = "";
                ruang = "1";
                keterangan = "KTP \"" + ktp + "\" tidak ada";
            }
        } else {
            keterangan = "KTP harus diisi";
        }
    } else if (tombolKaryawan.equals("Pilih")) {
        ktp = ktpDipilih;
        namaKaryawan = "";
        ruang = "1";
        if (!ktpDipilih.equals("")) {
            if (karyawan.baca(ktpDipilih)) {
                ktp = karyawan.getKtp();
                namaKaryawan = karyawan.getNama();
                ruang = Integer.toString(karyawan.getRuang());
                keterangan = "<br>";
            } else {
                keterangan = "KTP \"" + ktp + "\" tidak ada";
            }
        } else {
            keterangan = "Tidak ada yang dipilih";
        }
    }

    String kontenLihat = "";
    if (tombolKaryawan.equals("Lihat") || tombolKaryawan.equals("Sebelumnya") || tombolKaryawan.equals("Berikutnya") || tombolKaryawan.equals("Tampilkan")) {
        kontenLihat += "<tr>";
        kontenLihat += "<td colspan='2' align='center'>";
        kontenLihat += "<table>";

        if (tombolKaryawan.equals("Sebelumnya")) {
            mulai -= jumlah;
            if (mulai < 0) mulai = 0;
        }
        if (tombolKaryawan.equals("Berikutnya")) {
            mulai += jumlah;
        }

        Object[][] listKaryawan = null;
        if (karyawan.bacaData(mulai, jumlah)) {
            listKaryawan = karyawan.getList();
        } else {
            keterangan = karyawan.getPesan();
        }

        if (listKaryawan != null) {
            for (int i = 0; i < listKaryawan.length; i++) {
                kontenLihat += "<tr>";
                kontenLihat += "<td>";
                if (i == 0) {
                    kontenLihat += "<input type='radio' checked name='ktpDipilih' value='" + listKaryawan[i][0].toString() + "'>";
                } else {
                    kontenLihat += "<input type='radio' name='ktpDipilih' value='" + listKaryawan[i][0].toString() + "'>";
                }
                kontenLihat += "</td>";
                kontenLihat += "<td>" + listKaryawan[i][0].toString() + "</td>";
                kontenLihat += "<td>" + listKaryawan[i][1].toString() + "</td>";
                kontenLihat += "</tr>";
            }
        }

        kontenLihat += "</table>";
        kontenLihat += "</td>";
        kontenLihat += "</tr>";

        kontenLihat += "<tr>";
        kontenLihat += "<td colspan='2' align='center'>";
        kontenLihat += "<table>";
        kontenLihat += "<tr>";
        kontenLihat += "<td align='center'><input type='submit' name='tombolKaryawan' value='Sebelumnya' style='width: 100px'></td>";
        kontenLihat += "<td align='center'><input type='submit' name='tombolKaryawan' value='Pilih' style='width: 60px'></td>";
        kontenLihat += "<td align='center'><input type='submit' name='tombolKaryawan' value='Berikutnya' style='width: 100px'></td>";
        kontenLihat += "</tr>";
        kontenLihat += "<tr>";
        kontenLihat += "<td align='center'>Mulai <input type='text' name='mulai' value='" + mulai + "' style='width: 40px'></td>";
        kontenLihat += "<td>Jumlah:";
        kontenLihat += "<select name='jumlah'>";
        for (int i = 1; i <= 10; i++) {
            if (jumlah == (i * 10)) {
                kontenLihat += "<option selected value='" + (i * 10) + "'>" + (i * 10) + "</option>";
            } else {
                kontenLihat += "<option value='" + (i * 10) + "'>" + (i * 10) + "</option>";
            }
        }
        kontenLihat += "</select></td>";
        kontenLihat += "<td align='center'><input type='submit' name='tombolKaryawan' value='Tampilkan' style='width: 90px'></td>";
        kontenLihat += "</tr></table></td></tr>";
        kontenLihat += "<tr><td colspan='2' align='center'><br></td></tr>";
    }

    // Bagian tombol simpan dan hapus
    if (!tombol.equals("")) {
        if (tombol.equals("Simpan")) {
            if (!ktp.equals("") && !kodePekerjaan.equals("")) {
                gaji.setKtp(ktp);
                gaji.setListGaji(new Object[][]{{kodePekerjaan, gajiBersih, gajiKotor, tunjangan}});
                if (gaji.simpan()) {
                    ktp = ""; namaKaryawan = ""; ruang = ""; kodePekerjaan = "";
                    namaPekerjaan = ""; jumlahTugas = ""; gajiBersih = ""; gajiKotor = ""; tunjangan = "";
                    keterangan = "Sudah disimpan";
                } else {
                    keterangan = "Gagal menyimpan:\n" + gaji.getPesan();
                }
            } else {
                keterangan = "KTP dan kode pekerjaan tidak boleh kosong";
            }
        } else if (tombol.equals("Hapus")) {
            if (!ktp.equals("") && !kodePekerjaan.equals("")) {
                if (gaji.hapus(ktp, kodePekerjaan)) {
                    ktp = ""; namaKaryawan = ""; ruang = ""; kodePekerjaan = "";
                    namaPekerjaan = ""; jumlahTugas = ""; gajiBersih = ""; gajiKotor = ""; tunjangan = "";
                    keterangan = "Sudah dihapus";
                } else {
                    keterangan = "Gagal menghapus:\n" + gaji.getPesan();
                }
            } else {
                keterangan = "KTP dan kode mata kuliah tidak boleh kosong";
            }
        }
    }

    // Bagian tampilan HTML (konten)
    String konten = "<h2>Input Gaji Karyawan</h2>";
    konten += "<form action='GajiController' method='post'>";
    konten += "<table>";
    konten += "<tr><td align='right'>KTP</td>";
    konten += "<td align='left'><input type='text' value='" + ktp + "' name='ktp' maxlength='15' style='width: 100px'>";
    konten += "<input type='submit' name='tombolKaryawan' value='Cari'>";
    konten += "<input type='submit' name='tombolKaryawan' value='Lihat'></td></tr>";
    konten += "<tr><td align='right'>Nama</td>";
    konten += "<td align='left'><input type='text' readonly value='" + namaKaryawan + "' name='namaKaryawan' style='width: 200px'></td></tr>";
    konten += "<tr><td align='right'>Ruang</td>";
    konten += "<td align='left'><input type='text' readonly value='" + ruang + "' name='ruang' style='width: 20px'></td></tr>";

    if (!tombolKaryawan.equals("")) {
        if (!keterangan.equals("<br>")) {
            konten += "<tr><td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", " ") + "</b></td></tr>";
        }
        konten += kontenLihat;
    }

    konten += "<tr><td align='right'>Kode Pekerjaan</td>";
    konten += "<td align='left'><input type='text' value='" + kodePekerjaan + "' name='kodePekerjaan' maxlength='15' style='width: 120px'>";
    konten += "<input type='submit' name='tombolPekerjaan' value='Cari'>";
    konten += "<input type='submit' name='tombolPekerjaan' value='Lihat'></td></tr>";
    konten += "<tr><td align='right'>Nama Pekerjaan</td>";
    konten += "<td align='left'><input type='text' readonly value='" + namaPekerjaan + "' name='namaPekerjaan' style='width: 220px'></td></tr>";
    konten += "<tr><td align='right'>Jumlah Tugas</td>";
    konten += "<td align='left'><input type='text' readonly value='" + jumlahTugas + "' name='jumlahTugas' style='width: 20px'></td></tr>";

    if (!tombolPekerjaan.equals("")) {
        if (!keterangan.equals("<br>")) {
            konten += "<tr><td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", " ") + "</b></td></tr>";
        }
        konten += kontenLihat;
    }

    konten += "<tr><td align='right'>Gaji Bersih</td>";
    konten += "<td align='left'><input type='text' value='" + gajiBersih + "' name='gajiBersih' style='width: 50px'></td></tr>";
    konten += "<tr><td align='right'>Gaji Kotor</td>";
    konten += "<td align='left'><input type='text' value='" + gajiKotor + "' name='gajiKotor' style='width: 50px'></td></tr>";
    konten += "<tr><td align='right'>Tunjangan</td>";
    konten += "<td align='left'><input type='text' value='" + tunjangan + "' name='tunjangan' style='width: 50px'></td></tr>";

    konten += "<tr><td colspan='2' align='center'>";
    konten += "<table><tr>";
    konten += "<td align='center'><input type='submit' name='tombol' value='Simpan' style='width: 100px'></td>";
    konten += "<td align='center'><input type='submit' name='tombol' value='Hapus' style='width: 100px'></td>";
    konten += "</tr></table></td></tr>";

    if (!tombol.equals("") && !keterangan.equals("<br>")) {
        konten += "<tr><td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", " ") + "</b></td></tr>";
    }

    konten += "</table></form><br>";
    new MainForm().tampilkan(konten, request, response);
} else {
    response.sendRedirect(".");
}


        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
