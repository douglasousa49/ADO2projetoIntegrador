<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Produtos</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h2>Lista de Produtos</h2>
    <table border="1" cellspacing="0" cellpadding="5">
        <thead>
            <tr>
                <th>ID</th>
                <th>Marca</th>
                <th>Nome</th>
                <th>Descrição</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="produto" items="${listaProdutos}">
                <tr>
                    <td>${produto.idProduto}</td>
                    <td>${produto.marca}</td>
                    <td>${produto.nome}</td>
                    <td>${produto.descricao}</td>
                    <td>
						<a href="${pageContext.request.contextPath}/produtos/editar?idProduto=${produto.idProduto}">Editar</a>
						<a href="${pageContext.request.contextPath}/produtos/excluir?idProduto=${produto.idProduto}">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <a href="${pageContext.request.contextPath}/produtos/cadastro">Novo Produto</a>
</body>
</html>