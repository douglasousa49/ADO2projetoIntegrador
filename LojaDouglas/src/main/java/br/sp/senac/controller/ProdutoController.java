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

import br.sp.senac.dao.ProdutoDAO;
import br.sp.senac.model.Produto;

@WebServlet(name = "produtos", urlPatterns = { "/produtos", "/produtos/novo", "/produtos/cadastro",
        "/produtos/listar", "/produtos/editar", "/produtos/update", "/produtos/excluir" })

public class ProdutoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProdutoDAO produtoDAO;

    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();

        try {
            switch (action) {
                case "/produtos/novo":
                    novo(request, response);
                    break;
                case "/produtos/listar":
                    listar(request, response);
                    break;
                case "/produtos/cadastro":
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/produtos/produto-cadastro.jsp");
                    dispatcher.forward(request, response);
                    break;
                case "/produtos/excluir":
                    excluirForm(request, response);
                    break;
                case "/produtos/editar":
                    editarForm(request, response);
                    break;
                case "/produtos/update":
                    update(request, response);
                    break;
                case "/produtos/confirmarExclusao":
                    excluirForm(request, response);
                    break;    
                default:
                    listar(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();

        try {
            switch (action) {
                case "/produtos/novo":
                    novo(request, response);
                    break;
                case "/produtos/update":
                    update(request, response);
                    break;
                case "/produtos/editar":
                    editarForm(request, response);
                    break;
                case "/produtos/excluir":
                    excluirForm(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não suportada para POST");
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }


    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        ArrayList<Produto> listaProdutos = produtoDAO.listar();
        request.setAttribute("listaProdutos", listaProdutos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/produtos/produto-listar.jsp");
        dispatcher.forward(request, response);
    }

    private void novo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Produto novoProduto = new Produto();
        novoProduto.setNome(request.getParameter("nome"));
        novoProduto.setDescricao(request.getParameter("descricao"));

        try {
            produtoDAO.inserir(novoProduto);
            listar(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao inserir produto: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/produtos/produto-cadastro.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void editarForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    
    String idProdutoStr = request.getParameter("idProduto");
    if (idProdutoStr == null || idProdutoStr.isEmpty()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do produto não informado");
        return;
    }
    
    try {
        int idProduto = Integer.parseInt(idProdutoStr);
        Produto produto = produtoDAO.buscarPorId(idProduto);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/produtos/produto-editar.jsp");
        request.setAttribute("produto", produto);
        dispatcher.forward(request, response);
    } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do produto inválido");
    }
}
    
    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        Produto produto = new Produto();
        produto.setIdProduto(Integer.parseInt(request.getParameter("idProduto")));
        produto.setMarca(request.getParameter("marca"));
        produto.setNome(request.getParameter("nome"));
        produto.setDescricao(request.getParameter("descricao"));

        produtoDAO.atualizar(produto);
        response.sendRedirect("listar");
    }

    private void excluirForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String idProdutoStr = request.getParameter("idProduto");
        if (idProdutoStr == null || idProdutoStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do produto não informado");
            return;
        }

        try {
            int idProduto = Integer.parseInt(idProdutoStr);
            boolean sucesso = produtoDAO.excluir(idProduto);

            if (sucesso) {
                response.sendRedirect(request.getContextPath() + "/produtos/listar");
            } else {
                request.setAttribute("erro", "Falha ao excluir produto.");
                response.sendRedirect(request.getContextPath() + "/produtos/listar");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do produto inválido");
        }
    }
}