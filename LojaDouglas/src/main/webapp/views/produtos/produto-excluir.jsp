<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Excluir Produto</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h2>Excluir Produto</h2>
    <p>Tem certeza de que deseja excluir o produto?</p>
    <p>ID: ${produto.idProduto}</p>
    <p>Marca: ${produto.marca}</p>
    <p>Nome: ${produto.nome}</p>
    <p>Descrição: ${produto.descricao}</p>

    <form action="${pageContext.request.contextPath}/produtos/excluir" method="post">
        <input type="hidden" name="idProduto" value="${produto.idProduto}"/>
        <input type="submit" value="Excluir Produto"/>
    </form>
    <input type="button" value="Cancelar" onclick="window.location.href='${pageContext.request.contextPath}/produtos/listar';"/>
</body>
</html>