/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import banco.Banco;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author caiot
 */
@WebServlet(name = "ChatPrincipal", urlPatterns = {"/ChatPrincipal"})
public class ChatPrincipal extends HttpServlet {

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

        HttpSession sessao = request.getSession();
        sessao.setMaxInactiveInterval(60 * 5);

        if (sessao.getAttribute("logado") == null || sessao.getAttribute("logado").equals(false)) {
            response.sendRedirect("Login");
        }

        ArrayList<String> contatos = new ArrayList<>();
        try {
            contatos = Banco.getContatos(sessao.getAttribute("user").toString());
        } catch (SQLException ex) {
            Logger.getLogger(ChatPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>\n"
                    + "\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>Chat - " + sessao.getAttribute("user") + "</title>\n"
                    + "        <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                    + "        <link rel=\"stylesheet\" href=\"css/style.css\">\n"
                    + "    </head>\n"
                    + "\n"
                    + "    <body>\n"
                    + "        <div class=\"container\">\n"
                    + "            <div class=\"row\">\n"
                    + "                <div class=\"col-sm-3 mt-5\">\n"
                    + "                    <h3>Olá, <span class=\"negrito\">" + sessao.getAttribute("user") + "</span>!</h3> <a href=\"Logout\">Sair</a>\n"
                    + "                    <p class=\"mt-3\">Contatos:</p>\n"
                    + "                    <div class=\"list-group contatos\">\n"
            );

            for (String contato : contatos) {
                out.println("<a href=\"ChatPrincipal?from=" + sessao.getAttribute("user") + "&to=" + contato + "\" class=\"list-group-item list-group-item-action\">" + contato + "</a>\n");
            }
            String para;

            if (request.getParameter("to") == null) {
                para = "Selecione um contato!";
            } else {
                para = "Chat com " + request.getParameter("to");
            }
            out.println("                    </div>\n"
                    + "                </div>\n"
                    + "                <div class=\"col-sm-8 mt-5\">\n"
                    + "                    <h3>" + para + "</h3>"
                    + "                    <iframe src = \"Mensagens?from="+request.getParameter("from")+"&to="+request.getParameter("to")+"\" height = \"400\" width = \"700\"></iframe>"
            );

            /*                   + "                    <textarea id = \"painelMsg\" class=\"ml-2 mt-5 form-control\" rows=\"20\" disabled>"
            );

            ResultSet rs = null;

            try {
                rs = Banco.buscaMensagens(request.getParameter("from"), request.getParameter("to"));

                if (rs != null) {
                    while (rs.next()) {
                        out.println("["+rs.getTimestamp("data")+"] "+rs.getString("remetente")+" diz:\n" +rs.getString("texto")+"\n\n");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ChatPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
             */
            out.println("                    </textarea>\n"
                    + "                    <form action = \"EscreveMsg\" method = \"POST\">"
                    + "                    <textarea class=\"ml-2 mt-3 form-control\" name=\"msg\" rows=\"5\" "
            );
            
            if(para.equals("Selecione um contato!")){
                out.println("disabled ");
            }
            
            out.println("placeholder=\"Write a message...\"></textarea>\n"
                    + "                </div>\n"
                    + "                <div class=\"col-sm-1\">\n"
                    + "                    <input type='hidden' name='from' value='" + request.getParameter("from") + "'>"
                    + "                    <input type='hidden' name='to' value='" + request.getParameter("to") + "'>"
                    + "                    <input type='submit' class=\"btn btn-primary botaoenviar\">\n"
                    + "                    </form>"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "    </body>\n"
                    + "</html>");
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
