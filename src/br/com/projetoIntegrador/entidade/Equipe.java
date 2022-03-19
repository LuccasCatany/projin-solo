/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projetoIntegrador.entidade;

/**
 *
 * @author maria
 */
public class Equipe {
    private Integer id;
    private String nome;
    
    private Campeonato campeonato;//composição quando salvo a equipe aponto um campeonato

    
    public Equipe(){
        
    }
    
    public Equipe(String nome) {
        this.nome = nome;
    }

   
    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }
        
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getNome(); //To change body of generated methods, choose Tools | Templates.
    }
    

}
