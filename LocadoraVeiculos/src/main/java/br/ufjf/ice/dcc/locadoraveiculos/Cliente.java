/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos;
import java.util.Date;

/**
 *
 * @author lucca
 */

//Precisa ter metodo abstrato
public abstract class Cliente {
    private String nome;
    private Endereco endereco;
    private String email;
    private String telefone;

    
    public String getNome(){
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public abstract String getID();
    public abstract void setID(String id);
    public abstract Date getDataNascimento();
    public abstract void setDataNascimento(Date nascimento);
    

}
