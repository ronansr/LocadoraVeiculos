/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos.telas;

import br.ufjf.ice.dcc.locadoraveiculos.Locadora;
import br.ufjf.ice.dcc.locadoraveiculos.PagamentoCartao;
import br.ufjf.ice.dcc.locadoraveiculos.PagamentoDinheiro;
import br.ufjf.ice.dcc.locadoraveiculos.Locacao;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ronan
 */
public class Alugar extends javax.swing.JFrame {

    /**
     * Creates new form Alugar
     */
    public Alugar() {
        initComponents();

        carregaCBVeiculos();
    }

    //CARREGA COMBO BOX VEÍCULOS.
    public void carregaCBVeiculos() {
        cb_veiculos.removeAllItems();

        for (int i = 0; i < Locadora.getVeiculos().size(); i++) {
            cb_veiculos.addItem(Locadora.getVeiculos().get(i).getModelo());
        }
    }

    public int calculaQuantDias() {
        Date locacao = null, devolucao = null;

        try {
            locacao = converteStringData(ctext_dataLocacao.getText());
            devolucao = converteStringData(ctext_dataDevolucao.getText());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Data Inválida");
        }

        long quant = Math.abs(devolucao.getTime() - locacao.getTime());
        int quantDias = (int) TimeUnit.DAYS.convert(quant, TimeUnit.MILLISECONDS);

        return quantDias;
    }

    //CARREGA DADOS DO VEÍCULO SELECIONADO NAS CAIXA DE TEXTO.
    public void carregaDadosVeiculo() {
        int indice = cb_veiculos.getSelectedIndex();

        ctext_modelo.setText(Locadora.getVeiculos().get(indice).getModelo());
        ctext_marca.setText(Locadora.getVeiculos().get(indice).getMarca());
        ctext_cor.setText(Locadora.getVeiculos().get(indice).getCor());

        String capacidade = Integer.toString(Locadora.getVeiculos().get(indice).getCapacidade());
        ctext_capacidade.setText(capacidade);

        String ano = Integer.toString(Locadora.getVeiculos().get(indice).getAno());
        ctext_ano.setText(ano);
    }

    //CONVERTE DATA PARA STRING.
    public static String converteDateString(Date data) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = dateFormat.format(data);

