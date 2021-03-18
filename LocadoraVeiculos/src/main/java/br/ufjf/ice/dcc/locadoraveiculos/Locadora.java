/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos;

import br.ufjf.ice.dcc.locadoraveiculos.telas.ArquivoDeDados.Arquivo;
import java.util.*;
import com.google.gson.Gson;

public class Locadora {
    private static List <Veiculo> veiculos = new ArrayList<>(); //todos os veiculos
    private static List <Locacao> reservas = new ArrayList<>(); //guarda todas as reversas de determinado veiculo
    private static List <Cliente> clientes = new ArrayList<>();
    private static List <Atendente> atendentes = new ArrayList<>(); 
    private static List <Gerente> gerentes = new ArrayList<>(); 
    private static Atendente atendenteLogado = new Atendente();
    private static Gerente gerenteLogado = new Gerente();
    private static Gson gson = new Gson();
    
    public static void adicionaVeiculo(Veiculo veiculo){
        veiculos.add(veiculo);
        salvarVeiculos();
    }

    public static List<Veiculo> getVeiculos() {
        return veiculos;
    }
    
    public static void adicionaReserva(Locacao reserva){
        reservas.add(reserva);
        salvarReservas();
    }

    public static List<Locacao> getReservas() {
        return reservas;
    }
    
    public static void adicionaCliente(Cliente cliente){
        clientes.add(cliente);
        salvarClientes();
    }

    public static List<Cliente> getCliente() {
        return clientes;
    }
    
    public static List<Atendente> getAtendente() {
        return atendentes;
    }
    
    public static List<Gerente> getGerente() {
        return gerentes;
    }
    
    //RETORNA SE UM VEICULO JA FOI CADASTRADO
    public static boolean verificaVeiculos(Veiculo veiculo){
        for (int i = 0; i < veiculos.size(); i++) {
            if(veiculos.get(i).getPlaca().equals(veiculo.getPlaca()))
                return true;
        }
        return false;
    }
    
    //RETORNA SE UMA PESSOA FÍSICA JA FOI CADASTRADO
    public static boolean verificaCliente(Cliente cliente){
        for (int i = 0; i < clientes.size(); i++) {
            if(clientes.get(i).getID().equals(cliente.getID()))
                return true;
        }
        return false;
    }
    
    public static boolean buscaUsuario(String id, String senha){
        if(id.equals("adm") && senha.equals("123")){
            return true;
        } else {
            for (int i = 0; i < atendentes.size(); i++) {
                System.out.println("entrou nos atendentes");
                if(atendentes.get(i).getID().equals(id)){
                    if(atendentes.get(i).usuario.validarUsuario(id, senha)){
                        System.out.println("Atendente logado com sucesso.");
                        atendenteLogado = atendentes.get(i);
                        return true;
                    }
                }  
            }
            for (int i = 0; i < gerentes.size(); i++) {
                System.out.println("entrou nos gerentes");
                if(gerentes.get(i).getID().equals(id)){
                    System.out.println("Encontrou fulano");
                    if(gerentes.get(i).usuario.validarUsuario(id, senha)){
                        System.out.println("Gerente logado com sucesso.");
                        gerenteLogado = gerentes.get(i);
                        return true;
                    }
                } 
            }
            return false;
        }
    }

    
    public static boolean verificaAtendente(Atendente funcionario){
        for (int i = 0; i < atendentes.size(); i++) {
            if(atendentes.get(i).getID().equals(funcionario.getID()))
                return true;
        }
        return false;
    }
    
    public static boolean verificaGerente(Gerente funcionario){
        for (int i = 0; i < gerentes.size(); i++) {
            if(gerentes.get(i).getID().equals(funcionario.getID()))
                return true;
        }
        return false;
    }
    
    public static Funcionario getLogado(){
        if(!atendenteLogado.getID().isEmpty()){
            return atendenteLogado;
        } else {
            return gerenteLogado;
        }
    }
    
    public static void adicionaAtendente(Atendente funcionario){
        atendentes.add(funcionario);
        salvarAtendentes();
        
    }
    
