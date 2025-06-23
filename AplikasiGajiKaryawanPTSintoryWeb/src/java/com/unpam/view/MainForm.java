/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.view;

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
@WebServlet(name = "MainForm", urlPatterns = {"/MainForm"})
public class MainForm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void tampilkan(String konten, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession(true);

            String menu = "<br><b>Master Data</b><br>"
                    + "<a href='KaryawanController'>Karyawan</a><br>"
                    + "<a href='PekerjaanController'>Pekerjaan</a><br>"
                    + "<b>Transaksi</b><br>"
                    + "<a href='Gaji'>Gaji</a><br>"
                    + "<b>Laporan</b><br>"
                    + "<a href='LaporanGaji'>Gaji</a><br>"
                    + "<a href='LoginController'>Login</a><br><br>";

            String topMenu = "<nav><ul>"
                    + "<li><a href='Home'>Home</a></li>"
                    + "<li><a href='#'>Master Data</a><ul>"
                    + "<li><a href='KaryawanController'>Karyawan</a></li>"
                    + "<li><a href='PekerjaanController'>Pekerjaan</a></li>"
                    + "</ul></li>"
                    + "<li><a href='Transaksi'>Transaksi</a></li>"
                    + "<li><a href='Gaji'>Gaji</a></li>"
                    + "<li><a href='#'>Laporan</a><ul><li><a href='LaporanGaji'>Gaji</a></li></ul></li>"
                    + "<li><a href='LoginController'>Login</a></li>"
                    + "</ul></nav>";

            session.setAttribute("menu", menu);
            session.setAttribute("topMenu", topMenu);

            String userName = "";

            if (!session.isNew()) {
                try {
                    userName = session.getAttribute("userName").toString();
                } catch (Exception ex) {
                    userName = "";
                }
            }


            if ((userName == null) || userName.equals("")) {
                if (konten == null || konten.equals("")) {
                    konten = "<br><h1>Selamat Datang</h1><h2>" + userName + "</h2>";
                }
    }

            try {
                menu = session.getAttribute("menu").toString();
            } catch (Exception ex) {}

            try {
                topMenu = session.getAttribute("topMenu").toString();
            } catch (Exception ex) {}

            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html><head><title>Informasi Gaji Karyawan</title>");
                out.println("<link href='" + request.getContextPath() + "/style.css' rel='stylesheet' type='text/css' />");
                out.println("<body bgcolor='#808080'><center><table width='80%' bgcolor='#eeeeee'>");

                out.println("<tr><td colspan='2' align='center'>");
                out.println("<h2>Informasi Gaji Karyawan</h2><i>PT Sinergy</i>");
                out.println("Jl. Surya Kencana No. 99 Pamulang, Tangerang Selatan, Banten</td></tr>");

                out.println("<tr><td align='center' valign='top' bgcolor='#ffeeff'>" + menu + "</td>");
                out.println("<td align='center' valign='top' bgcolor='#ffffff'>" + topMenu + "<br>" + konten + "</td></tr>");

                out.println("<tr><td colspan='2' align='center' bgcolor='#ffeeff'><small>");
                out.println("Copyright &copy; 2017 PT Sinergy<br>");
                out.println("Jl. Surya Kencana No. 99 Pamulang, Tangerang Selatan, Banten</small></td></tr>");

                out.println("</table></center></body></html>");
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
        tampilkan("", request, response);
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

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
