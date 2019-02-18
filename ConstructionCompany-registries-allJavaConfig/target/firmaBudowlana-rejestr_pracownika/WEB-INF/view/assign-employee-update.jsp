<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

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
	
<title>Pracownicy projektu</title>
</head>
<body>
	
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Lista pracowników projektu</h1>
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
	
	<div class="container">
		<form:form action="/przypiszPracownika/usun" modelAttribute="project" method="GET" class="form-horizontal">
			<form:hidden path="id"/>
				<div class="form-group">
					<div class="row">
						<div class="col-md-12" align="left" style="padding-top: 10px">
							<form:label path="projectName">Projekt:&nbsp;<b>${project.projectName}</b></form:label>
						</div>
					</div>
					<table>
						<tbody>
							<tr>
								<th>Imię</th>
								<th>Nazwisko</th>
								<th>Wykonaj akcję</th>
							</tr>
							<c:forEach var="tempEmployee" items="${employeeList}">
								<c:url var="removeLink" value="/koordynator/przypiszPracownika/usun">
									<c:param name="employeeId" value="${tempEmployee.id}"/>
								</c:url>
								<tr>
									<td>${tempEmployee.firstName}</td>
									<td>${tempEmployee.lastName}</td>	
									<td>
										<a href="${removeLink}" onclick="if(!(confirm('Czy na pewno chcesz wypisać tego pracownika z projektu?'))) return false">Wypisz z projektu</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
		</form:form>
		<div>
			<div class="row">
				<div class="col-md-4" style="padding-top: 10px">
					<button type="button" onclick="window.location.href='../przypiszPracownika'; return false;" class="btn btn-secondary btn-block">Przypisz pracowników do projektu</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>