/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos;

public class Gerente extends Funcionario {
    
    public Gerente(){
        
    }
    
    Gerente(String cpf){
        this.setCpf(cpf);
    }

    @Override
    public int tipoFuncionario() {
        return 0;
    }

}
