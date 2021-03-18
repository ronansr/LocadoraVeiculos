/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos;

import java.util.Date;

//Mantem um registro de uma reserva
public class Locacao {
    private Cliente cliente;
    private Veiculo veiculo;
    private Date dataInicio = new Date();
    private Date dataFim = new Date();
    private int periodoLocacao;
    private float total;
    
    public Locacao(){
      //settar cliente e veiculos recebendo como parametro  
    };

    public Locacao(float total, int periodoLocacao) {
        this.total = total;
        this.periodoLocacao = periodoLocacao;
    }

    public Locacao(Cliente cliente, Veiculo veiculo, Date dataInicio, Date dataFim, int periodoLocacao, float total) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.periodoLocacao = periodoLocacao;
        this.total = total;
    }
    
    public Cliente getCliente(){
        return this.cliente;
    }
    
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Veiculo getVeiculo(){
        return this.veiculo;
    }
    
    public void setVeiculo(Veiculo veiculo){
        this.veiculo = veiculo;
    }

    public float getTotal() {
        return total;
    }

    public int getPeriodoLocacao() {
        return periodoLocacao;
    }
    
    
    
}
