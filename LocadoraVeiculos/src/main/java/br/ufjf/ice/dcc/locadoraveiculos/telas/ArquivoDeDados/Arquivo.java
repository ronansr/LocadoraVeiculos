/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */

package br.ufjf.ice.dcc.locadoraveiculos.telas.ArquivoDeDados;

import br.ufjf.ice.dcc.locadoraveiculos.Atendente;
import br.ufjf.ice.dcc.locadoraveiculos.Cliente;
import br.ufjf.ice.dcc.locadoraveiculos.Endereco;
import br.ufjf.ice.dcc.locadoraveiculos.Gerente;
import br.ufjf.ice.dcc.locadoraveiculos.Locacao;
import br.ufjf.ice.dcc.locadoraveiculos.Locadora;
import br.ufjf.ice.dcc.locadoraveiculos.PessoaFisica;
import br.ufjf.ice.dcc.locadoraveiculos.PessoaJuridica;
import br.ufjf.ice.dcc.locadoraveiculos.Veiculo;
import br.ufjf.ice.dcc.locadoraveiculos.telas.Alugar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Arquivo {

    public static String read(String caminho) {
        String conteudo = "";
        try {
            FileReader arq = new FileReader(caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";
            try {
                linha = lerArq.readLine();
                while (linha != null) {
                    conteudo += linha;
                    linha = lerArq.readLine();
                }
                arq.close();
            } catch (IOException ex) {
                conteudo = "Erro: Não foi possível ler o arquivo!";
            }

        } catch (FileNotFoundException ex) {
            conteudo = "Erro: Arquivo não encontrado!";
        }
        if (conteudo.contains("Erro")) {
            return "";
        } else {
            return conteudo;
        }

    }

    public static boolean write(String caminho, String Text) {
        try {
            /* File way = new File("Arquivo" + File.separator + "DadosUniversitarios");
            if(!way.isDirectory()){
                way.mkdirs();
            }*/
            FileWriter arq = new FileWriter(caminho);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(Text);
            gravarArq.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void carregarDados() {
        Atendente[] atendentes = null;
        Gerente[] gerentes = null;
        List<Locacao> reservas = new ArrayList<>();
        Veiculo[] veiculos = null;
        List<Cliente> clientes = new ArrayList<>();

        Gson gson = new Gson();
        String json;

        json = Arquivo.read("atendentes.txt");
        if (!json.isEmpty()) {
            atendentes = gson.fromJson(json, Atendente[].class);
            System.out.println("atendentes " + atendentes.length);
        }

        json = Arquivo.read("gerentes.txt");
        if (!json.isEmpty()) {
            gerentes = gson.fromJson(json, Gerente[].class);
            System.out.println("gerentes " + gerentes.length);
        }

        json = Arquivo.read("reservas.txt");
        if (!json.isEmpty()) {
            /*java.lang.reflect.Type myType = new TypeToken<List<Locacao>>() {
            }.getType();
            reservas.addAll(gson.fromJson(json, myType));
            gson.
            System.out.println("reservas " + reservas.size());
             */
            JsonElement fileEl;
            try {
                fileEl = JsonParser.parseReader(new FileReader("reservas.txt"));
                JsonArray fileOb = fileEl.getAsJsonArray();
                Locacao locacao;
                Date dataInicio, dataFim;
                
                for (JsonElement obj : fileOb) {
                    System.out.println(obj.getAsJsonObject().get("cliente").getAsJsonObject().get("dataNascimento"));
                    if (obj.getAsJsonObject().get("cliente").getAsJsonObject().get("dataNascimento") != null) {
                        Cliente cliente = new PessoaFisica();
                        Veiculo veiculo = gson.fromJson(obj.getAsJsonObject().get("veiculo"), Veiculo.class);
                        cliente = gson.fromJson(obj.getAsJsonObject().get("cliente"), PessoaFisica.class);
                        dataInicio = gson.fromJson(obj.getAsJsonObject().get("dataInicio"), Date.class);
                        dataFim = gson.fromJson(obj.getAsJsonObject().get("dataFim"), Date.class);

                        locacao = new Locacao(
                                cliente,
                                veiculo,
                                dataInicio,
                                dataFim,
                                Integer.parseInt(obj.getAsJsonObject().get("periodoLocacao").getAsString()),
                                Float.parseFloat(obj.getAsJsonObject().get("total").getAsString())
                        );
                        reservas.add(locacao);

                    } else {
                        Cliente cliente = new PessoaJuridica();
                        Veiculo veiculo = gson.fromJson(obj.getAsJsonObject().get("veiculo"), Veiculo.class);
                        cliente = gson.fromJson(obj.getAsJsonObject().get("cliente"), PessoaJuridica.class);
                        dataInicio = gson.fromJson(obj.getAsJsonObject().get("dataInicio"), Date.class);
                        dataFim = gson.fromJson(obj.getAsJsonObject().get("dataFim"), Date.class);
                        
                        locacao = new Locacao(
                                cliente,
                                veiculo,
                                dataInicio,
                                dataFim,
                                Integer.parseInt(obj.getAsJsonObject().get("periodoLocacao").getAsString()),
                                Float.parseFloat(obj.getAsJsonObject().get("total").getAsString())
                        );
                        reservas.add(locacao);

                        //System.out.println("email" + cliente.getEmail());
                    }
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        json = Arquivo.read("veiculos.txt");
        if (!json.isEmpty()) {
            veiculos = gson.fromJson(json, Veiculo[].class);
            System.out.println("veiculos " + veiculos.length);
        }

        json = Arquivo.read("clientesPF.txt");
        if (!json.isEmpty()) {
            clientes.addAll(Arrays.asList(gson.fromJson(json, PessoaFisica[].class)));
        }

        json = Arquivo.read("clientesPJ.txt");
        if (!json.isEmpty()) {
            clientes.addAll(Arrays.asList(gson.fromJson(json, PessoaJuridica[].class)));
        }
        System.out.println("clientes " + clientes.size());

        Locadora.carregaDados(veiculos, clientes, atendentes, gerentes, reservas);
        System.out.println("Carregamento concluido com sucesso!");
    }

}
