/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projetoIntegrador.dao;

import br.com.projetoIntegrador.entidade.Equipe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author maria
 */
public class EquipeDaoImpl {

    private Connection conexao;
    private PreparedStatement preparaSql;
    private ResultSet resultado;

    public void salvar(Equipe equipe) throws SQLException {
        String sql = "INSERT INTO equipe(nome, campeonato_id) VALUES(?, ?) ";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setString(1, equipe.getNome());
            preparaSql.setInt(2, equipe.getCampeonato().getId());
            preparaSql.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao salvar a equipe " + e.getMessage());
        } finally {
            conexao.close();
            preparaSql.close();
        }
    }

    public void alterar(Equipe equipe) throws SQLException {
        String sql = "UPDATE equipe SET nome = ?, campeonato_id = ? WHERE id = ?";
        try {

            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setString(1, equipe.getNome());

            CampeonatoDaoImpl campeonatoDaoImpl = new CampeonatoDaoImpl();
            equipe.setCampeonato(campeonatoDaoImpl.pesquisarPorId(equipe.getCampeonato().getId()));

            preparaSql.setInt(2, equipe.getCampeonato().getId());
            preparaSql.setInt(3, equipe.getId());

            preparaSql.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao alterar equipe " + e.getMessage());
        } finally {
            conexao.close();
            preparaSql.close();
            resultado.close();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM equipe WHERE id = ?";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setInt(1, id);
            preparaSql.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao excluir o equipe" + e.getMessage());
        } finally {
            conexao.close();
            preparaSql.close();
        }
    }

    public Equipe pesquisarEquipePorIdEquipe(int id) throws SQLException {
        String sql = "SELECT * FROM equipe WHERE id = ?";
        Equipe equipe = null;
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setInt(1, id);
            resultado = preparaSql.executeQuery();
            if (resultado.next()) {
                equipe = new Equipe();
                equipe.setId(id);
                equipe.setNome(resultado.getString("nome"));

                CampeonatoDaoImpl campeonatoDaoImpl = new CampeonatoDaoImpl();
                equipe.setCampeonato(campeonatoDaoImpl.pesquisarPorId(resultado.getInt("campeonato_id")));

            }

        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por id do participante " + e.getMessage());
        } finally {
            conexao.close();
            preparaSql.close();
            resultado.close();
        }

        return equipe;
    }
    
     public List<Equipe> pesquisarEquipes(Equipe equipe) {
        String sql = "SELECT * FROM equipe";
        List<Equipe> equipes = new ArrayList<>();
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            resultado = preparaSql.executeQuery();

            while (resultado.next()) {
                equipe = new Equipe();
                equipe.setId(resultado.getInt("id"));
                equipe.setNome(resultado.getString("nome"));
                equipes.add(equipe);
            }

        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por equipes " + e.getMessage());
        }
        return equipes;
    }

    
    //usado no lêTabelaEquipe
    public List<Equipe> pesquisarEquipePorNomeEquipe(String nome) throws SQLException {
        String sql = "SELECT * FROM equipe WHERE nome LIKE ?";
        Equipe equipe;
        List<Equipe> equipes = new ArrayList<>();
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setString(1, "%" + nome + "%");
            resultado = preparaSql.executeQuery();

            while (resultado.next()) {

                equipe = new Equipe();
                equipe.setId(resultado.getInt("id"));
                equipe.setNome(resultado.getString("nome"));

                CampeonatoDaoImpl campeonatoDaoImpl = new CampeonatoDaoImpl();
                equipe.setCampeonato(campeonatoDaoImpl.pesquisarPorId(resultado.getInt("campeonato_id")));

                equipes.add(equipe);
            }
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por nome de equipe " + e.getMessage());
        }
        return equipes;
    }
    //usado no excluiCampeonato
    public List<Equipe> pesquisarEquipesPorIdCampeonato(int id) {
        String sql = "SELECT * FROM equipe WHERE campeonato_id = ?";
        Equipe equipe;
        List<Equipe> equipes = new ArrayList<>();
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setInt(1, id);
            resultado = preparaSql.executeQuery();

            while (resultado.next()) {
                equipe = new Equipe();
                equipe.setId(resultado.getInt("id"));
                equipe.setNome(resultado.getString("nome"));
                
                CampeonatoDaoImpl campeonatoDaoImpl = new CampeonatoDaoImpl();
                equipe.setCampeonato(campeonatoDaoImpl.pesquisarPorId(id));
                
                equipes.add(equipe);
            }

        } catch (Exception e) {
            System.out.println("Erro ao pesquisar por equipes " + e.getMessage());
        }
        return equipes;
    }

    //Tentativa sistema de sorteio
    public List<Equipe> sorteiaEquipes(List<Equipe> equipes) {

        Collections.shuffle(equipes);
        return equipes;
    }

}
