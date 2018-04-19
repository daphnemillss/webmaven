/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import banco.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import object.CadObject;
import object.LoginObject;
import object.MsgObject;

/**
 *
 * @author caiot
 */
public class Banco {

    public static boolean login(LoginObject login) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM public.\"user\" WHERE usuario=? AND senha=?";
        if (Conexao.acessaBD()) {
            Connection con = Conexao.getConnection();
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1, login.getUsuario());
            psmt.setString(2, login.getSenha());
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                psmt.close();
                rs.close();
                con.close();

                return true;
            } else {
                psmt.close();
                rs.close();
                con.close();

                return false;
            }

        } else {
            return false;
        }
    }

    public static boolean cadastro(CadObject cadastro) throws SQLException {
        String sql = "INSERT INTO public.\"user\" VALUES (?, ?, ?)";
        if (Conexao.acessaBD()) {
            Connection con = Conexao.getConnection();
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1, cadastro.getUsuario());
            psmt.setString(2, cadastro.getEmail());
            psmt.setString(3, cadastro.getSenha());
            //System.out.println(cadastro.getUsuario() + "/" + cadastro.getEmail() + "/" + cadastro.getSenha());

            int i = psmt.executeUpdate();
            if (i != 0) {
                psmt.close();
                con.close();
                System.out.println(i);

                return true;
            } else {
                psmt.close();
                con.close();

                return false;
            }

        } else {
            return false;
        }
    }

    public static boolean escreveMensagem(MsgObject msg) throws SQLException {
        String sql = "INSERT INTO public.\"mensagem\" values (?, ?, ?, ?)";

        if (Conexao.acessaBD()) {
            Connection con = Conexao.getConnection();
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1, msg.getFrom());
            psmt.setString(2, msg.getMessage());
            psmt.setString(3, msg.getTo());
            psmt.setTimestamp(4, msg.getDate());

            int i = psmt.executeUpdate();
            if (i != 0) {
                psmt.close();
                con.close();

                return true;
            } else {
                psmt.close();
                con.close();

                return false;
            }

        } else {
            return false;
        }
    }

    public static ArrayList<String> getContatos(String user) throws SQLException {
        if (user == null) {
            return null;
        }

        if (Conexao.acessaBD()) {
            ArrayList<String> contatos = new ArrayList<>();
            String sql = "SELECT usuario FROM public.\"user\" where usuario != '" + user + "'";
            Connection con = Conexao.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                contatos.add(rs.getString("usuario"));
            }
            con.close();
            st.close();
            rs.close();
                
            return contatos;
        }

        return null;
    }

    public static ResultSet buscaMensagens(String user1, String user2) throws SQLException {
        if(user1 == null || user2 == null){
            return null;
        }
        
        if (Conexao.acessaBD()) {
            String sql = "select * from public.\"mensagem\" where (remetente = ? or remetente = ?) and (destinatario = ? or destinatario = ?) order by data asc";
            Connection con = Conexao.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user1);
            st.setString(2, user2);
            st.setString(3, user1);
            st.setString(4, user2);
            
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                
                return rs;
            } else {
                st.close();
                con.close();
                
                return null;
            }
        }
        
        return null;
    }
}