    public static void adicionaGerente(Gerente funcionario){
        System.out.println(funcionario.getID());
        gerentes.add(funcionario);
        salvarGerentes();
    }
    
    public static boolean estaDisponivel(String placa, Date dataInicio, Date dataFim){
        
        if(dataInicio.before(dataFim) || dataInicio.equals(dataFim)){
            for (int i = 0; i < reservas.size(); i++) {
                if(reservas.get(i).getVeiculo().getPlaca().equals(placa)){
                    if(reservas.get(i).getDataFim().equals(dataFim)){
                        System.out.println("data de fim igual a data da nova locacao");
                        return false;
                    } else if(reservas.get(i).getDataInicio().equals(dataInicio)){
                        return false;
                    } else if(reservas.get(i).getDataFim().equals(dataInicio)){
                        return false;
                    } else {
                        if(reservas.get(i).getDataFim().before(dataInicio)){
                            return true;
                        } else if(!reservas.get(i).getDataFim().after(dataInicio)){
                            return true;
                        }
                        
                        return false;
                    }
                }
            }
            return true;
        } else
            return false;
    }
    
    public static void deslogar(){
        gerenteLogado = new Gerente();
        atendenteLogado = new Atendente();
    }
    
    public static String getNomeLogado(){
        if(!gerenteLogado.getID().isEmpty())
            return gerenteLogado.getNome();
        else if(!atendenteLogado.getID().isEmpty())
            return atendenteLogado.getNome();
        return "";
    }
   
    public static void salvarGerentes(){
        if(Arquivo.write("gerentes.txt", gson.toJson(gerentes) ))
            System.out.println("Gerentes salvos com sucesso");
        else 
            System.out.println("Erro ao salvar Gerentes");
    }
    
    public static void salvarAtendentes(){
        
        if(Arquivo.write("atendentes.txt", gson.toJson(atendentes) ))
            System.out.println("atendentes salvo com sucesso");
        else 
            System.out.println("Erro ao salvar atendentes");
    }
    
    public static void salvarVeiculos(){
        
        if(Arquivo.write("veiculos.txt", gson.toJson(veiculos) ))
            System.out.println("veiculos salvo com sucesso");
        else 
            System.out.println("Erro ao salvar veiculos");
    }
    
    public static void salvarClientes(){
        List <Cliente> clientesPF = new ArrayList<>();
        List <Cliente> clientesPJ = new ArrayList<>();

        clientes.forEach((cliente) -> {
            if(cliente.getDataNascimento() != null)
                clientesPF.add(cliente);
            else 
                clientesPJ.add(cliente);
        });
        
        if(Arquivo.write("clientesPF.txt", gson.toJson(clientesPF) ))
            System.out.println("clientes salvo com sucesso");
        else 
            System.out.println("Erro ao salvar clientesPJ");
        
        if(Arquivo.write("clientesPJ.txt", gson.toJson(clientesPJ) ))
            System.out.println("clientesPJ salvo com sucesso");
        else 
            System.out.println("Erro ao salvar clientes");
    }
    
    public static void salvarReservas(){
        
        if(Arquivo.write("reservas.txt", gson.toJson(reservas) ))
            System.out.println("reservas salvo com sucesso");
        else 
            System.out.println("Erro ao salvar reservas");
    }
    
    public static void carregaDados(
            Veiculo [] veiculosSalvos, 
            List clientesSalvos, 
            Atendente [] atendentesSalvos, 
            Gerente [] gerentesSalvos, 
            List reservasSalvas 
    ){
        if(veiculosSalvos != null){
            veiculos.clear();
            veiculos.addAll(Arrays.asList(veiculosSalvos));
        }
        clientes = clientesSalvos;
        if(atendentesSalvos != null){
            atendentes.clear();
            atendentes.addAll(Arrays.asList(atendentesSalvos));
        }
            
        if(gerentesSalvos != null){
            gerentes.clear();
            gerentes.addAll(Arrays.asList(gerentesSalvos));
        }
            
        if(reservasSalvas != null)
            reservas = reservasSalvas;
        
    }
    
    public static void carregaListVeiculos(List veiculosSalvos){
        veiculos = veiculosSalvos;
    }
}
