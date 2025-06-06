package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Produto;
import util.Conexao;

// Classe DAO para manipular produtos no banco de dados
public class ProdutoDAO {

    // Adiciona um novo produto
    public void adicionar(Produto produto) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "INSERT INTO produto (nome, preco_unitario, unidade, quantidade_estoque, quantidade_minima, quantidade_maxima, categoria_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoUnitario());
            stmt.setString(3, produto.getUnidade());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getQuantidadeMinima());
            stmt.setInt(6, produto.getQuantidadeMaxima());
            stmt.setInt(7, produto.getCategoriaId());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    // Lista todos os produtos
    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.getConnection();
            String sql = "SELECT * FROM produto";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPrecoUnitario(rs.getDouble("preco_unitario"));
                p.setUnidade(rs.getString("unidade"));
                p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                p.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                p.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));
                p.setCategoriaId(rs.getInt("categoria_id"));
                lista.add(p);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        return lista;
    }

    // Atualiza os dados de um produto
    public void atualizar(Produto produto) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "UPDATE produto SET nome = ?, preco_unitario = ?, unidade = ?, quantidade_estoque = ?, quantidade_minima = ?, quantidade_maxima = ?, categoria_id = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoUnitario());
            stmt.setString(3, produto.getUnidade());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getQuantidadeMinima());
            stmt.setInt(6, produto.getQuantidadeMaxima());
            stmt.setInt(7, produto.getCategoriaId());
            stmt.setInt(8, produto.getId());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }
    // autera o percentual de todos 
    public void atualizar_percentual(double porcentual) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "UPDATE produto SET nome_da_coluna = preco_unitario * (1 + ? / 100)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, porcentual);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    public List<Object[]> listarQuantidadePorCategoria(){
        try {
            Connection conn = Conexao.getConnection();
            String sql = "";
        } catch (Exception e) {
        }
        List sla = new ArrayList();
        
        return sla;
    }

    // Remove um produto pelo id
    public void remover(int id) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "DELETE FROM produto WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
        }
    }
}
