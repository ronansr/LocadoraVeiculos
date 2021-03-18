/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos;

public class Veiculo {
    private String placa;
    private String modelo;
    private String marca;
    private int ano;
    private String cor;
    private int capacidade;
    private String tipo;
    private float diaria;
    private boolean disponivel;

    public Veiculo(){
        
    }
    
    public Veiculo( String placa, String modelo, String marca, int ano, String cor, int capacidade, String tipo, float diaria){
        this.setPlaca(placa);
        this.setModelo(modelo);
        this.setMarca(marca);
        this.setAno(ano);
        this.setCor(cor);
        this.setCapacidade(capacidade);
        this.setTipo(tipo);
        this.setDiaria(diaria);
        disponivel = true;
    }
    
    public Veiculo( String placa, String modelo){
        this.setPlaca(placa);
        this.setModelo(modelo);
    }
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getDiaria() {
        return diaria;
    }

    public void setDiaria(float diaria) {
        this.diaria = diaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    
    
    public void imprime(){
        System.out.println("Placa: " + this.getPlaca());
        System.out.println("Modelo: " + this.getModelo());
        System.out.println("Marca: " + this.getMarca());
        System.out.println("Cor: " + this.getCor());
        System.out.println("Capacidade: " + this.getCapacidade());
        System.out.println("Ano: " + this.getAno());

    }

    
}
