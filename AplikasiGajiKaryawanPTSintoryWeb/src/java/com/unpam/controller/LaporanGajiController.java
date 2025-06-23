/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Gaji;
import com.unpam.view.MainForm;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;

/**
 *
 * @author kyuza
 */
@WebServlet(name = "LaporanGajiController", urlPatterns = {"/LaporanGajiController"})
public class LaporanGajiController extends HttpServlet {

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
        String[][] formatTypeData = {
            {"PDF (Portable Document Format)", "pdf", "application/pdf"},
            {"XLSX (Microsoft Excel)", "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {"XLS (Microsoft Excel 97-2003)", "xls", "application/vnd.ms-excel"},
            {"DOCX (Microsoft Word)", "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {"ODT (OpenDocument Text)", "odt", "application/vnd.oasis.opendocument.text"},
            {"RTF (Rich Text Format)", "rtf", "text/rtf"}
        };

        HttpSession session = request.getSession(true);
        String userName = "";

        String tombol = request.getParameter("tombol");
        String opsi = request.getParameter("opsi");
        String ktp = request.getParameter("ktp");
        String ruang = request.getParameter("ruang");
        String formatType = request.getParameter("formatType");

        if (tombol == null) tombol = "";
        if (ktp == null) ktp = "";
        if (opsi == null) opsi = "";
        if (ruang == null) ruang = "";

        String keterangan = "<br>";
        int noType = 0;

        for (int i = 0; i < formatTypeData.length; i++) {
            if (formatTypeData[i][0].equals(formatType)) {
                noType = i;
            }
        }

        try {
            userName = session.getAttribute("userName").toString();
        } catch (Exception ex) {}

        if ((userName != null) || !userName.equals("")) {
            boolean opsiSelected = false;

            if (tombol.equals("Cetak")) {
                gaji = new Gaji();

                int urutDipilih = 0;
                try {
                    urutDipilih = Integer.parseInt(ruang);
                } catch (NumberFormatException ex) {}

                if (gaji.cetakLaporan(opsi, ktp, urutDipilih, formatTypeData[noType][1],
                    getServletContext().getRealPath("report/lapGajiExport.jasper"))) {
                    byte[] pdfBytes = gaji.getPdfBytes();
                    OutputStream outStream = response.getOutputStream();
                    try {
                        response.setHeader("Content-Disposition", "inline; filename=lapGajiExport." + formatTypeData[noType][1]);
                        response.setContentType(formatTypeData[noType][2]);

                        response.setContentLength(pdfBytes.length);
                        outStream.write(pdfBytes, 0, pdfBytes.length);

                        outStream.flush();
                        outStream.close();
                    } catch (IOException ex) {}
                } else {
                    keterangan += gaji.getKeterangan();
                }
            }

            String konten = "<h2>Mencetak Gaji</h2>";
            konten += "<form action='LaporanGajiController' method='post'>";
            konten += "<table>";

            if (opsi.equalsIgnoreCase("KTP")) {
                konten += "<td align='left'><input type='radio' checked name='opsi' value='KTP'></td>";
                opsiSelected = true;
            } else {
                konten += "<td align='left'><input type='radio' name='opsi' value='KTP'></td>";
            }

            konten += "<td align='left'>KTP</td>";
            konten += "<td align='left'><input type='text' value='" + ktp + "' name='ktp' maxlength='15' size='15'></td>";
            konten += "</tr>";

            konten += "<tr>";
            if (opsi.equals("ruang")) {
                konten += "<td align='left'><input type='radio' checked name='opsi' value='ruang'></td>";
                opsiSelected = true;
            } else {
                konten += "<td align='left'><input type='radio' name='opsi' value='ruang'></td>";
            }

            konten += "<td align='left'>Ruang</td>";
            konten += "<td align='left'>";
            konten += "<select name='ruang'>";

            konten += "<option selected value='Semua'>Semua</option>";

            if (ruang != null) {
                konten += "<option selected value='" + ruang + "'>" + ruang + "</option>";
            }

            konten += "</select>";
            konten += "</td>";
            konten += "</tr>";

            konten += "<tr>";
            if (!opsiSelected) {
                konten += "<td align='right'><input type='radio' checked name='opsi' value='Semua'></td>";
            } else {
                konten += "<td align='right'><input type='radio' name='opsi' value='Semua'></td>";
            }

            konten += "<td align='left'>Semua</td>";
            konten += "<td><br></td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td>Format Laporan</td>";
            konten += "<td colspan=2>";
            konten += "<select name='formatType'>";

            for (String[] formatLaporan : formatTypeData) {
                if (formatLaporan[0].equals(formatType)) {
                    konten += "<option selected value='" + formatLaporan[0] + "'>" + formatLaporan[0] + "</option>";
                } else {
                    konten += "<option value='" + formatLaporan[0] + "'>" + formatLaporan[0] + "</option>";
                }
            }

            konten += "</select>";
            konten += "</td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan=3>" + keterangan.replaceAll("\n", "<br>").replaceAll(",", " ") + "</td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan=3 align='center'><input type='submit' name='tombol' value='Cetak' style='width: 100px'></td>";
            konten += "</tr>";

            konten += "</table>";
            konten += "</form>";

            new MainForm().tampilkan(konten, request, response);
        } else {
            response.sendRedirect("");
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
