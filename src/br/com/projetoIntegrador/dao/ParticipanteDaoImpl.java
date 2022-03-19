package br.com.projetoIntegrador.dao;

import br.com.projetoIntegrador.entidade.Participante;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDaoImpl {

    private Connection conexao;
    private PreparedStatement preparaSql;
    private ResultSet resultado;

    //TODO CRUD ( Salvar, Pesquisar por id/nome , Alterar, Excluir)
    public void salvar(Participante participante) throws SQLException {
        String sql = "INSERT INTO participante(nome, cpf, telefone, dataNascimento, Equipe_id) VALUES(?, ?, ?, ?, ?) ";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparaSql.setString(1, participante.getNome());
            preparaSql.setString(2, participante.getCpf());
            preparaSql.setString(3, participante.getTelefone());
            preparaSql.setDate(4, new Date(participante.getNascimento().getTime()));
            preparaSql.setInt(5, participante.getEquipe().getId());
            preparaSql.executeUpdate();
            resultado = preparaSql.getGeneratedKeys();
            resultado.next();
            participante.setId(resultado.getInt(1));

            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.salvarParticipante(participante.getEndereco(), participante.getId(), conexao);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o participante " + e.getMessage());
        } finally {
            conexao.close();
            preparaSql.close();
            resultado.close();
        }

    }

    public void excluir(int id) {
        String sql = "DELETE FROM participante WHERE id = ?";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setInt(1, id);

            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.excluirEnderecoParticipante(id, conexao);
            preparaSql.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao excluir o participante" + e.getMessage());
        }
    }

    public Participante pesquisarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM participante WHERE nome LIKE ?";
        Participante participante = new Participante();

        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setString(1, "%"+ nome + "%");
            resultado = preparaSql.executeQuery();

            if (resultado.next()) {
                participante.setId(resultado.getInt("id"));
                participante.setNome(resultado.getString("nome"));
                participante.setCpf(resultado.getString("cpf"));
                participante.setTelefone(resultado.getString("telefone"));
                participante.setNascimento(resultado.getDate("dataNascimento"));

                EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
                participante.setEndereco(enderecoDaoImpl.pesquisarPorParticipante(participante.getEndereco(), participante.getId(), conexao));

                EquipeDaoImpl equipeDaoImpl = new EquipeDaoImpl();
                participante.setEquipe(equipeDaoImpl.pesquisarEquipePorIdEquipe(resultado.getInt("equipe_id")));
            }

        } catch (Exception e) {
            System.out.println("Erro ao pesquisar participante por nome " + e.getMessage());

        } finally {
            conexao.close();
            preparaSql.close();
            resultado.close();
        }
        return participante;
    }

    public void alterar(Participante participante) throws SQLException {
        String sql = "UPDATE participante SET nome = ?, cpf = ?, telefone = ?, dataNascimento = ?, equipe_id = ? WHERE id = ?";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setString(1, participante.getNome());
            preparaSql.setString(2, participante.getCpf());
            preparaSql.setString(3, participante.getTelefone());
            preparaSql.setDate(4, new Date(participante.getNascimento().getTime()));

            preparaSql.setInt(6, participante.getId());

            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.alterarEnderecoParticipante(participante.getEndereco(), participante.getId(), conexao);

            EquipeDaoImpl equipeDaoImpl = new EquipeDaoImpl();
            participante.setEquipe(equipeDaoImpl.pesquisarEquipePorIdEquipe(participante.getEquipe().getId()));
            preparaSql.setInt(5, participante.getEquipe().getId());
            preparaSql.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao alterar o participante" + e.getMessage());
        } finally {
            conexao.close();
            preparaSql.close();
        }
    }

    public List<Participante> pesquisarParticipantesPorEquipe(int id) throws SQLException {
        String sql = "SELECT * FROM participante WHERE equipe_id = ?";
        List<Participante> participantes = new ArrayList<>();

        try {

            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setInt(1, id);
            resultado = preparaSql.executeQuery();

            while (resultado.next()) {
                Participante participante = new Participante();
                participante.setId(resultado.getInt("id"));
                participante.setNome(resultado.getString("nome"));
                participante.setCpf(resultado.getString("cpf"));
                participante.setTelefone(resultado.getString("telefone"));
                participante.setNascimento(resultado.getDate("dataNascimento"));

                EquipeDaoImpl equipeDaoImpl = new EquipeDaoImpl();
                participante.setEquipe(equipeDaoImpl.pesquisarEquipePorIdEquipe(id));
                
                EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
                participante.setEndereco(enderecoDaoImpl.pesquisarPorParticipante(participante.getEndereco(), participante.getId(), conexao));
                
                participantes.add(participante);
            }
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar participantes da equipe" + e);
            
        } finally {
            conexao.close();
            preparaSql.close();
            resultado.close();
        }
        return participantes;
    }
    
    public List<Participante> pesquisarParticipantesPorNome(String nome) throws SQLException{
        String sql = "SELECT * FROM participante WHERE nome LIKE ?";
        List<Participante> participantes = new ArrayList<>();
        
        try {
            conexao = FabricaConexao.abrirConexao();
            preparaSql = conexao.prepareStatement(sql);
            preparaSql.setString(1, "%" + nome + "%");
            resultado = preparaSql.executeQuery();
            
            while (resultado.next()) {
                Participante participante = new Participante();
                participante.setId(resultado.getInt("id"));
                participante.setNome(resultado.getString("nome"));
                participante.setCpf(resultado.getString("cpf"));
                participante.setTelefone(resultado.getString("telefone"));
                participante.setNascimento(resultado.getDate("dataNascimento"));

                EquipeDaoImpl equipeDaoImpl = new EquipeDaoImpl();
                participante.setEquipe(equipeDaoImpl.pesquisarEquipePorIdEquipe(resultado.getInt("equipe_id")));
                
                EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
                participante.setEndereco(enderecoDaoImpl.pesquisarPorParticipante(participante.getEndereco(), participante.getId(), conexao));
                
                participantes.add(participante);
                
            }
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar participante por nome " + e.getMessage());
        }finally {
            conexao.close();
            preparaSql.close();
            resultado.close();
        }
        
        return participantes;
    }
    
}
