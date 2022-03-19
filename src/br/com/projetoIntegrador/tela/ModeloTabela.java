/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projetoIntegrador.tela;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author maria
 */

 //Monta tabela com as colunas e linhas
public class ModeloTabela extends AbstractTableModel{
        private ArrayList linhas = null;
        private String[] colunas = null;
        
        public ModeloTabela(){
            
        }
        
        public ModeloTabela(ArrayList linha, String[] coluna){
            setLinhas(linha);
            setColunas(coluna);
        }
        
        public ArrayList getLinhas(){
            return linhas;
        }

        public void setLinhas(ArrayList dados){
            this.linhas = dados;
        }
        
         public String[] getColunas(){
            return colunas;
        }

        public void setColunas(String[] nomes){
            this.colunas = nomes;
        }
        //contagem do numero de colunas
        public int getColumnCount(){
            return colunas.length;
                    
        }
        public int getRowCount(){
            return linhas.size();
        }

        public String getColumnName(int numColunas){
            return colunas[numColunas];
        }

        public Object getValueAt(int numLinhas, int numColunas){
            Object[] linha = (Object[])getLinhas().get(numLinhas);
            return linha[numColunas];
        }
        
       

       
}

