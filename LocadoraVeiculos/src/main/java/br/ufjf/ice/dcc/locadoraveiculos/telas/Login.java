/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos.telas;

import br.ufjf.ice.dcc.locadoraveiculos.Endereco;
import br.ufjf.ice.dcc.locadoraveiculos.Funcionario;
import br.ufjf.ice.dcc.locadoraveiculos.Gerente;
import br.ufjf.ice.dcc.locadoraveiculos.Locadora;
import br.ufjf.ice.dcc.locadoraveiculos.telas.ArquivoDeDados.Arquivo;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author lucca
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        carregaLogo();
        Arquivo.carregarDados();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        logo1 = new javax.swing.JLabel();
        txt_Login = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jb_entrar = new javax.swing.JButton();
        txt_Senha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Login:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Senha:");

        jb_entrar.setText("Entrar");
        jb_entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_entrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(165, 165, 165)
                                .addComponent(jb_entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)
                            .addComponent(txt_Login)
                            .addComponent(txt_Senha))))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Senha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jb_entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jb_entrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_entrarActionPerformed
        /*if(Locadora.buscaGerente(txt_Login.getText()) || Locadora.buscaAtendente(txt_Login.getText())){
            new Principal().setVisible(true);
            this.setVisible(false);
        }else{
            JOptionPane.showMessageDialog(null, "Login não cadastrado.");
        }*/
        
        /*
        //instaciando administrador
        Funcionario adm = new Gerente();
        adm.setCpf("adm");
        adm.setDataNascimento(new Date());
        adm.setEmail("adm@locar.com");
        adm.setEndereco(new Endereco("", "", 0, "", "", "", ""));
        adm.setNome("adm");
        adm.setTelefone("0000000");
        adm.usuario.setId("adm");        
        adm.usuario.setSenha("123");

        Locadora.adicionaGerente((Gerente) adm);
        */
        if(Locadora.buscaUsuario(txt_Login.getText(), txt_Senha.getText())){
            new Principal().setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso negado!");
        }
        
        /*
        if(txt_Login.getText().equals("funcionario") && txt_Senha.getText().equals("1234"))
        {
            //Locadora.adicionaAtendente();
            new Principal().setVisible(true);
            this.setVisible(false);
        }
        else if(txt_Login.getText().equals("gerente") && txt_Senha.getText().equals("adm1234"))
        {
            new Principal().setVisible(true);
            this.setVisible(false);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Acesso negado!");
        }
        */
    }//GEN-LAST:event_jb_entrarActionPerformed

    private void carregaLogo() {
        ImageIcon icon = new ImageIcon("src/main/java/br/ufjf/ice/dcc/locadoraveiculos/telas/Imagens/logo.png");
        logo1.setIcon(icon);
    }
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jb_entrar;
    private javax.swing.JLabel logo1;
    private javax.swing.JTextField txt_Login;
    private javax.swing.JPasswordField txt_Senha;
    // End of variables declaration//GEN-END:variables
}
