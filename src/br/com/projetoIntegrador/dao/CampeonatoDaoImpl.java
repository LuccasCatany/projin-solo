package br.com.projetoIntegrador.dao;

import br.com.projetoIntegrador.entidade.Campeonato;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CampeonatoDaoImpl {

    private Connection conexao;
    private PreparedStatement preparaSql;
    private ResultSet resultado;

    public void salvar(Campeonato campeonato) throws SQLException {
        String sql = "INSERT INTO campeonato(nome, data) VALUES(?, ?) ";

        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparaSql.setString(1, campeonato.getNomeCampeonato());
            preparaSql.setDate(2, new Date(campeonato.getDataCampeonato().getTime()));
            preparaSql.executeUpdate();
            resultado = preparaSql.getGeneratedKeys();//essa parte e o return keys é pra quando for salvar ao mesmo tempo ≧◠‿●‿◠≦
            resultado.next();
            campeonato.setId(resultado.getInt(1));

            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.salvarCampeonato(campeonato.getEndereco(), campeonato.getId(), conexao);

        } catch (Exception e) {
            System.out.println("Erro ao salvar o campeonato " + e.getMessage());
        } finally {
            conexao.close();
            preparaSql.close();
        }
    }

    public void alterar(Campeonato campeonato) throws SQLException {
        String sql = "UPDATE campeonato SET nome = ?, data = ? WHERE id = ?";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setString(1, campeonato.getNomeCampeonato());
            preparaSql.setDate(2, new Date(campeonato.getDataCampeonato().getTime()));
            preparaSql.setInt(3, campeonato.getId());
            preparaSql.executeUpdate();

            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.alterarEnderecoCampeonato(campeonato.getEndereco(), campeonato.getId(), conexao);

        } catch (Exception e) {
            System.out.println("Erro ao alterar o campeonato" + e.getMessage());
        } finally {
            conexao.close();
            preparaSql.close();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM campeonato WHERE id = ?";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setInt(1, id);
            
            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.excluirEnderecoCampeonato(id, conexao);
            preparaSql.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao excluir o campeonato" + e.getMessage());
        }
    }

    public List<Campeonato> pesquisarCampeonatoPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM campeonato WHERE nome LIKE ?";
        List<Campeonato> campeonatos = new ArrayList<>();

        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setString(1, "%" + nome + "%");
            resultado = preparaSql.executeQuery();

            while (resultado.next()) {
                Campeonato campeonato = new Campeonato();
                campeonato.setId(resultado.getInt("id"));
                campeonato.setNomeCampeonato(resultado.getString("nome"));
                campeonato.setDataCampeonato(resultado.getDate("data"));

                EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
                campeonato.setEndereco(enderecoDaoImpl.pesquisarPorCampeonato(campeonato.getEndereco(), campeonato.getId(), conexao));

                campeonatos.add(campeonato);
            }

        } catch (Exception e) {
            System.out.println("Erro ao pesquisar campeonato por nome " + e.getMessage());

        } finally {
            conexao.close();
            preparaSql.close();
            resultado.close();
        }
        return campeonatos;
    }
    
    public List<Campeonato> pesquisarCampeonatos(Campeonato campeonato){
        String sql = "SELECT * FROM campeonato";
        List<Campeonato> campeonatos = new ArrayList<>();
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            resultado = preparaSql.executeQuery();
            
             while(resultado.next()){
                campeonato = new Campeonato();
                campeonato.setId(resultado.getInt("id"));
                campeonato.setNomeCampeonato(resultado.getString("nome"));
                campeonato.setDataCampeonato(resultado.getDate("data"));
                
                EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
                campeonato.setEndereco(enderecoDaoImpl.pesquisarPorCampeonato(campeonato.getEndereco(), campeonato.getId(), conexao));
                campeonatos.add(campeonato);
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por campeonatos " + e.getMessage());
        }
        return campeonatos;
    }
    
    

    public Campeonato pesquisarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM campeonato WHERE id = ?";
        Campeonato campeonato = null;
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setInt(1, id);
            resultado = preparaSql.executeQuery();
            if (resultado.next()) {
                campeonato = new Campeonato();
                campeonato.setId(id);
                campeonato.setNomeCampeonato(resultado.getString("nome"));
                campeonato.setDataCampeonato(resultado.getDate("data"));

                EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
                campeonato.setEndereco(enderecoDaoImpl.pesquisarPorCampeonato(campeonato.getEndereco(), campeonato.getId(), conexao));

            }

        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por id do campeonato " + e.getMessage());
        } finally {
            conexao.close();
            preparaSql.close();
            resultado.close();
        }
        return campeonato;
    }
}
