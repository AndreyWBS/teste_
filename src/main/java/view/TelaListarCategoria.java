package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.CategoriaDAO;
import model.Categoria;
import java.util.List;

public class TelaListarCategoria extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnEditar, btnDeletar, btnNovo;

    public TelaListarCategoria() {
        setTitle("Listar Categorias");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Tamanho");
        modeloTabela.addColumn("Embalagem");

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");
        btnDeletar = new JButton("Deletar");

        btnNovo.addActionListener(e -> abrirCadastro(null));
        btnEditar.addActionListener(e -> editarSelecionado());
        btnDeletar.addActionListener(e -> deletarSelecionado());

        JPanel botoes = new JPanel();
        botoes.add(btnNovo);
        botoes.add(btnEditar);
        botoes.add(btnDeletar);

        add(scroll, "Center");
        add(botoes, "South");

        listarCategorias();
    }

    private void listarCategorias() {
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> lista = dao.listar();
        modeloTabela.setRowCount(0);
        for (Categoria c : lista) {
            modeloTabela.addRow(new Object[]{c.getId(), c.getNome(), c.getTamanho(), c.getEmbalagem()});
        }
    }

    private void abrirCadastro(Categoria categoria) {
        TelaCadastroCategoria tela = new TelaCadastroCategoria(categoria, this::listarCategorias);
        tela.setVisible(true);
    }

    private void editarSelecionado() {
        int row = tabela.getSelectedRow();
        if (row >= 0) {
            Categoria c = new Categoria();
            c.setId((int) modeloTabela.getValueAt(row, 0));
            c.setNome((String) modeloTabela.getValueAt(row, 1));
            c.setTamanho((String) modeloTabela.getValueAt(row, 2));
            c.setEmbalagem((String) modeloTabela.getValueAt(row, 3));
            abrirCadastro(c);
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
                CategoriaDAO dao = new CategoriaDAO();
                dao.remover(id);
                listarCategorias();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para deletar.");
        }
    }
}
