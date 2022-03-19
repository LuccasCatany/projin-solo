/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projetoIntegrador.dao;

import br.com.projetoIntegrador.entidade.Campeonato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import br.com.projetoIntegrador.entidade.Endereco;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author luccas.espinola
 */
public class EnderecoDaoImpl {

    ParticipanteDaoImpl participanteDaoImpl = new ParticipanteDaoImpl();
    CampeonatoDaoImpl campeonatoDaoImpl = new CampeonatoDaoImpl();
    private ResultSet resultado;

    public void salvarParticipante(Endereco endereco, int id, Connection conexao) {
        String sql = "INSERT INTO endereco(logradouro, bairro, cidade, estado, cep, complemento, participante_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
        salvar(endereco, id, sql, conexao);

    }

    public void salvarCampeonato(Endereco endereco, int id, Connection conexao) {
        String sql = "INSERT INTO endereco(logradouro, bairro, cidade, estado, cep, complemento, campeonato_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
        salvar(endereco, id, sql, conexao);

    }

    private void salvar(Endereco endereco, int id, String sql, Connection conexao) {
        PreparedStatement prepararSql;
        try {
            prepararSql = conexao.prepareStatement(sql);
            prepararSql.setString(1, endereco.getLogradouro());
            prepararSql.setString(2, endereco.getBairro());
            prepararSql.setString(3, endereco.getCidade());
            prepararSql.setString(4, endereco.getEstado());
            prepararSql.setString(5, endereco.getCep());
            prepararSql.setString(6, endereco.getComplemento());
            prepararSql.setInt(7, id);
            prepararSql.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao salvar o endereço " + e.getMessage());
        }
    }

    public void alterarEnderecoParticipante(Endereco endereco, int id, Connection conexao) {
        String sql = "UPDATE endereco SET logradouro=?, bairro = ?, cidade = ?, estado = ?, cep = ?, complemento = ?  WHERE participante_id = ? ";
        salvar(endereco, id, sql, conexao);

    }

    public void alterarEnderecoCampeonato(Endereco endereco, int id, Connection conexao) {
        String sql = "UPDATE endereco SET logradouro=?, bairro = ?, cidade = ?, estado = ?, cep = ?, complemento = ?  WHERE campeonato_id = ? ";
        alterar(endereco, id, sql, conexao);
    }

    private void alterar(Endereco endereco, int id, String sql, Connection conexao) {
        PreparedStatement prepararSql;
        try {
            prepararSql = conexao.prepareStatement(sql);
            prepararSql.setString(1, endereco.getLogradouro());
            prepararSql.setString(2, endereco.getBairro());
            prepararSql.setString(3, endereco.getCidade());
            prepararSql.setString(4, endereco.getEstado());
            prepararSql.setString(5, endereco.getCep());
            prepararSql.setString(6, endereco.getComplemento());
            prepararSql.setInt(7, id);
            prepararSql.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao alterar o endereço " + e.getMessage());
        }
    }

    public void excluirEnderecoParticipante(int id, Connection conexao) {
        String sql = "DELETE FROM endereco WHERE participante_id = ?";
        excluir(id, sql, conexao);
    }

    public void excluirEnderecoCampeonato(int id, Connection conexao) {
        String sql = "DELETE FROM endereco WHERE campeonato_id = ?";
        excluir(id, sql, conexao);
    }

    private void excluir(int id, String sql, Connection conexao) {
        PreparedStatement prepararSql;
        try {
            conexao = FabricaConexao.abrirConexao();
            prepararSql = conexao.prepareStatement(sql);
            prepararSql.setInt(1, id);
            prepararSql.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao excluir o endereço " + e.getMessage());
        }
    }

    
    public Endereco pesquisarPorCampeonato(Endereco endereco, int id, Connection conexao) {
        String sql = "SELECT * FROM endereco WHERE campeonato_id = ? ";
        endereco = pesquisar(endereco, id, sql, conexao);
        return endereco;
      
    }

    public Endereco pesquisarPorParticipante(Endereco endereco, int id, Connection conexao) {
        String sql = "SELECT * FROM endereco WHERE participante_id = ? ";
        endereco = pesquisar(endereco, id, sql, conexao);
        return endereco;
    }

    private Endereco pesquisar(Endereco endereco, int id, String sql, Connection conexao) {
        PreparedStatement prepararSql;
        endereco = new Endereco();
        try {
            prepararSql = conexao.prepareStatement(sql);
            prepararSql.setInt(1, id);
            resultado = prepararSql.executeQuery();

            if(resultado.next()){
                endereco.setId(resultado.getInt("id"));
                endereco.setLogradouro(resultado.getString("logradouro"));
                endereco.setBairro(resultado.getString("bairro"));
                endereco.setCidade(resultado.getString("cidade"));
                endereco.setEstado(resultado.getString("estado"));
                endereco.setCep(resultado.getString("cep"));
                endereco.setComplemento(resultado.getString("complemento"));
                
            }   
            
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar o endereço " + e.getMessage());
        }
        return endereco;
    }


}
