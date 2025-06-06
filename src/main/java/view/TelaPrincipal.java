package view;

import javax.swing.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Sistema de Controle de Estoque");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnCategoria = new JButton("Gerenciar Categorias");
        JButton btnProduto = new JButton("Gerenciar Produtos");
        JButton btnMovimento = new JButton("Movimentar Estoque");
        JButton btnRelatorio = new JButton("Relatórios");
        JButton btnReajuste = new JButton("Reajustar Preços");

        JPanel panel = new JPanel();
        panel.add(btnCategoria);
        panel.add(btnProduto);
        panel.add(btnMovimento);
        panel.add(btnRelatorio);
        panel.add(btnReajuste);

        add(panel);

        btnCategoria.addActionListener(e -> new TelaListarCategoria().setVisible(true));
        btnProduto.addActionListener(e -> new TelaListarProduto().setVisible(true));
        btnMovimento.addActionListener(e -> new TelaListarMovimento().setVisible(true));
        btnRelatorio.addActionListener(e -> new TelaRelatorio().setVisible(true));
        btnReajuste.addActionListener(e -> new TelaReajuste().setVisible(true));
    }

    public static void main(String[] args) {
        new TelaPrincipal().setVisible(true);
    }
}