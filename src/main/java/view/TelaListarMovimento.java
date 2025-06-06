package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.MovimentoDAO;
import model.Movimento;
import java.util.List;

public class TelaListarMovimento extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnEditar, btnDeletar, btnNovo;

    public TelaListarMovimento() {
        setTitle("Listar Movimentações");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Produto ID");
        modeloTabela.addColumn("Quantidade");
        modeloTabela.addColumn("Tipo");
        modeloTabela.addColumn("Data");

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

        listarMovimentacoes();
    }

    private void listarMovimentacoes() {
        MovimentoDAO dao = new MovimentoDAO();
        List<Movimento> lista = dao.listar();
        modeloTabela.setRowCount(0);
        for (Movimento m : lista) {
            modeloTabela.addRow(new Object[]{
                m.getId(), m.getProdutoId(), m.getQuantidade(), m.getTipo(), m.getDataMovimentacao()
            });
        }
    }

    private void abrirCadastro(Movimento movimento) {
        TelaCadastroMovimento tela = new TelaCadastroMovimento(movimento, this::listarMovimentacoes);
        tela.setVisible(true);
    }

    private void editarSelecionado() {
        int row = tabela.getSelectedRow();
        if (row >= 0) {
            Movimento m = new Movimento();
            m.setId((int) modeloTabela.getValueAt(row, 0));
            m.setProdutoId((int) modeloTabela.getValueAt(row, 1));
            m.setQuantidade((int) modeloTabela.getValueAt(row, 2));
            m.setTipo((String) modeloTabela.getValueAt(row, 3));
            abrirCadastro(m);
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
                MovimentoDAO dao = new MovimentoDAO();
                dao.remover(id);
                listarMovimentacoes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para deletar.");
        }
    }
}
