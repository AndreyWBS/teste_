package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import util.Conexao;

// Classe responsável por acessar o banco de dados e manipular categorias
public class CategoriaDAO {

    // Adiciona uma nova categoria no banco
    public void adicionar(Categoria categoria) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "INSERT INTO categoria (nome, tamanho, embalagem) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getTamanho());
            stmt.setString(3, categoria.getEmbalagem());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar categoria: " + e.getMessage());
        }
    }

    // Retorna uma lista de todas as categorias do banco
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.getConnection();
            String sql = "SELECT * FROM categoria";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTamanho(rs.getString("tamanho"));
                c.setEmbalagem(rs.getString("embalagem"));
                lista.add(c);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar categorias: " + e.getMessage());
        }
        return lista;
    }

    // Atualiza os dados de uma categoria no banco
    public void atualizar(Categoria categoria) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "UPDATE categoria SET nome = ?, tamanho = ?, embalagem = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getTamanho());
            stmt.setString(3, categoria.getEmbalagem());
            stmt.setInt(4, categoria.getId());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    // Remove uma categoria pelo id
    public void remover(int id) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "DELETE FROM categoria WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao remover categoria: " + e.getMessage());
        }
    }
}