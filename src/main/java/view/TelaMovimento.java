package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.MovimentoDAO;
import model.Movimento;
import java.util.Date;
import java.util.List;


public class TelaMovimento extends JFrame {
    private JTextField txtId, txtProdutoId, txtQuantidade;
    private JComboBox<String> cbTipo;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaMovimento() {
        setTitle("Movimentar Estoque");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(5);
        txtId.setEditable(false);

        JLabel lblProduto = new JLabel("Produto ID:");
        txtProdutoId = new JTextField(10);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        txtQuantidade = new JTextField(10);

        JLabel lblTipo = new JLabel("Tipo:");
        cbTipo = new JComboBox<>(new String[]{"ENTRADA", "SAIDA"});

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnListar = new JButton("Listar");

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Produto ID");
        modeloTabela.addColumn("Quantidade");
        modeloTabela.addColumn("Tipo");
        modeloTabela.addColumn("Data");

        tabela = new JTable(modeloTabela);
        tabela.getSelectionModel().addListSelectionListener(e -> selecionarMovimento());
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel panel = new JPanel();
        panel.add(lblId); panel.add(txtId);
        panel.add(lblProduto); panel.add(txtProdutoId);
        panel.add(lblQuantidade); panel.add(txtQuantidade);
        panel.add(lblTipo); panel.add(cbTipo);
        panel.add(btnRegistrar); panel.add(btnListar);

        add(panel, "North");
        add(scroll, "Center");

        btnRegistrar.addActionListener(e -> registrarMovimento());
        btnListar.addActionListener(e -> listarMovimentacoes());
    }


    private void registrarMovimento() {
        try {
            Movimento m = new Movimento();
            m.setProdutoId(Integer.parseInt(txtProdutoId.getText()));
            m.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            m.setTipo(cbTipo.getSelectedItem().toString());
            m.setDataMovimentacao(new Date());

            MovimentoDAO dao = new MovimentoDAO();
            dao.adicionar(m);

            String aviso = dao.checarEstoque(m.getProdutoId());
            if (aviso != null) {
                JOptionPane.showMessageDialog(this, aviso);
            }

            JOptionPane.showMessageDialog(this, "Movimentação registrada com sucesso!");
            limparCampos();
            listarMovimentacoes();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos!");
        }
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

    private void selecionarMovimento() {
        int row = tabela.getSelectedRow();
        if (row >= 0) {
            txtId.setText(modeloTabela.getValueAt(row, 0).toString());
            txtProdutoId.setText(modeloTabela.getValueAt(row, 1).toString());
            txtQuantidade.setText(modeloTabela.getValueAt(row, 2).toString());
            cbTipo.setSelectedItem(modeloTabela.getValueAt(row, 3));
        }
    }


    private void limparCampos() {
        txtId.setText("");
        txtProdutoId.setText("");
        txtQuantidade.setText("");
        cbTipo.setSelectedIndex(0);
    }
}