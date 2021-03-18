/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos;

import java.util.Date;

public class PessoaFisica extends Cliente {

    private String cpf = "";
    private Date dataNascimento;

    public PessoaFisica() {
    }

    public PessoaFisica(String cpf, Date dataNascimento) {
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    /*public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }*/

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String getID() {
        return this.cpf;
    }

    @Override
    public void setID(String id) {
        this.cpf = id;
    }

    @Override
    public Date getDataNascimento() {
        return this.dataNascimento;
    }

    @Override
    public void setDataNascimento(Date nascimento) {
        this.dataNascimento = nascimento;
    }
   
}