        return dataFormatada;
    }

    //CONVERTE STRING EM DATA.
    public static Date converteStringData(String dataString) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formato.parse(dataString);

        return data;
    }

    //CARREGA DADOS DE PESSOA FÍSICA NAS CAIXAS DE TEXTO.
    public void carregaDadosCliente(int i) {
        Date dataAtual = new Date();
        dataAtual = Locadora.getCliente().get(i).getDataNascimento();

        if (dataAtual != null) {
            ctext_alugarNascimento.setText(converteDateString(dataAtual));
        } else {
            ctext_alugarNascimento.setText("");
        }

        ctext_alugarNome.setText(Locadora.getCliente().get(i).getNome());
        ctext_alugarEmail.setText(Locadora.getCliente().get(i).getEmail());
        ctext_alugarTelefone.setText(Locadora.getCliente().get(i).getTelefone());

    }

    public void carregaTabelaTotal(float precoDiaria, int quantDias, float total) {
        Object colunas[] = new Object[]{"Preço da Diária", "Quantidade de dias", "Total"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        Object linha[] = new Object[]{precoDiaria, quantDias, total};
        modeloTabela.addRow(linha);

        table_total.setModel(modeloTabela);
    }

    //PESQUISA CPF JÁ CADASTRADOS.
    public int pesquisaID() {
        for (int i = 0; i < Locadora.getCliente().size(); i++) {
            if (Locadora.getCliente().get(i).getID().equals(ctext_alugarID.getText())) {
                return i;
            }
        }
        return -1;
    }

    public float totalPagar() {
        int index = cb_veiculos.getSelectedIndex();
        float diaria = Locadora.getVeiculos().get(index).getDiaria();
        int quantDias = calculaQuantDias();
        float total = 0;

        if (rbut_dinheiro.isSelected()) {
            PagamentoDinheiro pag = new PagamentoDinheiro();
            total = pag.pagamento(diaria, quantDias);
        } else if (rbut_cartao.isSelected()) {
            PagamentoCartao pag = new PagamentoCartao();
            total = pag.pagamento(diaria, quantDias);
        }

        return total;
    }

    public boolean verificaCampo(Locacao reserva) {
        try {
            reserva.setDataInicio(converteStringData(ctext_dataLocacao.getText()));
            reserva.setDataFim(converteStringData(ctext_dataDevolucao.getText()));
        } catch (ParseException | NumberFormatException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Campos incompletos ou preenchidos incorretamente.");
            return false;
        }

        if (ctext_alugarID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os Campos.");
            return false;
        } else {
            return true;
        }
    }

    public void estaDisponivel() {
        int indice = cb_veiculos.getSelectedIndex();
        try {
            if (Locadora.estaDisponivel(
                    Locadora.getVeiculos().get(indice).getPlaca(),
                    converteStringData(ctext_dataLocacao.getText()),
                    converteStringData(ctext_dataDevolucao.getText()))) {
                JOptionPane.showMessageDialog(null, "Esta disponivel!");
                int index = cb_veiculos.getSelectedIndex();
                float diaria = Locadora.getVeiculos().get(index).getDiaria();

                if (rbut_dinheiro.isSelected() || rbut_cartao.isSelected()) {
                    carregaTabelaTotal(diaria, calculaQuantDias(), totalPagar());
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma Forma de Pagamento");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Não esta disponivel nessa data!");
            }

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Deu ruim!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField7 = new javax.swing.JTextField();
        bgrup_alugarID = new javax.swing.ButtonGroup();
        bgrup_formaPagar = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        but_reservar = new javax.swing.JButton();
        but_cancelar = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        rbut_alugarCpf = new javax.swing.JRadioButton();
        rbut_alugarCnpj = new javax.swing.JRadioButton();
        ctext_alugarID = new javax.swing.JTextField();
        but_pesquisar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        ctext_alugarNome = new javax.swing.JTextField();
        ctext_alugarEmail = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        ctext_alugarNascimento = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        ctext_alugarTelefone = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cb_veiculos = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        ctext_modelo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ctext_marca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        ctext_cor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ctext_capacidade = new javax.swing.JTextField();
        ctext_ano = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ctext_dataLocacao = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        ctext_dataDevolucao = new javax.swing.JFormattedTextField();
        rbut_dinheiro = new javax.swing.JRadioButton();
        rbut_cartao = new javax.swing.JRadioButton();
        but_calcular = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_total = new javax.swing.JTable();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jRadioButtonMenuItem2.setSelected(true);
        jRadioButtonMenuItem2.setText("jRadioButtonMenuItem2");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTextField7.setText("jTextField7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Alugue um veículo");

        but_reservar.setText("Reservar");
        but_reservar.setToolTipText("Reservar");
        but_reservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_reservarActionPerformed(evt);
            }
        });

        but_cancelar.setText("Cancelar");
        but_cancelar.setToolTipText("Cancelar");
        but_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_cancelarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Dados do Cliente"));

        bgrup_alugarID.add(rbut_alugarCpf);
        rbut_alugarCpf.setText("CPF");
        rbut_alugarCpf.setToolTipText("CPF");

        bgrup_alugarID.add(rbut_alugarCnpj);
        rbut_alugarCnpj.setText("CNPJ");
        rbut_alugarCnpj.setToolTipText("CNPJ");

        ctext_alugarID.setToolTipText("Identificador");

        but_pesquisar.setText("Pesquisar");
        but_pesquisar.setToolTipText("Pesquisar");
        but_pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_pesquisarActionPerformed(evt);
            }
        });

        jLabel14.setText("Nome");

        ctext_alugarNome.setEditable(false);
        ctext_alugarNome.setToolTipText("Nome");

        ctext_alugarEmail.setEditable(false);
        ctext_alugarEmail.setToolTipText("Email");

        jLabel16.setText("Data de Nascimento");

        ctext_alugarNascimento.setEditable(false);
        ctext_alugarNascimento.setToolTipText("Data de Nascimento");

        jLabel15.setText("E-mail");

        jLabel18.setText("Telefone");

        ctext_alugarTelefone.setEditable(false);
        ctext_alugarTelefone.setToolTipText("Telefone");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(rbut_alugarCpf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbut_alugarCnpj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ctext_alugarID, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(but_pesquisar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ctext_alugarEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ctext_alugarNascimento))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ctext_alugarNome, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ctext_alugarTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbut_alugarCpf)
                    .addComponent(rbut_alugarCnpj)
                    .addComponent(ctext_alugarID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(but_pesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(ctext_alugarNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(ctext_alugarTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ctext_alugarEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(ctext_alugarNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Dados do Veículo"));

        jLabel2.setText("Escolha o Veículo");

        cb_veiculos.setToolTipText("Escolha o Veículo");
        cb_veiculos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_veiculosItemStateChanged(evt);
            }
        });
        cb_veiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_veiculosActionPerformed(evt);
            }
        });

        jLabel6.setText("Modelo");

        ctext_modelo.setEditable(false);
        ctext_modelo.setToolTipText("Modelo");

        jLabel7.setText("Marca");

        ctext_marca.setEditable(false);
        ctext_marca.setToolTipText("Marca");

        jLabel8.setText("Cor");

        ctext_cor.setEditable(false);
        ctext_cor.setToolTipText("Cor");

        jLabel9.setText("Capacidade");

        jLabel10.setText("Ano");

        ctext_capacidade.setEditable(false);
        ctext_capacidade.setToolTipText("Capacidade");

        ctext_ano.setEditable(false);
        ctext_ano.setToolTipText("Ano");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ctext_modelo)
                            .addComponent(ctext_cor, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ctext_capacidade, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ctext_ano, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ctext_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_veiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cb_veiculos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ctext_modelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(ctext_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(ctext_cor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(ctext_capacidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(ctext_ano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Valor a Pagar"));

        jLabel12.setText("Forma de Pagamento");

        jLabel3.setText("Data de locação");

        try {
            ctext_dataLocacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ctext_dataLocacao.setToolTipText("Data de Locação");

        jLabel4.setText("Data de devolução");

        try {
            ctext_dataDevolucao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ctext_dataDevolucao.setToolTipText("Data de Devolução");

        bgrup_formaPagar.add(rbut_dinheiro);
        rbut_dinheiro.setText("Dinheiro");
        rbut_dinheiro.setToolTipText("Dinheiro");

        bgrup_formaPagar.add(rbut_cartao);
        rbut_cartao.setText("Cartão");
        rbut_cartao.setToolTipText("Cartão");

        but_calcular.setText("Calcular");
        but_calcular.setToolTipText("Calcular");
        but_calcular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                but_calcularMouseClicked(evt);
            }
        });
        but_calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_calcularActionPerformed(evt);
            }
        });

        table_total.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Preço da diária", "Quantidade de dias", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(table_total);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ctext_dataLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbut_dinheiro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbut_cartao)
                                .addGap(55, 55, 55)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(but_calcular)
                            .addComponent(ctext_dataDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ctext_dataLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(ctext_dataDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(rbut_dinheiro)
                    .addComponent(rbut_cartao)
                    .addComponent(but_calcular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(but_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(but_reservar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel17)))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(but_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(but_reservar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void but_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_cancelarActionPerformed
        new Principal().setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_but_cancelarActionPerformed

    private void cb_veiculosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_veiculosItemStateChanged
        carregaDadosVeiculo();
    }//GEN-LAST:event_cb_veiculosItemStateChanged

    private void but_pesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_pesquisarActionPerformed
        int index;

        if (rbut_alugarCpf.isSelected() || rbut_alugarCnpj.isSelected()) {
            index = pesquisaID();
            if (index > -1) {
                carregaDadosCliente(index);
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não cadastrado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um identificador");
        }
    }//GEN-LAST:event_but_pesquisarActionPerformed

    private void but_calcularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_but_calcularMouseClicked

    }//GEN-LAST:event_but_calcularMouseClicked

    private void but_reservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_reservarActionPerformed
        try {
            Locacao novaReserva = new Locacao(totalPagar(), calculaQuantDias());
            int indexVeiculo = cb_veiculos.getSelectedIndex();
            int indexCliente;

            novaReserva.setVeiculo(Locadora.getVeiculos().get(indexVeiculo));

            if ((rbut_alugarCpf.isSelected() || rbut_alugarCnpj.isSelected()) && verificaCampo(novaReserva)) {
                indexCliente = pesquisaID();
                if (indexCliente > -1) {
                    System.out.println(indexCliente);
                    novaReserva.setCliente(Locadora.getCliente().get(indexCliente));
                    Locadora.adicionaReserva(novaReserva);
                    new Principal().setVisible(true);
                    this.setVisible(false);
                }
            }
        } catch (NullPointerException err) {
            System.out.println("Data invalida...");
        }


    }//GEN-LAST:event_but_reservarActionPerformed

    private void but_calcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_calcularActionPerformed
        estaDisponivel();
    }//GEN-LAST:event_but_calcularActionPerformed

    private void cb_veiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_veiculosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_veiculosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Alugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Alugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Alugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Alugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Alugar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrup_alugarID;
    private javax.swing.ButtonGroup bgrup_formaPagar;
    private javax.swing.JButton but_calcular;
    private javax.swing.JButton but_cancelar;
    private javax.swing.JButton but_pesquisar;
    private javax.swing.JButton but_reservar;
    private javax.swing.JComboBox<String> cb_veiculos;
    private javax.swing.JTextField ctext_alugarEmail;
    private javax.swing.JTextField ctext_alugarID;
    private javax.swing.JTextField ctext_alugarNascimento;
    private javax.swing.JTextField ctext_alugarNome;
    private javax.swing.JTextField ctext_alugarTelefone;
    private javax.swing.JTextField ctext_ano;
    private javax.swing.JTextField ctext_capacidade;
    private javax.swing.JTextField ctext_cor;
    private javax.swing.JFormattedTextField ctext_dataDevolucao;
    private javax.swing.JFormattedTextField ctext_dataLocacao;
    private javax.swing.JTextField ctext_marca;
    private javax.swing.JTextField ctext_modelo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JRadioButton rbut_alugarCnpj;
    private javax.swing.JRadioButton rbut_alugarCpf;
    private javax.swing.JRadioButton rbut_cartao;
    private javax.swing.JRadioButton rbut_dinheiro;
    private javax.swing.JTable table_total;
    // End of variables declaration//GEN-END:variables
}
