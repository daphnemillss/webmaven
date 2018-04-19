package banco;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author caiot
 */
public class Conexao {
    private static Connection connection = null;
    private static PreparedStatement stdados = null;
    private static ResultSet rsdados = null;
    
    public static final boolean acessaBD(){  
        try {
            String usuario = "postgres";
            String senha = "1602";
            
            Class.forName("org.postgresql.Driver");
            String urlconexao = "jdbc:postgresql://localhost/Chat";

            setConnection(DriverManager.getConnection(urlconexao, usuario, senha));
            
            getConnection().setAutoCommit(true);

        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Falha ao carregar o driver JDBC/ODBC:" +erro, "Erro de carregamento de driver", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de SQL:" +erro, "Erro de SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * @return the connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * @return the stdados
     */
    public static PreparedStatement getStdados() {
        return stdados;
    }

    /**
     * @return the rsdados
     */
    public static ResultSet getRsdados() {
        return rsdados;
    }

    /**
     * @param aConnection the connection to set
     */
    public static void setConnection(Connection aConnection) {
        connection = aConnection;
    }

    /**
     * @param aStdados the stdados to set
     */
    public static void setStdados(PreparedStatement aStdados) {
        stdados = aStdados;
    }

    /**
     * @param aRsdados the rsdados to set
     */
    public static void setRsdados(ResultSet aRsdados) {
        rsdados = aRsdados;
    }
}
