<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manutenção de Produtos</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <form action="${pageContext.request.contextPath}/produtos/novo" method="post">
    Nome: <input type="text" name="nome" value="${produto != null ? produto.nome : ''}" required /><br>
    Descrição: <input type="text" name="descricao" value="${produto != null ? produto.descricao : ''}" required /><br>
    <input type="submit" value="Salvar" />
    <input type="button" value="Cancelar" onclick="window.location.href='${pageContext.request.contextPath}/produtos/listar';" />
</form>
</body>
</html>