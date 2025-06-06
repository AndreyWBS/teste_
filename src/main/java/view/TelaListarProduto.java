package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import dao.ProdutoDAO;
import model.Produto;
import java.util.List;

public class TelaListarProduto extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnEditar, btnDeletar, btnNovo;

    public TelaListarProduto() {
        setTitle("Listar Produtos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Produtos Cadastrados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

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
        tabela.setRowHeight(24);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scroll = new JScrollPane(tabela);
        mainPanel.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");
        btnDeletar = new JButton("Deletar");
        for (JButton btn : new JButton[]{btnNovo, btnEditar, btnDeletar}) {
            btn.setBackground(new Color(0, 123, 255));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
        }
        botoes.add(btnNovo);
        botoes.add(btnEditar);
        botoes.add(btnDeletar);
        mainPanel.add(botoes, BorderLayout.SOUTH);

        btnNovo.addActionListener(e -> abrirCadastro(null));
        btnEditar.addActionListener(e -> editarSelecionado());
        btnDeletar.addActionListener(e -> deletarSelecionado());

        add(mainPanel);
        listarProdutos();
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

    private void abrirCadastro(Produto produto) {
        TelaCadastroProduto tela = new TelaCadastroProduto(produto, this::listarProdutos);
        tela.setVisible(true);
    }

    private void editarSelecionado() {
        int row = tabela.getSelectedRow();
        if (row >= 0) {
            Produto p = new Produto();
            p.setId((int) modeloTabela.getValueAt(row, 0));
            p.setNome((String) modeloTabela.getValueAt(row, 1));
            p.setPrecoUnitario(Double.parseDouble(modeloTabela.getValueAt(row, 2).toString()));
            p.setUnidade((String) modeloTabela.getValueAt(row, 3));
            p.setQuantidadeEstoque((int) modeloTabela.getValueAt(row, 4));
            p.setQuantidadeMinima((int) modeloTabela.getValueAt(row, 5));
            p.setQuantidadeMaxima((int) modeloTabela.getValueAt(row, 6));
            p.setCategoriaId((int) modeloTabela.getValueAt(row, 7));
            abrirCadastro(p);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para editar.");
        }
    }

    private void deletarSelecionado() {
        int row = tabela.getSelectedRow();
        if (row >= 0) {
            int id = (int) modeloTabela.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente deletar?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ProdutoDAO dao = new ProdutoDAO();
                dao.remover(id);
                listarProdutos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para deletar.");
        }
    }
}
