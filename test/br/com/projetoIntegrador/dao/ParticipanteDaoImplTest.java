/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projetoIntegrador.dao;

import br.com.projetoIntegrador.entidade.Endereco;
import br.com.projetoIntegrador.entidade.Equipe;
import br.com.projetoIntegrador.entidade.Participante;
import br.com.projetoIntegrador.util.GeradorUtil;
import java.sql.SQLException;
import org.junit.Test;
import java.util.Date;
import java.util.List;

/**
 *
 * @author maria
 */
public class ParticipanteDaoImplTest {

    private ParticipanteDaoImpl participanteDaoImpl;
    private Participante participante;

    public ParticipanteDaoImplTest() {
        participanteDaoImpl = new ParticipanteDaoImpl();
    }

//@Test
    public void testSalvar() throws SQLException {
        System.out.println("salvar");

        participante = new Participante(
                GeradorUtil.gerarNome(),
                GeradorUtil.gerarCpf(),
                GeradorUtil.gerarTelefone(),
                new Date()
        );
        Endereco endereco = new Endereco(
                GeradorUtil.gerarLogradouro(),
                GeradorUtil.gerarBairro(),
                GeradorUtil.gerarCidade(),
                GeradorUtil.gerarEstado(),
                GeradorUtil.gerarCep(),
                "Teste do ice"
        );
        EquipeDaoImpl equipeDaoImpl = new EquipeDaoImpl();
        int idEquipe = 36;
        participante.setEquipe(equipeDaoImpl.pesquisarEquipePorIdEquipe(idEquipe));

        participante.setEndereco(endereco);
//        participante.setEquipe(equipe);
        participanteDaoImpl.salvar(participante);
    }

//   @Test
    public void salvandoVarios() throws SQLException {
        for (int i = 0; i < 5; i++) {
            testSalvar();
        }
    }    
    
//      @Test
    public void testeAlterar() throws SQLException {
        System.out.println("Alterar :)");
        participante = participanteDaoImpl.pesquisarPorNome("Ankha inamoto");
        mostrarParticipanteEndereco(participante);
        participante.setNome("Junior alterado2");
        participante.setCpf(GeradorUtil.gerarCpf());
        participante.setTelefone(GeradorUtil.gerarTelefone());
        participante.setNascimento(new Date());

        participante.getEndereco().setLogradouro("Logradouro");
        participante.getEndereco().setBairro("Bairro");
        participante.getEndereco().setEstado("Estado");
        participante.getEndereco().setCidade("Cidade");
        participante.getEndereco().setCep("Cep alt");
        participante.getEndereco().setComplemento("Complemento");

        participante.getEquipe().setId(2);
        participanteDaoImpl.alterar(participante);

    }

//       @Test
    public void testeExcluir() {
        System.out.println("Excluir :)");
        int i = 1;
        participanteDaoImpl.excluir(i);
    }

    //   @Test
    public void testePesquisarPorNome() throws SQLException {
        System.out.println("Pesquisar por nome");
        participante = participanteDaoImpl.pesquisarPorNome("Roberto satsuki");
        mostrarParticipanteEndereco(participante);
    }

    public void mostrarParticipanteEndereco(Participante participante) {
        System.out.println("ID " + participante.getId());
        System.out.println("Nome " + participante.getNome());
        System.out.println("Cpf " + participante.getCpf());
        System.out.println("Telefone " + participante.getTelefone());
        System.out.println("Data Nascimento " + participante.getNascimento());
        System.out.println("Logradouro " + participante.getEndereco().getLogradouro());
        System.out.println("Bairro " + participante.getEndereco().getBairro());
        System.out.println("Cidade " + participante.getEndereco().getCidade());
        System.out.println("Estado " + participante.getEndereco().getEstado());
        System.out.println("Cep " + participante.getEndereco().getCep());
        System.out.println("Complemento " + participante.getEndereco().getComplemento());
        System.out.println("Nome da Equipe " + participante.getEquipe().getNome() + "\n");
    }

    //@Test
    public void pesquisarParticipantesDaEquipe() throws SQLException {
        System.out.println("Pesquisar participantes da equipe ");

        List<Participante> participantes = participanteDaoImpl.pesquisarParticipantesPorEquipe(1);

        for (Participante participante1 : participantes) {
            mostrarParticipanteEndereco(participante1);
        }

    }
    
    @Test
    public void testPesquisarParticipantesPorNome() throws SQLException{
        System.out.println("Pesquisar por nome retornando lista");
        List<Participante> participantes = participanteDaoImpl.pesquisarParticipantesPorNome("Jenny");
        for (Participante participante : participantes) {
            mostrarParticipanteEndereco(participante);
        }
    }
    
}
