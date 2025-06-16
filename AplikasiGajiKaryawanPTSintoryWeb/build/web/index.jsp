<%-- 
    Document   : index
    Created on : May 27, 2025, 10:55:28 AM
    Author     : kyuza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css" />
        <title>Informasi Gaji Karyawan</title>
    </head>
    <body bgcolor="#808080">
        <%
            String menu="<br><b>Master Data</b><br>"
                    + "<a href=.>Karyawan</a></br>"
                    + "<a href=.>Pekerjaan</a><br>"
                    + "<b>Transaksi</b><br>"
                    + "<a href=.>Gaji</a><br>"
                    + "<b>Laporan</b><br>"
                    + "<a href=.>Gaji</a><br><br>"
                    + "<a href=LoginController>Login</a><br><br>";
            
                    String topMenu="<nav><ul>"
                            + "<li><a href=.>Home</a></li>"
                            + "<li><a href=#>Master Data</a>"
                            + "<ul>"
                            + "<li><a href=.>Karyawan</a></li>"
                            + "<li><a href=.>Pekerjaan</a></li>"
                            + "</ul>"
                            + "</li>"
                            + "<li><a href=#>Transaksi</a>"
                            + "<ul>"
                            + "<li><a href=.>Gaji</a></li>"
                            + "</ul>"
                            + "</li>"
                            + "<li><a href=#>Laporan</a>"
                            + "<ul>"
                            + "<li><a href=.>Gaji</a></li>"
                            + "</ul>"
                            + "</li>"
                            + "<li><a href=LoginController>Login</a></li>"
                            + "</ul>"
                            + "</nav>";
                    
                    String konten="<br><h1>SelamatDatang</h1>";
                    String userName="";
                    
                    if (!session.isNew()) {
                        try {
                            userName = session.getAttribute("userName").toString();
                        } catch (Exception ex) {}
                        
                        if (!((userName == null || userName.equals("") ))) {
                            konten += "<h2>"+userName+"</h2>";
                            
                            try {
                                menu = session.getAttribute("menu").toString();
                            } catch (Exception ex) {}
                            
                            try {
                                topMenu = session.getAttribute("topMenu").toString();
                            } catch (Exception ex) {}
                        } 
                    }
                %>
                
    <center>
        <table width="80%" bgcolor="#eeeee">
            <tr>
                <td colspan="2" align="center">
                    <br>
                    <h2 Style="margin-bottom: 0px; margin-top: 0px;">
                        Informasi Gaji karyawan
                    </h2>
                    <h1 Style="margin-bottom: 0px; margin-top: 0px;">
                        PT Sintory
                    </h1>
                    <h4 Style="margin-bottom: 0px; margin-top: 0px;">
                        Jl. Surya Kencana No. 99 Pamulang, Tanggerang Selatan, Banten
                    </h4>
                    <br>
                </td>
            </tr>
            <tr height="400">
                <td width="200" align="center" valign="top" bgcolor="#eeeeee">
                    <br>
                    <div id='menu'>
                        <%=menu%>
                    </div>
                </td>
                <td align="center" valign="top" bgcolor="#ffffff">
                    <%=topMenu%>
                    <br>
                    <%=konten%>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center" bgcolor="#eeeeff">
                    <small>
                        Copyright &copy; 2017 PT Sintory<br>
                        Jl. Surya Kencana No. 99 Pamulang, Tanggerang Selatan, Banten<br>
                    </small>
                </td>
            </tr>
        </table>
    </center>
    </body>                 
</html>
