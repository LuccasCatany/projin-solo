/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projetoIntegrador.dao;

import br.com.projetoIntegrador.entidade.Campeonato;
import br.com.projetoIntegrador.entidade.Equipe;
import br.com.projetoIntegrador.util.GeradorUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author luccas.espinola
 */
public class EquipeDaoImplTest {

    private Equipe equipe;
    private EquipeDaoImpl equipeDaoImpl;
    private Campeonato campeonato;

    public EquipeDaoImplTest() {
        equipeDaoImpl = new EquipeDaoImpl();
    }

//@Test 
    public void testSalvar() throws SQLException {
        System.out.println("salvar");
        equipe = new Equipe(
                GeradorUtil.gerarNome() + " Equipe"
        );
        CampeonatoDaoImpl campeonatoDaoImpl = new CampeonatoDaoImpl();
        List<Campeonato> campeonatos = campeonatoDaoImpl.pesquisarCampeonatoPorNome("Jhon silver");
        campeonato = campeonatos.get(0);
        equipe.setCampeonato(campeonato);
        mostrarEquipe(equipe);
        equipeDaoImpl.salvar(equipe);
    }

//    @Test
    public void salvandoVarios() throws SQLException {
        for (int i = 0; i < 10; i++) {
            testSalvar();
        }
    }

//    @Test 
    public void testeAlterar() throws SQLException {
        System.out.println("alterar");
        equipe = equipeDaoImpl.pesquisarEquipePorIdEquipe(1);
        equipe.setNome("Equipe testAlter");
        equipe.getCampeonato().setId(9);
        //CampeonatoDaoImpl campeonatoDaoImpl = new CampeonatoDaoImpl();
        // int idCampeonatoAtual = campeonatoDaoImpl.pesquisarPorId(equipe.getCampeonato().getId());

        equipeDaoImpl.alterar(equipe);
        mostrarEquipe(equipe);
    }

//    @Test
    public void testExcluir() throws Exception {
        System.out.println("excluir");
        equipeDaoImpl.excluir(3);
    }

//    @Test
    public void testPesquisarEquipePorIdEquipe() throws Exception {
        System.out.println("pesquisar por id");
        equipe = equipeDaoImpl.pesquisarEquipePorIdEquipe(4);
        mostrarEquipe(equipe);

    }

//    @Test
    public void testPesquisarPorNomeEquipe() throws Exception {
        System.out.println("Pesquisar por nome");
        List<Equipe> equipes = equipeDaoImpl.pesquisarEquipePorNomeEquipe("Tomori azuma Equipe");
        for (Equipe equipe1 : equipes) {
            mostrarEquipe(equipe1);
        }
    }

//    @Test
    public void testePesuisarCampeonatos() throws SQLException {
        System.out.println("Pesquisar por Equipes");
        int idCampeonato = 8;
        List<Equipe> equipes = equipeDaoImpl.pesquisarEquipesPorIdCampeonato(idCampeonato);
        for (Equipe equipe : equipes) {

            mostrarEquipe(equipe);
        }

    }

    //@Test
    public void testSorteiaDuasEquipes() throws SQLException {
        System.out.println("Sortear duas equipes");

        CampeonatoDaoImpl campeonatoDaoImpl = new CampeonatoDaoImpl();
        List<Campeonato> campeonatos = campeonatoDaoImpl.pesquisarCampeonatoPorNome("");
        campeonato = campeonatos.get(0);

    }

    //metodo auxiliar 
    private void mostrarEquipe(Equipe equipe) {
        System.out.println("Id: " + equipe.getId());
        System.out.println("Nome: " + equipe.getNome());
//        System.out.println("Id do campeonato: " + equipe.getCampeonato().getId());
//        System.out.println("Nome campeonato: " + equipe.getCampeonato().getNomeCampeonato());
//        System.out.println("Data campeonato: " + equipe.getCampeonato().getDataCampeonato());
//        System.out.println("Logradouro: " + equipe.getCampeonato().getEndereco().getLogradouro());
//        System.out.println("Bairro: " + equipe.getCampeonato().getEndereco().getBairro());
//        System.out.println("Cidade: " + equipe.getCampeonato().getEndereco().getCidade());
//        System.out.println("Estado: " + equipe.getCampeonato().getEndereco().getEstado());
//        System.out.println("Cep: " + equipe.getCampeonato().getEndereco().getCep());
//        System.out.println("Complemento: \n\n" + equipe.getCampeonato().getEndereco().getComplemento());

    }

}
