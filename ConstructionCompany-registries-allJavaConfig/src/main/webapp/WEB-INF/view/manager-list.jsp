<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
	
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">

<!-- Reference Bootstrap files -->
	<link rel="stylesheet"
		 href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Lista kierowników</title>
</head>
<body>
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Lista kierowników</h1>
				</div>
			</div>
		</div>
	</section>
	
	<!-- logout button -->
	<section>
		<div class="container">
			<div class="row" style="padding-top: 20px">
				
				<div class="col-md-11" align="right" >
       				<form:form action="${pageContext.request.contextPath}/logout" method="POST">
       					<input type="submit" value="Wyloguj" class="btn btn-danger btn-md"/>		
       				</form:form>
				</div>
			</div>
		</div>
	</section>
	
	
	<div class="container" style="padding-top: 30px">
		<table>
			<tr>
				<th>Nazwa użytkownika</th>
				<th>Imię</th>
				<th>Nazwisko</th>
				<th>Wykonaj akcję</th>
			</tr>
		
			<tbody>
				<c:forEach var="tempUser" items="${userList}">
					<!-- defining a link to update the user -->
					<c:url var="updateLink" value="/koordynator/updateKierownika">
						<c:param name="userId" value="${tempUser.id}"/>	
					</c:url>
					
					<!-- defining a link to update the user -->
					<c:url var="deleteLink" value="/koordynator/usunKierownika">
						<c:param name="userId" value="${tempUser.id}"/>	
					</c:url>
					<tr>
						<td>${tempUser.username}
						<td>${tempUser.firstName}</td>
						<td>${tempUser.lastName}</td>
						<td>
							<a href="${updateLink}">Zmień</a>
							&nbsp;|&nbsp;
							<a href="${deleteLink}" onclick="if(!(confirm('Czy na pewno chcesz usunąć tego kierownika?'))) return false">Usuń</a>
						</td>
					</tr>
				</c:forEach>	
			</tbody>
		</table>
		<div style="padding-top: 20px">
			<button style="margin-left: 20px" type="button" onclick="window.location.href='../rejestr/wypelnijRejestr'; return false;" class="btn-secondary btn-md">Wróć do strony głównej</button>
		</div>
	</div>
	
</body>
</html>