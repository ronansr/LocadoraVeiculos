/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos;

import java.util.Date;

//Parte do login
public class Usuario {
    private String id;
    private String senha;
    
    Usuario(){}
    
    Usuario(String id, String senha){
        this.setId(id.replaceAll("\\.", "").replaceAll("-", ""));
        this.setSenha(senha);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id.replaceAll("\\.", "").replaceAll("-", "");
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public boolean validarUsuario(String id, String senha){
        System.out.println(this.getId());
        if(this.getId().equals(id)){
            System.out.println("ID igual");
           if(this.getSenha().equals(senha)){
               System.out.println("Senha igual");
               return true; 
           }
                 
        }
            
        return false;
    }
    
}
