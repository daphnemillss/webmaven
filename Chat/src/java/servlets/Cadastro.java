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
import object.CadObject;
import org.postgresql.util.PSQLException;

/**
 *
 * @author caiot
 */
@WebServlet(name = "Cadastro", urlPatterns = {"/Cadastro"})
public class Cadastro extends HttpServlet {

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
        
        Cookie[] cookies = request.getCookies();
        
        boolean emailvazio = false, uservazio = false, senhavazia = false, senhasdiferentes = false, errocadastro = false, cadastroduplicado = false;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if(c.getName().equals("emailvazio")){
                    emailvazio = true;
                }
                if(c.getName().equals("uservazio")){
                    uservazio = true;
                }
                if(c.getName().equals("senhavazia")){
                    senhavazia = true;
                }
                if(c.getName().equals("senhasdiferentes")){
                    senhasdiferentes = true;
                }
                if(c.getName().equals("errocadastro")){
                    errocadastro = true;
                }
                if(c.getName().equals("cadastroduplicado")){
                    cadastroduplicado = true;
                }
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>\n"
                    + "\n"
                    + "<html lang = \"en-us\">\n"
                    + "	<head>\n"
                    + "            <meta charset=\"UTF-8\">\n"
                    + "      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "      \n"
                    + "      <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                    + "      <link rel = \"stylesheet\" href = \"css/style.css\">\n"
                    + "	  <script type = \"text/javascript\" src = \"js/jquery-3.2.1.min\"></script>\n"
                    + "	</head>\n"
                    + "   \n"
                    + "   <body>\n"
                    + "	  <div class=\"container\">\n"
                    + "				\n"
                    + "		<div class = \"divLogin row\">\n"
                    + "			<div class=\"col-md-4\">\n"
                    + "			</div>\n"
                    + "			\n"
                    + "			<div class=\"col-md-4 text-center\">\n"
                    + "			<div class=\"search-box\">\n"
                    + "				<div class=\"caption mt-3\">\n"
                    + "					<h1>Welcome!</h1>\n"
                    + "					<p>Please identify yourself</p>\n"
                    + "				</div>\n"
                    + "				<form action=\"Cadastro\" method=\"POST\" class=\"loginForm\">\n"
                    + "					<div class=\"input-group\">\n"
                    + "						<input type=\"email\" id=\"email\" name = \"email\" class=\"form-control mb-4\" placeholder=\"Email\" maxlength = \"50\">\n"
                    + "						\n"
                    + "					</div>\n"
            );
            
            if(emailvazio){
                out.println("<p>Email cannot be empty!</p>");
            }
            
            out.println("				\n"
                    + "			</div>\n"
                    + "			\n"
                    + "			<div class=\"search-box\">\n"
                    + "				\n"
                    + "					<div class=\"input-group\">\n"
                    + "						\n"
                    + "						<input type=\"text\" id=\"user\" name = \"user\" class=\"form-control mb-4\" placeholder=\"Username\" maxlength = \"30\">\n"
                    + "						\n"
                    + "					</div>\n"
            );
                    
            if(uservazio){
                out.println("<p>User cannot be empty!</p>");
            }
            
            out.println("				\n"
                    + "			</div>\n"
                    + "			\n"
                    + "		\n"
                    + "			<div class=\"search-box\">\n"
                    + "				\n"
                    + "					<div class=\"input-group\">\n"
                    + "						\n"
                    + "						<input type=\"password\" id=\"senha\" name = \"senha\" class=\"form-control mb-4\" placeholder=\"Password\" maxlength = \"16\">\n"
                    + "						\n"
                    + "					</div>\n"
                    + "				\n"
                    + "			</div>\n"
                    + "			\n"
                    + "			<div class=\"search-box\">\n"
                    + "				\n"
                    + "					<div class=\"input-group\">\n"
                    + "						\n"
                    + "						<input type=\"password\" id=\"senha_conf\" name = \"senha_conf\" class=\"form-control mb-4\" placeholder=\"Confirm Password\" maxlength = \"16\">\n"
                    + "						\n"
                    + "					</div>\n"
            );
            
            if(senhavazia){
                out.println("<p>Password cannot be empty!</p>");
            }
            
            if(senhasdiferentes){
                out.println("<p>Passwords doesn't match!</p>");
            }
            
            if(cadastroduplicado){
                out.println("<p>The email or the username is already taken!</p>");
            }
            
            if(errocadastro){
                out.println("<p>Unknown error!</p>");
            }
            
            out.println("				\n"
                    + "			</div>\n"
                    + "		\n"
                    + "			<div class=\"search-box\">\n"
                    + "				\n"
                    + "					<div class=\"input-group\">\n"
                    + "						\n"
                    + "						<input type=\"submit\" id=\"register\" class=\"btn btn-primary\" value=\"Register\">\n"
                    + "						\n"
                    + "					</div>\n"
                    + "				</form>\n"
                    + "				\n"
                    + "				<a href=\"Login\">Go back</a>\n"
                    + "			</div>\n"
                    + "		</div>\n"
                    + "		</div>\n"
                    + "		\n"
                    + "	  </div>\n"
                    + "   </body>\n"
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
                String usuario = request.getParameter("user");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                String senha_conf = request.getParameter("senha_conf");
                int flag = 0;
                boolean cadastrou = false;

        if (usuario.equals("")) {
           Cookie c = new Cookie("uservazio", "");
           c.setMaxAge(1);
           response.addCookie(c);
           flag++;
        }
        if(email.equals("")){
           Cookie c = new Cookie("emailvazio", "");
           c.setMaxAge(1);
           response.addCookie(c);
           flag++;
        }
        if(senha.equals("") || senha_conf.equals("")){
           Cookie c = new Cookie("senhavazia", "");
           c.setMaxAge(1);
           response.addCookie(c);
           flag++;
        }
        if (!senha.equals(senha_conf)) {
           Cookie c = new Cookie("senhasdiferentes", "");
           c.setMaxAge(1);
           response.addCookie(c);
           flag++;
        } 
        
        if(flag != 0){
            //do nothing
        }
        else {
            CadObject cadobj = new CadObject();
            
            cadobj.setEmail(email);
            cadobj.setUsuario(usuario);
            cadobj.setSenha(senha);
            
            try {
                boolean cad = Banco.cadastro(cadobj);

                if (cad) {
                    
                    cadastrou = true;
                } else {
                    Cookie c = new Cookie("errocadastro", "");
                    c.setMaxAge(1);
                    response.addCookie(c);
                    cadastrou = false;
                }
            } catch (PSQLException ex) {
                Cookie c = new Cookie("cadastroduplicado", "");
                c.setMaxAge(1);
                response.addCookie(c);
                cadastrou = false;
                
            } catch (SQLException ex){
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(cadastrou){
            response.sendRedirect("Login");
        }else{
            response.sendRedirect("Cadastro");
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
