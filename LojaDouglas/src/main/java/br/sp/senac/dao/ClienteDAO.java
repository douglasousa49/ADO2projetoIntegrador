package br.sp.senac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.sp.senac.model.Cliente;
import br.sp.senac.utils.ConnectionFactory;

public class ClienteDAO {

	Connection conexao;

	public boolean inserir(Cliente cliente) {
		boolean retorno = false;

		try {
			conexao = ConnectionFactory.getConnection();
			String sql = "INSERT INTO Cliente (CPF,nome) VALUES(?,?)";

			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setString(1, cliente.getCpf());
			ps.setString(2, cliente.getNome());

			int linhasAfetadas = ps.executeUpdate();

			if (linhasAfetadas > 0) {
				retorno = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return retorno;

	}

	public ArrayList<Cliente> listar() {

		ArrayList<Cliente> clientes = new ArrayList<>();

		String sql = "SELECT * FROM Cliente";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("idCliente");
				String nome = rs.getString("nome");
				String cpf = rs.getString("cpf");

				Cliente itemLista = new Cliente();
				itemLista.setIdCliente(id);
				itemLista.setCpf(cpf);
				itemLista.setNome(nome);

				clientes.add(itemLista);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clientes;
	}

	public Cliente buscarPorId(int id) {

		Cliente objRetorno = null;

		String sql = "SELECT * FROM Cliente WHERE idCliente = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)

		) {
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String nome = rs.getString("nome");
				String cpf = rs.getString("cpf");

				objRetorno = new Cliente();

				objRetorno.setIdCliente(id);
				objRetorno.setCpf(cpf);
				objRetorno.setNome(nome);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return objRetorno;
	}

	public boolean atualizar(Cliente clienteAtualizar) {
		boolean retorno = false;

		try {
			conexao = ConnectionFactory.getConnection();

			String sql = "UPDATE cliente SET nome=? WHERE idcliente = ?";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, clienteAtualizar.getNome());
			ps.setInt(2, clienteAtualizar.getIdCliente());

			int linhasAfetadas = ps.executeUpdate();
			if (linhasAfetadas > 0) {
				retorno = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return retorno;
	}

	public boolean excluir(int id) {

		boolean retorno = false;
		try {

			conexao = ConnectionFactory.getConnection();
			String sql = "DELETE FROM cliente WHERE idcliente = ?";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, id);

			int linhasAfetadas = ps.executeUpdate();

			if (linhasAfetadas > 0) {
				retorno = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return retorno;
	}
}