package br.com.projetoIntegrador.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class FabricaConexao {

    public static Connection abrirConexao() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // TODO mudar o nome do banco ali onde tem associacao
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dbintegrador?useTimezone=true&serverTimezone=America/Sao_Paulo&zeroDateTimeBehavior=convertToNull", 
                                                            "root", "admin");
        
        
    }
    
}