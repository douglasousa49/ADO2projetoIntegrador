<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edição de Produto</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h2>Edição de Produto</h2>    
    <form action="${pageContext.request.contextPath}/produtos/update" method="post">
    <input type="hidden" name="idProduto" value="${produto.idProduto}"/>
    <p>ID: ${produto.idProduto}</p>
    <p>Marca: ${produto.marca}</p>
    <p>Nome: ${produto.nome}</p>
    <p>Descrição: ${produto.descricao}</p>
    Novo nome: <input type="text" name="nome" value="${produto.nome}" required/><br>
    Nova descrição: <input type="text" name="descricao" value="${produto.descricao}" required/><br>
    <input type="submit" value="Salvar Alterações"/>
    <input type="button" value="Cancelar" onclick="window.location.href='listar'"/>
</form> 
</body>
</html>