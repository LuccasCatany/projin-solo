/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projetoIntegrador.dao;

import br.com.projetoIntegrador.entidade.Campeonato;
import br.com.projetoIntegrador.entidade.Endereco;
import br.com.projetoIntegrador.util.GeradorUtil;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author luccas.espinola
 */
public class CampeonatoDaoImplTest {

    private CampeonatoDaoImpl campeonatoDaoImpl;
    private Campeonato campeonato;
    private Endereco endereco;

    public CampeonatoDaoImplTest() {
        campeonatoDaoImpl = new CampeonatoDaoImpl();
    }

//@Test
    public void testSalvar() throws SQLException {
        System.out.println("salvar");
        campeonato = new Campeonato(
                GeradorUtil.gerarNome(),
                new Date()
        );
        Endereco endereco = new Endereco(GeradorUtil.gerarLogradouro(),
                GeradorUtil.gerarBairro(),
                GeradorUtil.gerarCidade(),
                GeradorUtil.gerarEstado(),
                GeradorUtil.gerarCep(),
                "Teste"
        );
        campeonato.setEndereco(endereco);
        campeonatoDaoImpl.salvar(campeonato);

    }

//    @Test
    public void salvandoVarios() throws SQLException {
        for (int i = 0; i < 10; i++) {
            testSalvar();
        }
    }

    //@Test
    public void testeAlterar() throws SQLException {
        System.out.println("Alterar :)");
        List<Campeonato> campeonatos = campeonatoDaoImpl.pesquisarCampeonatoPorNome("");
        campeonato = campeonatos.get(0);
        
        mostrarCampeonatoEndereco(campeonato); // So pra verificar se o endereço esta vindo
        campeonato.setNomeCampeonato("Junior alterado2");
        campeonato.setDataCampeonato(new Date());
        campeonato.getEndereco().setLogradouro("Logradouro");
        campeonato.getEndereco().setBairro("Bairro");
        campeonato.getEndereco().setEstado("Estado");
        campeonato.getEndereco().setCidade("Cidade");
        campeonato.getEndereco().setCep("Cep alt");
        campeonato.getEndereco().setComplemento("Complemento");

        campeonatoDaoImpl.alterar(campeonato);

    }

//    @Test
    public void testePesquisarPorNome() throws SQLException {
        System.out.println("Pesquisar por nome");
        List<Campeonato> campeonatos = campeonatoDaoImpl.pesquisarCampeonatoPorNome("");
        campeonato = campeonatos.get(0);
        mostrarCampeonatoEndereco(campeonato);
    }

    //@Test
    public void testePesquisarPorId() throws SQLException {
        System.out.println("Pesquisar por id");
        campeonato = campeonatoDaoImpl.pesquisarPorId(6);
        mostrarCampeonatoEndereco(campeonato);
    }

//       @Test
    public void testeExcluir() {
        System.out.println("Excluir :)");
        int i = 1;
        campeonatoDaoImpl.excluir(i);
    }
    
       //@Test
    public void testePesuisarCampeonatos() {
        System.out.println("Pesquisar por campeonatos");
        List<Campeonato> campeonatos = campeonatoDaoImpl.pesquisarCampeonatos(campeonato);
        for(Campeonato camp : campeonatos) {
            mostrarCampeonatoEndereco(camp);
        }
        
        
    }

    public void mostrarCampeonatoEndereco(Campeonato campeonato) {
        System.out.println("ID " + campeonato.getId());
        System.out.println("Nome " + campeonato.getNomeCampeonato());
        System.out.println("Data " + campeonato.getDataCampeonato());
        System.out.println("Logradouro " + campeonato.getEndereco().getLogradouro());
        System.out.println("Bairro " + campeonato.getEndereco().getBairro());
        System.out.println("Cidade " + campeonato.getEndereco().getCidade());
        System.out.println("Estado " + campeonato.getEndereco().getEstado());
        System.out.println("Cep " + campeonato.getEndereco().getCep());
        System.out.println("Complemento \n\n" + campeonato.getEndereco().getComplemento());
    }

}
