package view;

import javax.swing.*;
import dao.ProdutoDAO;


public class TelaReajuste extends JFrame {
    private JTextField txtPercentual;

    public TelaReajuste() {
        setTitle("Reajustar Preços");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblPercentual = new JLabel("Percentual de Reajuste (%):");
        txtPercentual = new JTextField(10);
        JButton btnReajustar = new JButton("Reajustar");

        JPanel panel = new JPanel();
        panel.add(lblPercentual);
        panel.add(txtPercentual);
        panel.add(btnReajustar);

        add(panel);

        btnReajustar.addActionListener(e -> reajustarPrecos());
    }


    private void reajustarPrecos() {
        try {
            double percentual = Double.parseDouble(txtPercentual.getText());
            ProdutoDAO dao = new ProdutoDAO();
            dao.atualizar_percentual(percentual);
            JOptionPane.showMessageDialog(this, "Preços reajustados em " + percentual + "% com sucesso!");
            txtPercentual.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Insira um percentual válido!");
        }
    }
}