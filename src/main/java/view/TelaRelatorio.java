package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.ProdutoDAO;
import model.Produto;
import java.util.List;


public class TelaRelatorio extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaRelatorio() {
        setTitle("Relatórios");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JButton btnListaPreco = new JButton("Lista de Preços");
        JButton btnBalanco = new JButton("Balanço Físico/Financeiro");
        JButton btnMin = new JButton("Produtos Abaixo do Mínimo");
        JButton btnMax = new JButton("Produtos Acima do Máximo");
        JButton btnPorCategoria = new JButton("Quantidade por Categoria");

        modeloTabela = new DefaultTableModel();
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel panel = new JPanel();
        panel.add(btnListaPreco);
        panel.add(btnBalanco);
        panel.add(btnMin);
        panel.add(btnMax);
        panel.add(btnPorCategoria);

        add(panel, "North");
        add(scroll, "Center");

        btnListaPreco.addActionListener(e -> listarPrecos());
        btnBalanco.addActionListener(e -> balancoFisicoFinanceiro());
        btnMin.addActionListener(e -> listarAbaixoMinimo());
        btnMax.addActionListener(e -> listarAcimaMaximo());
        btnPorCategoria.addActionListener(e -> listarPorCategoria());
    }


    private void listarPrecos() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listar();

        modeloTabela.setRowCount(0);
        modeloTabela.setColumnIdentifiers(new String[]{"ID", "Nome", "Preço", "Unidade", "Categoria ID"});
        for (Produto p : lista) {
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNome(), p.getPrecoUnitario(), p.getUnidade(), p.getCategoriaId()
            });
        }
    }


    private void balancoFisicoFinanceiro() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listar();

        modeloTabela.setRowCount(0);
        modeloTabela.setColumnIdentifiers(new String[]{"ID", "Nome", "Quantidade", "Preço Unitário", "Valor Total"});
        double totalEstoque = 0;
        for (Produto p : lista) {
            double valorTotal = p.getQuantidadeEstoque() * p.getPrecoUnitario();
            totalEstoque += valorTotal;
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNome(), p.getQuantidadeEstoque(), p.getPrecoUnitario(), valorTotal
            });
        }
        JOptionPane.showMessageDialog(this, "Valor total do estoque: R$ " + String.format("%.2f", totalEstoque));
    }


    private void listarAbaixoMinimo() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listar();

        modeloTabela.setRowCount(0);
        modeloTabela.setColumnIdentifiers(new String[]{"ID", "Nome", "Estoque", "Mínimo"});
        for (Produto p : lista) {
            if (p.getQuantidadeEstoque() < p.getQuantidadeMinima()) {
                modeloTabela.addRow(new Object[]{
                    p.getId(), p.getNome(), p.getQuantidadeEstoque(), p.getQuantidadeMinima()
                });
            }
        }
    }


    private void listarAcimaMaximo() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listar();

        modeloTabela.setRowCount(0);
        modeloTabela.setColumnIdentifiers(new String[]{"ID", "Nome", "Estoque", "Máximo"});
        for (Produto p : lista) {
            if (p.getQuantidadeEstoque() > p.getQuantidadeMaxima()) {
                modeloTabela.addRow(new Object[]{
                    p.getId(), p.getNome(), p.getQuantidadeEstoque(), p.getQuantidadeMaxima()
                });
            }
        }
    }


    private void listarPorCategoria() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Object[]> lista = dao.listarQuantidadePorCategoria();

        modeloTabela.setRowCount(0);
        modeloTabela.setColumnIdentifiers(new String[]{"Categoria", "Quantidade de Produtos"});
        for (Object[] row : lista) {
            modeloTabela.addRow(row);
        }
    }
}