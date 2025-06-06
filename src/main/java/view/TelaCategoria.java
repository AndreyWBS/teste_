package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.CategoriaDAO;
import model.Categoria;
import java.util.List;

public class TelaCategoria extends JFrame {
    private JTextField txtId, txtNome;
    private JComboBox<String> cbTamanho, cbEmbalagem;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaCategoria() {
        setTitle("Cadastro de Categoria");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(5);
        txtId.setEditable(false);

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);

        JLabel lblTamanho = new JLabel("Tamanho:");
        cbTamanho = new JComboBox<>(new String[]{"Pequeno", "Médio", "Grande"});

        JLabel lblEmbalagem = new JLabel("Embalagem:");
        cbEmbalagem = new JComboBox<>(new String[]{"Lata", "Vidro", "Plástico"});

        JButton btnSalvar = new JButton("Salvar");
        JButton btnListar = new JButton("Listar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Tamanho");
        modeloTabela.addColumn("Embalagem");

        tabela = new JTable(modeloTabela);
        tabela.getSelectionModel().addListSelectionListener(e -> selecionarCategoria());
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel panel = new JPanel();
        panel.add(lblId); panel.add(txtId);
        panel.add(lblNome); panel.add(txtNome);
        panel.add(lblTamanho); panel.add(cbTamanho);
        panel.add(lblEmbalagem); panel.add(cbEmbalagem);
        panel.add(btnSalvar); panel.add(btnListar);
        panel.add(btnAlterar); panel.add(btnExcluir);

        add(panel, "North");
        add(scroll, "Center");

        btnSalvar.addActionListener(e -> salvarCategoria());
        btnListar.addActionListener(e -> listarCategorias());
        btnAlterar.addActionListener(e -> alterarCategoria());
        btnExcluir.addActionListener(e -> excluirCategoria());
    }

    private void salvarCategoria() {
        Categoria cat = new Categoria();
        cat.setNome(txtNome.getText());
        cat.setTamanho(cbTamanho.getSelectedItem().toString());
        cat.setEmbalagem(cbEmbalagem.getSelectedItem().toString());

        CategoriaDAO dao = new CategoriaDAO();
        dao.adicionar(cat);

        JOptionPane.showMessageDialog(this, "Categoria salva com sucesso!");
        limparCampos();
    }

    private void listarCategorias() {
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> lista = dao.listar();

        modeloTabela.setRowCount(0);
        for (Categoria c : lista) {
            modeloTabela.addRow(new Object[]{c.getId(), c.getNome(), c.getTamanho(), c.getEmbalagem()});
        }
    }

    private void selecionarCategoria() {
        int row = tabela.getSelectedRow();
        if (row >= 0) {
            txtId.setText(modeloTabela.getValueAt(row, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(row, 1).toString());
            cbTamanho.setSelectedItem(modeloTabela.getValueAt(row, 2));
            cbEmbalagem.setSelectedItem(modeloTabela.getValueAt(row, 3));
        }
    }

    private void alterarCategoria() {
        try {
            Categoria cat = new Categoria();
            cat.setId(Integer.parseInt(txtId.getText()));
            cat.setNome(txtNome.getText());
            cat.setTamanho(cbTamanho.getSelectedItem().toString());
            cat.setEmbalagem(cbEmbalagem.getSelectedItem().toString());

            CategoriaDAO dao = new CategoriaDAO();
            dao.atualizar(cat);

            JOptionPane.showMessageDialog(this, "Categoria alterada com sucesso!");
            limparCampos();
            listarCategorias();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Selecione uma categoria válida!");
        }
    }

    private void excluirCategoria() {
        try {
            int id = Integer.parseInt(txtId.getText());
            CategoriaDAO dao = new CategoriaDAO();
            dao.remover(id);

            JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!");
            limparCampos();
            listarCategorias();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Selecione uma categoria válida!");
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        cbTamanho.setSelectedIndex(0);
        cbEmbalagem.setSelectedIndex(0);
    }
}