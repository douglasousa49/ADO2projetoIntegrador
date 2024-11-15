package br.sp.senac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.sp.senac.model.Produto;
import br.sp.senac.utils.ConnectionFactory;

public class ProdutoDAO {

    private Connection conexao;

    public boolean inserir(Produto produto) {
        boolean retorno = false;

        try {
            conexao = ConnectionFactory.getConnection();
            String sql = "INSERT INTO produto (marca, nome, descricao) VALUES (?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, produto.getMarca());
            ps.setString(2, produto.getNome());
            ps.setString(3, produto.getDescricao());

            retorno = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return retorno;
    }

    public ArrayList<Produto> listar() {
        ArrayList<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM Produto";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setMarca(rs.getString("marca"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));

                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public Produto buscarPorId(int id) {
        Produto produto = null;
        String sql = "SELECT * FROM Produto WHERE idProduto = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setMarca(rs.getString("marca"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    public boolean atualizar(Produto produto) {
        boolean retorno = false;
        String sql = "UPDATE Produto SET nome = ?, descricao = ? WHERE idProduto = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
        	
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setInt(3, produto.getIdProduto());
            
            retorno = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public boolean excluir(int id) {
        boolean retorno = false;
        String sql = "DELETE FROM Produto WHERE idProduto = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            retorno = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }
}