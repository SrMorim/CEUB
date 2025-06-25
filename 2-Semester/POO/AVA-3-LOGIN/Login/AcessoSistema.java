// Arquivo: AcessoSistema.java

import javax.swing.*;
import java.awt.*;

public class AcessoSistema extends JFrame {

    private JLabel lblUsuario, lblSenha;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar, btnCancelar, btnNovoUsuario;

    /**
     * COMENTÁRIO OBRIGATÓRIO (INTERFACE): 
     * A variável 'loginService' é declarada com o tipo da INTERFACE (LoginService).
     *
     * JUSTIFICATIVA: 
     * Utilizar a interface aqui desacopla a classe da tela (AcessoSistema) da implementação
     * concreta da lógica de login (AuthenticationService). Isso significa que, no futuro,
     * poderíamos trocar o 'AuthenticationService' por outro serviço (ex: um que se conecte
     * a um banco de dados ou a um servidor LDAP) sem precisar alterar NENHUMA linha de código
     * nesta classe da tela, tornando o sistema mais flexível e fácil de manter.
     */
    private LoginService loginService;

    public AcessoSistema() {
        // --- Configurações da Janela ---
        super("Acesso ao Sistema"); // Título da janela 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(380, 200);
        this.setLayout(null); // Layout absoluto para posicionar componentes
        this.setResizable(false);

        /**
         * COMENTÁRIO OBRIGATÓRIO (POLIMORFISMO): 
         * Aqui ocorre o POLIMORFISMO. Estamos instanciando um objeto da classe
         * 'AuthenticationService' e atribuindo-o a uma variável do tipo da interface 'LoginService'.
         *
         * JUSTIFICATIVA: 
         * O polimorfismo permite que o objeto 'loginService' seja tratado genericamente
         * como um 'LoginService', escondendo os detalhes de sua implementação real ('AuthenticationService').
         * Quando chamamos 'loginService.login()', o Java sabe, em tempo de execução, que deve
         * invocar o método da classe concreta ('AuthenticationService'), permitindo que diferentes
         * implementações da mesma interface tenham comportamentos diferentes para o mesmo método.
         */
        this.loginService = new AuthenticationService();

        // Desenho da janela conforme a imagem 
        lblUsuario = new JLabel("Usuário:");
        lblUsuario.setBounds(50, 30, 80, 25);
        this.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(110, 30, 200, 25);
        this.add(txtUsuario);

        lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 70, 80, 25);
        this.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(110, 70, 200, 25);
        this.add(txtSenha);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(30, 120, 90, 25);
        this.add(btnEntrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(135, 120, 90, 25);
        this.add(btnCancelar);

        btnNovoUsuario = new JButton("Novo Usuário");
        btnNovoUsuario.setBounds(240, 120, 110, 25);
        this.add(btnNovoUsuario);

        btnEntrar.addActionListener(e -> handleLogin());

        btnCancelar.addActionListener(e -> {
            // Clicando no botão "Cancelar" para encerrar o sistema 
            System.exit(0);
        });

        btnNovoUsuario.addActionListener(e -> {
            // Mensagem para o botão "Novo Usuário" 
            JOptionPane.showMessageDialog(this, "Em desenvolvimento.", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        });

        // Centraliza a janela na tela 
        this.setLocationRelativeTo(null);
    }

    private void handleLogin() {
        String username = txtUsuario.getText();
        String password = new String(txtSenha.getPassword());

        // Bloco de tratamento de exceções
        try {
            /**
             * COMENTÁRIO OBRIGATÓRIO (INTERFACE E POLIMORFISMO EM AÇÃO): 
             * A chamada do método 'login' é feita através da referência da interface.
             *
             * JUSTIFICATIVA: 
             * A classe AcessoSistema não sabe qual é a implementação real por trás de 'loginService'.
             * Ela apenas sabe que pode chamar o método 'login' porque isso é garantido pelo
             * "contrato" da interface LoginService. Isso demonstra o poder do polimorfismo,
             * onde a mesma chamada de método pode ter diferentes comportamentos dependendo do
             * objeto real que está sendo referenciado.
             */
            boolean sucesso = loginService.login(username, password);

            if (sucesso) {
                String nomeUsuario = loginService.getCurrentUser().getUsername();
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + nomeUsuario + "!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Login inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
        } finally {
            txtSenha.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AcessoSistema().setVisible(true);
        });
    }
}