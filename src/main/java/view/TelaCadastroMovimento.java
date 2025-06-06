package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import dao.MovimentoDAO;
import model.Movimento;
import java.util.Date;

public class TelaCadastroMovimento extends JFrame {
    private JTextField txtId, txtProdutoId, txtQuantidade;
    private JComboBox<String> cbTipo;
    private JButton btnSalvar;
    private Movimento movimentoEdicao;
    private Runnable onSaveCallback;

    public TelaCadastroMovimento() {
        this(null, null);
    }

    public TelaCadastroMovimento(Movimento movimento, Runnable onSaveCallback) {
        setTitle(movimento == null ? "Cadastrar Movimentação" : "Editar Movimentação");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.movimentoEdicao = movimento;
        this.onSaveCallback = onSaveCallback;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(movimento == null ? "Cadastro de Movimentação" : "Edição de Movimentação");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(5);
        txtId.setEditable(false);
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(lblId, gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);

        JLabel lblProduto = new JLabel("Produto ID:");
        txtProdutoId = new JTextField(10);
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(lblProduto, gbc);
        gbc.gridx = 1;
        panel.add(txtProdutoId, gbc);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        txtQuantidade = new JTextField(10);
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(lblQuantidade, gbc);
        gbc.gridx = 1;
        panel.add(txtQuantidade, gbc);

        JLabel lblTipo = new JLabel("Tipo:");
        cbTipo = new JComboBox<>(new String[]{"ENTRADA", "SAIDA"});
        gbc.gridy = 4; gbc.gridx = 0;
        panel.add(lblTipo, gbc);
        gbc.gridx = 1;
        panel.add(cbTipo, gbc);

        btnSalvar = new JButton(movimento == null ? "Cadastrar" : "Salvar");
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.addActionListener(e -> salvarMovimento());
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(btnSalvar, gbc);

        add(panel);

        if (movimento != null) {
            preencherCampos(movimento);
        }
    }

    private void preencherCampos(Movimento m) {
        txtId.setText(String.valueOf(m.getId()));
        txtProdutoId.setText(String.valueOf(m.getProdutoId()));
        txtQuantidade.setText(String.valueOf(m.getQuantidade()));
        cbTipo.setSelectedItem(m.getTipo());
    }

    private void salvarMovimento() {
        try {
            Movimento m = movimentoEdicao != null ? movimentoEdicao : new Movimento();
            m.setProdutoId(Integer.parseInt(txtProdutoId.getText()));
            m.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            m.setTipo(cbTipo.getSelectedItem().toString());
            m.setDataMovimentacao(new Date());

            MovimentoDAO dao = new MovimentoDAO();
            if (movimentoEdicao == null) {
                dao.adicionar(m);
            } else {
                dao.atualizar(m);
            }

            String aviso = dao.checarEstoque(m.getProdutoId());
            if (aviso != null) {
                JOptionPane.showMessageDialog(this, aviso);
            }

            JOptionPane.showMessageDialog(this, movimentoEdicao == null ? "Movimentação cadastrada!" : "Movimentação atualizada!");
            if (onSaveCallback != null) onSaveCallback.run();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos!");
        }
    }
}
