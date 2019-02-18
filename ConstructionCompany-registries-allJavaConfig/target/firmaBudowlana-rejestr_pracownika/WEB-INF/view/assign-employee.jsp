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

<title>Przypisz pracownika</title>
</head>
<body>
	
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Przypisz pracownika do projektu</h1>
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
		<form:form action="dodajPracownikowDoProjektu" modelAttribute="project" method="POST" class="form-horizontal">
				<div class="form-group">
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label" for="id">Wybierz projekt:</label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<form:select path="id" class="form-control">
								<c:forEach var="tempProject" items="${projectList}">
									<form:option value="${tempProject.id}" label="${tempProject.projectName}"/>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label" for="employees">Dodaj pracowników:</label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<form:select path="employees" multiple="true" class="form-control">
								<c:forEach var="tempEmployee" items="${employeeList}">
									<form:option value="${tempEmployee.id}">${tempEmployee.firstName}&nbsp;${tempEmployee.lastName}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label"></label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<button type="submit" class="btn btn-success btn-block">Przypisz pracowników do projektu</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label"></label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<button type="button" onclick="window.location.href='../rejestr/wypelnijRejestr'; return false;" class="btn btn-secondary btn-block">Wróć do strony głównej</button>
						</div>
					</div>
				</div>
		</form:form>
	</div>

 	<div class="container" style="padding-top: 30px">
		 <legend>Liczba pracowników per projekt</legend>
			<table>
				<tbody>
					<tr>
						<th>Nazwa projektu</th>
						<th>Liczba pracowników</th>
						<th>Wykonaj akcję</th>
					</tr>
					<c:forEach var="tempProject" items="${employeesPerProject}">
						<c:url var="updateLink" value="/koordynator/przypiszPracownika/update">
							<c:param name="projectId" value="${tempProject.key.id}"></c:param>
						</c:url>
						<tr>
							<td>${tempProject.key.projectName}</td>
							<td>${tempProject.value}</td>
							<td><a href="${updateLink}">Edytuj</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>	
</body>
</html>