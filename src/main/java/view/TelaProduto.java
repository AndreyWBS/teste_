package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.ProdutoDAO;
import model.Produto;
import java.util.List;


public class TelaProduto extends JFrame {
    private JTextField txtId, txtNome, txtPreco, txtUnidade, txtQtdEstoque, txtQtdMin, txtQtdMax, txtCategoriaId;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaProduto() {
        setTitle("Cadastro de Produto");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(5);
        txtId.setEditable(false);

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);

        JLabel lblPreco = new JLabel("Preço Unitário:");
        txtPreco = new JTextField(10);

        JLabel lblUnidade = new JLabel("Unidade:");
        txtUnidade = new JTextField(10);

        JLabel lblQtdEstoque = new JLabel("Qtd. Estoque:");
        txtQtdEstoque = new JTextField(5);

        JLabel lblQtdMin = new JLabel("Qtd. Mínima:");
        txtQtdMin = new JTextField(5);

        JLabel lblQtdMax = new JLabel("Qtd. Máxima:");
        txtQtdMax = new JTextField(5);

        JLabel lblCategoriaId = new JLabel("Categoria ID:");
        txtCategoriaId = new JTextField(5);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnListar = new JButton("Listar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Preço");
        modeloTabela.addColumn("Unidade");
        modeloTabela.addColumn("Estoque");
        modeloTabela.addColumn("Mín.");
        modeloTabela.addColumn("Máx.");
        modeloTabela.addColumn("Cat. ID");

        tabela = new JTable(modeloTabela);
        tabela.getSelectionModel().addListSelectionListener(e -> selecionarProduto());
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel panel = new JPanel();
        panel.add(lblId); panel.add(txtId);
        panel.add(lblNome); panel.add(txtNome);
        panel.add(lblPreco); panel.add(txtPreco);
        panel.add(lblUnidade); panel.add(txtUnidade);
        panel.add(lblQtdEstoque); panel.add(txtQtdEstoque);
        panel.add(lblQtdMin); panel.add(txtQtdMin);
        panel.add(lblQtdMax); panel.add(txtQtdMax);
        panel.add(lblCategoriaId); panel.add(txtCategoriaId);
        panel.add(btnSalvar); panel.add(btnListar);
        panel.add(btnAlterar); panel.add(btnExcluir);

        add(panel, "North");
        add(scroll, "Center");

        btnSalvar.addActionListener(e -> salvarProduto());
        btnListar.addActionListener(e -> listarProdutos());
        btnAlterar.addActionListener(e -> alterarProduto());
        btnExcluir.addActionListener(e -> excluirProduto());
    }


    private void salvarProduto() {
        try {
            Produto p = new Produto();
            p.setNome(txtNome.getText());
            p.setPrecoUnitario(Double.parseDouble(txtPreco.getText()));
            p.setUnidade(txtUnidade.getText());
            p.setQuantidadeEstoque(Integer.parseInt(txtQtdEstoque.getText()));
            p.setQuantidadeMinima(Integer.parseInt(txtQtdMin.getText()));
            p.setQuantidadeMaxima(Integer.parseInt(txtQtdMax.getText()));
            p.setCategoriaId(Integer.parseInt(txtCategoriaId.getText()));

            ProdutoDAO dao = new ProdutoDAO();
            dao.adicionar(p);

            JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos!");
        }
    }

    private void listarProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listar();

        modeloTabela.setRowCount(0);
        for (Produto p : lista) {
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNome(), p.getPrecoUnitario(), p.getUnidade(),
                p.getQuantidadeEstoque(), p.getQuantidadeMinima(), p.getQuantidadeMaxima(), p.getCategoriaId()
            });
        }
    }


    private void selecionarProduto() {
        int row = tabela.getSelectedRow();
        if (row >= 0) {
            txtId.setText(modeloTabela.getValueAt(row, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(row, 1).toString());
            txtPreco.setText(modeloTabela.getValueAt(row, 2).toString());
            txtUnidade.setText(modeloTabela.getValueAt(row, 3).toString());
            txtQtdEstoque.setText(modeloTabela.getValueAt(row, 4).toString());
            txtQtdMin.setText(modeloTabela.getValueAt(row, 5).toString());
            txtQtdMax.setText(modeloTabela.getValueAt(row, 6).toString());
            txtCategoriaId.setText(modeloTabela.getValueAt(row, 7).toString());
        }
    }


    private void alterarProduto() {
        try {
            Produto p = new Produto();
            p.setId(Integer.parseInt(txtId.getText()));
            p.setNome(txtNome.getText());
            p.setPrecoUnitario(Double.parseDouble(txtPreco.getText()));
            p.setUnidade(txtUnidade.getText());
            p.setQuantidadeEstoque(Integer.parseInt(txtQtdEstoque.getText()));
            p.setQuantidadeMinima(Integer.parseInt(txtQtdMin.getText()));
            p.setQuantidadeMaxima(Integer.parseInt(txtQtdMax.getText()));
            p.setCategoriaId(Integer.parseInt(txtCategoriaId.getText()));

            ProdutoDAO dao = new ProdutoDAO();
            dao.atualizar(p);

            JOptionPane.showMessageDialog(this, "Produto alterado com sucesso!");
            limparCampos();
            listarProdutos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos!");
        }
    }


    private void excluirProduto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            ProdutoDAO dao = new ProdutoDAO();
            dao.remover(id);

            JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
            limparCampos();
            listarProdutos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Selecione um produto válido!");
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtPreco.setText("");
        txtUnidade.setText("");
        txtQtdEstoque.setText("");
        txtQtdMin.setText("");
        txtQtdMax.setText("");
        txtCategoriaId.setText("");
    }
}