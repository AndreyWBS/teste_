package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Movimento;
import util.Conexao;

// Classe que cuida das movimentações de entrada e saída de produtos
public class MovimentoDAO {

    // Adiciona uma movimentação (entrada/saída) e atualiza o estoque do produto
    public void adicionar(Movimento movimento) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "INSERT INTO movimento (produto_id, quantidade, tipo, data_movimentacao) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movimento.getProdutoId());
            stmt.setInt(2, movimento.getQuantidade());
            stmt.setString(3, movimento.getTipo());
            stmt.setTimestamp(4, new Timestamp(movimento.getDataMovimentacao().getTime()));
            stmt.executeUpdate();
            stmt.close();

            // Atualiza o estoque do produto
            String atualizaSql = "";
            if (movimento.getTipo().equals("ENTRADA")) {
                atualizaSql = "UPDATE produto SET quantidade_estoque = quantidade_estoque + ? WHERE id = ?";
            } else {
                atualizaSql = "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?";
            }
            PreparedStatement atualizaStmt = conn.prepareStatement(atualizaSql);
            atualizaStmt.setInt(1, movimento.getQuantidade());
            atualizaStmt.setInt(2, movimento.getProdutoId());
            atualizaStmt.executeUpdate();
            atualizaStmt.close();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar movimento: " + e.getMessage());
        }
    }

    // Retorna todas as movimentações do banco
    public List<Movimento> listar() {
        List<Movimento> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.getConnection();
            String sql = "SELECT * FROM movimento";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Movimento m = new Movimento();
                m.setId(rs.getInt("id"));
                m.setProdutoId(rs.getInt("produto_id"));
                m.setQuantidade(rs.getInt("quantidade"));
                m.setTipo(rs.getString("tipo"));
                m.setDataMovimentacao(rs.getTimestamp("data_movimentacao"));
                lista.add(m);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar movimentos: " + e.getMessage());
        }
        return lista;
    }

    // Verifica se o estoque está abaixo do mínimo ou acima do máximo
    public String checarEstoque(int produtoId) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "SELECT quantidade_estoque, quantidade_minima, quantidade_maxima FROM produto WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, produtoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int estoque = rs.getInt("quantidade_estoque");
                int minimo = rs.getInt("quantidade_minima");
                int maximo = rs.getInt("quantidade_maxima");
                rs.close();
                stmt.close();
                conn.close();

                if (estoque < minimo) {
                    return "Atenção: Estoque abaixo do mínimo!";
                } else if (estoque > maximo) {
                    return "Atenção: Estoque acima do máximo!";
                }
            } else {
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao checar estoque: " + e.getMessage());
        }
        return null;
    }

    public String atualizar(Movimento movimento) {
        try {
            Connection conn = Conexao.getConnection();

            // Atualiza o estoque do produto conforme o tipo de movimentação
            String atualizaSql;
            if (movimento.getTipo().equals("ENTRADA")) {
                atualizaSql = "UPDATE produto SET quantidade_estoque = quantidade_estoque + ? WHERE id = ?";
            } else {
                atualizaSql = "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?";
            }
            PreparedStatement atualizaStmt = conn.prepareStatement(atualizaSql);
            atualizaStmt.setInt(1, movimento.getQuantidade());
            atualizaStmt.setInt(2, movimento.getProdutoId());
            atualizaStmt.executeUpdate();
            atualizaStmt.close();

            // Busca os valores atualizados para verificar limites
            String sql = "SELECT quantidade_estoque, quantidade_minima, quantidade_maxima FROM produto WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movimento.getProdutoId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int estoque = rs.getInt("quantidade_estoque");
                int minimo = rs.getInt("quantidade_minima");
                int maximo = rs.getInt("quantidade_maxima");
                rs.close();
                stmt.close();
                conn.close();

                if (estoque < minimo) {
                    return "Atenção: Estoque abaixo do mínimo!";
                } else if (estoque > maximo) {
                    return "Atenção: Estoque acima do máximo!";
                }
            } else {
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar estoque: " + e.getMessage());
        }
        return null;
    }

    // Remove uma movimentação pelo id (não atualiza o estoque do produto, só remove
    // o registro)
    public void remover(int idMovimento) {
        try {
            Connection conn = Conexao.getConnection();
            String sql = "DELETE FROM movimento WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idMovimento);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao remover movimento: " + e.getMessage());
        }
    }
}