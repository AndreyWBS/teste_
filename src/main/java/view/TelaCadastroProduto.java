package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import dao.ProdutoDAO;
import model.Produto;


public class TelaCadastroProduto extends JFrame {
    private JTextField txtId, txtNome, txtPreco, txtUnidade, txtQtdEstoque, txtQtdMin, txtQtdMax, txtCategoriaId;
    private JButton btnSalvar;
    private Produto produtoEdicao;
    private Runnable onSaveCallback;

    public TelaCadastroProduto() {
        this(null, null);
    }

    public TelaCadastroProduto(Produto produto, Runnable onSaveCallback) {
        setTitle(produto == null ? "Cadastrar Produto" : "Editar Produto");
        setSize(450, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.produtoEdicao = produto;
        this.onSaveCallback = onSaveCallback;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(produto == null ? "Cadastro de Produto" : "Edição de Produto");
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

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(lblNome, gbc);
        gbc.gridx = 1;
        panel.add(txtNome, gbc);

        JLabel lblPreco = new JLabel("Preço Unitário:");
        txtPreco = new JTextField(10);
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(lblPreco, gbc);
        gbc.gridx = 1;
        panel.add(txtPreco, gbc);

        JLabel lblUnidade = new JLabel("Unidade:");
        txtUnidade = new JTextField(10);
        gbc.gridy = 4; gbc.gridx = 0;
        panel.add(lblUnidade, gbc);
        gbc.gridx = 1;
        panel.add(txtUnidade, gbc);

        JLabel lblQtdEstoque = new JLabel("Qtd. Estoque:");
        txtQtdEstoque = new JTextField(5);
        gbc.gridy = 5; gbc.gridx = 0;
        panel.add(lblQtdEstoque, gbc);
        gbc.gridx = 1;
        panel.add(txtQtdEstoque, gbc);

        JLabel lblQtdMin = new JLabel("Qtd. Mínima:");
        txtQtdMin = new JTextField(5);
        gbc.gridy = 6; gbc.gridx = 0;
        panel.add(lblQtdMin, gbc);
        gbc.gridx = 1;
        panel.add(txtQtdMin, gbc);

        JLabel lblQtdMax = new JLabel("Qtd. Máxima:");
        txtQtdMax = new JTextField(5);
        gbc.gridy = 7; gbc.gridx = 0;
        panel.add(lblQtdMax, gbc);
        gbc.gridx = 1;
        panel.add(txtQtdMax, gbc);

        JLabel lblCategoriaId = new JLabel("Categoria ID:");
        txtCategoriaId = new JTextField(5);
        gbc.gridy = 8; gbc.gridx = 0;
        panel.add(lblCategoriaId, gbc);
        gbc.gridx = 1;
        panel.add(txtCategoriaId, gbc);

        btnSalvar = new JButton(produto == null ? "Cadastrar" : "Salvar");
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.addActionListener(e -> salvarProduto());
        gbc.gridy = 9; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(btnSalvar, gbc);

        add(panel);

        if (produto != null) {
            preencherCampos(produto);
        }
    }

    private void preencherCampos(Produto p) {
        txtId.setText(String.valueOf(p.getId()));
        txtNome.setText(p.getNome());
        txtPreco.setText(String.valueOf(p.getPrecoUnitario()));
        txtUnidade.setText(p.getUnidade());
        txtQtdEstoque.setText(String.valueOf(p.getQuantidadeEstoque()));
        txtQtdMin.setText(String.valueOf(p.getQuantidadeMinima()));
        txtQtdMax.setText(String.valueOf(p.getQuantidadeMaxima()));
        txtCategoriaId.setText(String.valueOf(p.getCategoriaId()));
    }

    private void salvarProduto() {
        try {
            Produto p = produtoEdicao != null ? produtoEdicao : new Produto();
            p.setNome(txtNome.getText());
            p.setPrecoUnitario(Double.parseDouble(txtPreco.getText()));
            p.setUnidade(txtUnidade.getText());
            p.setQuantidadeEstoque(Integer.parseInt(txtQtdEstoque.getText()));
            p.setQuantidadeMinima(Integer.parseInt(txtQtdMin.getText()));
            p.setQuantidadeMaxima(Integer.parseInt(txtQtdMax.getText()));
            p.setCategoriaId(Integer.parseInt(txtCategoriaId.getText()));

            ProdutoDAO dao = new ProdutoDAO();
            if (produtoEdicao == null) {
                dao.adicionar(p);
            } else {
                dao.atualizar(p);
            }

            JOptionPane.showMessageDialog(this, produtoEdicao == null ? "Produto cadastrado!" : "Produto atualizado!");
            if (onSaveCallback != null) onSaveCallback.run();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos!");
        }
    }
}
