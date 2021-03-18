/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos;

import java.util.Date;

public class PessoaJuridica extends Cliente {
    
    private String cnpj;
    
    public PessoaJuridica() {
    }    
    
    public PessoaJuridica(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String getID() {
        return this.cnpj;
    }

    @Override
    public void setID(String id) {
        this.cnpj = id;
    }

    @Override
    public Date getDataNascimento() {
        return null;
    }

    @Override
    public void setDataNascimento(Date nascimento) {
        
    }
    
    
}
