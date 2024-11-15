<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manutenção de Clientes</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
	<form
		action="${cliente == null ? '/clientes/novo' : '/clientes/update'}"
		method="post">
		<input type="hidden" name="id"
			value="${cliente != null ? cliente.id : ''}" /> Nome: <input
			type="text" name="nome"
			value="${cliente != null ? cliente.nome : ''}" required /><br>
		CPF: <input type="text" name="cpf"
			value="${cliente != null ? cliente.cpf : ''}" required /><br> <input
			type="submit" value="Salvar"> <input type="button"
			value="Cancelar" onclick="window.location.href='cliente-listar';"/>
	</form>
</body>
</html>