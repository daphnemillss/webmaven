/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import banco.Banco;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import object.LoginObject;

/**
 *
 * @author caiot
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

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
        if(sessao.getAttribute("logado") != null && sessao.getAttribute("logado").equals(true)){
            response.sendRedirect("ChatPrincipal");
        }
        
        Cookie[] cookie = request.getCookies();
        
        String errologin = null;
        
        if(request.getParameter("logout") != null){
            request.getSession().invalidate();
        }

        if (cookie != null) {
            for (int i = 0 ; i < cookie.length ; i++) {
                if (cookie[i].getName().equals("errologin") && cookie[i].getValue() != null) {
                    errologin = cookie[i].getValue();
                   
                }
            }
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            out.println("<!DOCTYPE html>\n"
                    + "\n"
                    + "<html lang = \"en-us\">\n"
                    + "    <head>\n"
                    + "        <meta charset =\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "\n"
                    + "        <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                    + "        <link rel = \"stylesheet\" href = \"css/style.css\">\n"
                    + "        <title>Chat</title>\n"
                    + "    </head>\n"
                    + "\n"
                    + "    <body>\n"
                    + "        <div class=\"container\">\n"
                    + "\n"
                    + "            <div class = \"divLogin row\">\n"
                    + "                <div class=\"col-md-4\">\n"
                    + "                </div>\n"
                    + "\n"
                    + "                <div class=\"col-md-4 text-center\">\n"
                    + "                    <div class=\"search-box\">\n"
                    + "                        <div class=\"caption mt-3\">\n"
                    + "                            <h1>Greetings,</h1>\n"
                    + "                            <p>Please login to start</p>\n"
                    + "                        </div>\n"
                    + "                        <form action=\"Login\" method=\"POST\" class=\"loginForm\">\n"
                    + "                            <div class=\"input-group\">\n"
                    + "                                <input type=\"text\" id=\"name\" name = \"user\" class=\"form-control mb-2\" value = \"");

            if (errologin != null) {
                out.println(errologin);
            }
            out.println("\" placeholder=\"Username\" maxlength = \"30\">\n"
                    + "\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                    </div>\n"
                    + "\n"
                    + "                    <div class=\"search-box\">\n"
                    + "\n"
                    + "                        <div class=\"input-group\">\n"
                    + "\n"
                    + "                            <input type=\"password\" id=\"paw\" name = \"senha\" class=\"form-control mb-4\" placeholder=\"Password\" maxlength = \"16\">\n"
                    + "\n"
                    + "                        </div>\n"
            );

            if (errologin != null) {
                out.println("<p>User or password incorrect.</p>");
            }

            out.println("\n"
                    + "                    </div>\n"
                    + "\n"
                    + "                    <div class=\"search-box\">\n"
                    + "\n"
                    + "                        <div class=\"input-group\">\n"
                    + "\n"
                    + "                            <input type=\"submit\" id=\"submit\" class=\"btn btn-primary\" value=\"Login\">\n"
                    + "\n"
                    + "                        </div>\n"
                    + "                        </form>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"input-group\">\n"
                    + "\n"
                    + "                        <a id=\"new_acc\" class =\"ml-1\" href = \"Cadastro\">Doesn't have an account? Create a new account!</a>\n"
                    + "\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "\n"
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
        //processRequest(request, response);
        String usuario = request.getParameter("user");
        String senha = request.getParameter("senha");

        LoginObject logObj = new LoginObject();
        logObj.setUsuario(usuario);
        logObj.setSenha(senha);

        try {
            boolean log = Banco.login(logObj);

            if (log) {
                HttpSession sessao = request.getSession();
                sessao.setAttribute("logado", new Boolean(true));
                sessao.setAttribute("user", usuario);
                
                response.sendRedirect("ChatPrincipal");
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Cookie errologin = new Cookie("errologin", request.getParameter("user"));
                errologin.setMaxAge(1);
                response.addCookie(errologin);
                response.sendRedirect("Login");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
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
