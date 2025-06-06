package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import dao.CategoriaDAO;
import model.Categoria;

public class TelaCadastroCategoria extends JFrame {
    private JTextField txtId, txtNome;
    private JComboBox<String> cbTamanho, cbEmbalagem;
    private JButton btnSalvar;
    private Categoria categoriaEdicao;
    private Runnable onSaveCallback;

    public TelaCadastroCategoria() {
        this(null, null);
    }

    public TelaCadastroCategoria(Categoria categoria, Runnable onSaveCallback) {
        setTitle(categoria == null ? "Cadastrar Categoria" : "Editar Categoria");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.categoriaEdicao = categoria;
        this.onSaveCallback = onSaveCallback;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(categoria == null ? "Cadastro de Categoria" : "Edição de Categoria");
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

        JLabel lblTamanho = new JLabel("Tamanho:");
        cbTamanho = new JComboBox<>(new String[]{"Pequeno", "Médio", "Grande"});
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(lblTamanho, gbc);
        gbc.gridx = 1;
        panel.add(cbTamanho, gbc);

        JLabel lblEmbalagem = new JLabel("Embalagem:");
        cbEmbalagem = new JComboBox<>(new String[]{"Lata", "Vidro", "Plástico"});
        gbc.gridy = 4; gbc.gridx = 0;
        panel.add(lblEmbalagem, gbc);
        gbc.gridx = 1;
        panel.add(cbEmbalagem, gbc);

        btnSalvar = new JButton(categoria == null ? "Cadastrar" : "Salvar");
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.addActionListener(e -> salvarCategoria());
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(btnSalvar, gbc);

        add(panel);

        if (categoria != null) {
            preencherCampos(categoria);
        }
    }

    private void preencherCampos(Categoria c) {
        txtId.setText(String.valueOf(c.getId()));
        txtNome.setText(c.getNome());
        cbTamanho.setSelectedItem(c.getTamanho());
        cbEmbalagem.setSelectedItem(c.getEmbalagem());
    }

    private void salvarCategoria() {
        try {
            Categoria c = categoriaEdicao != null ? categoriaEdicao : new Categoria();
            c.setNome(txtNome.getText());
            c.setTamanho(cbTamanho.getSelectedItem().toString());
            c.setEmbalagem(cbEmbalagem.getSelectedItem().toString());

            CategoriaDAO dao = new CategoriaDAO();
            if (categoriaEdicao == null) {
                dao.adicionar(c);
            } else {
                dao.atualizar(c);
            }

            JOptionPane.showMessageDialog(this, categoriaEdicao == null ? "Categoria cadastrada!" : "Categoria atualizada!");
            if (onSaveCallback != null) onSaveCallback.run();
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar categoria!");
        }
    }
}