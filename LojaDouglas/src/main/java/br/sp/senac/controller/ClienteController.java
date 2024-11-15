package br.sp.senac.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import br.sp.senac.dao.ClienteDAO;
import br.sp.senac.model.Cliente;

@WebServlet(name = "clientes", urlPatterns = { "/clientes", "/clientes/novo", "/clientes/cadastro", 
					"/clientes/listar","/clientes/editar", "/clientes/update", "/clientes/excluir" })

public class ClienteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ClienteDAO clienteDAO;

	public ClienteController() {
		super();
		clienteDAO = new ClienteDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();

		try {
			switch (action) {

			case "/clientes/novo":
				novo(request, response);
				break;

			case "/clientes/listar":
				listar(request, response);
				break;

			case "/clientes/cadastro":
				RequestDispatcher dispatcher = request.getRequestDispatcher("/views/clientes/cliente-cadastro.jsp");
				dispatcher.forward(request, response);
				break;

			case "/clientes/excluir":
				excluir(request, response);
				break;

			case "/clientes/editar":
				editarForm(request, response);
				break;

			case "/clientes/update":
				update(request, response);
				break;

			default:
				listar(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}

	}

	private void listar(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		ArrayList<Cliente> listaClientes = clienteDAO.listar();
		request.setAttribute("listaClientes", listaClientes);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/clientes/cliente-listar.jsp");
		dispatcher.forward(request, response);
	}

	private void novo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Cliente novoCliente = new Cliente();
		String nome = request.getParameter("nome");
		String cpf = request.getParameter("cpf");

		if (nome != null && cpf != null) {

			novoCliente.setNome(nome);
			novoCliente.setCpf(cpf);

			boolean retorno = clienteDAO.inserir(novoCliente);
			
			if (retorno) {
	            response.sendRedirect("/clientes/listar");
	        } else {
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar cliente.");
	        }
		}

		try {
			listar(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void editarForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		Cliente clienteAlterar = clienteDAO.buscarPorId(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/clientes/cliente-cadastro.jsp");
		request.setAttribute("cliente", clienteAlterar);
		dispatcher.forward(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");

		Cliente clienteAtualizar = new Cliente();
		clienteAtualizar.setIdCliente(id);
		clienteAtualizar.setNome(nome);

		clienteDAO.atualizar(clienteAtualizar);
		response.sendRedirect("listar");
	}

	private void excluir(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		clienteDAO.excluir(id);
		response.sendRedirect("listar");
	}
}