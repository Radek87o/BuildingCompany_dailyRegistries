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

<title>Dodaj projekt</title>


</head>
<body>
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Dodaj projekt</h1>
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
		<form:form action="zapiszProjekt" modelAttribute="project" method="POST" class="form-horizontal">
			<form:hidden path="id"/>
				<div class="form-group">
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label" for="projectName">Nazwa projektu:</label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<form:errors path="projectName" class="error"/>
							<form:input path="projectName" class="form-control input-md"/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label" for="user.id">Przypisz kierownika budowy:</label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<form:select path="user.id" class="form-control">
								<c:forEach var="tempUser" items="${userList}">
									<form:option value="${tempUser.key}" label="${tempUser.value}"/>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label" for="projectStatus">Status projektu:</label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<form:select path="projectStatus" class="form-control">
								<form:option value="0" label="aktualny"/>
								<form:option value="1" label="zamknięty"/>
							</form:select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label"></label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<button type="submit" class="btn btn-success btn-block">Zapisz projekt</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label"></label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<button type="button" onclick="window.location.href='../koordynator/dodajProjekt'; return false;" class="btn-secondary btn-md btn-block">Wypełnij ponownie</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" style="padding-top: 10px">
							<label class="control-label"></label>
						</div>
						<div class="col-md-4" style="padding-top: 10px">
							<button type="button" onclick="window.location.href='../rejestr/wypelnijRejestr'; return false;" class="btn-secondary btn-md btn-block">Wróć do strony głównej</button>
						</div>
					</div>
					
				</div>
		</form:form>
	</div>
		<div class="container" style="padding-top: 30px">
			<table>
				<tbody>
					<tr>
						<th>Nazwa Projektu</th>
						<th>Kierownik projektu</th>
						<th>Wykonaj akcję:</th>
					</tr>
					<c:forEach var="tempProject" items="${projectList}"> 
						<c:url var="updateLink" value="/koordynator/updateProjektu">
								<c:param name="projectId" value="${tempProject.id}"/>
						</c:url>
						<c:url var="deleteLink" value="/koordynator/usunProjekt">
								<c:param name="projectId" value="${tempProject.id}"/>
						</c:url>
						<tr>
							<td>${tempProject.projectName}</td>
							<td>${tempProject.user.firstName}&nbsp;${tempProject.user.lastName}</td>
							<td>
								<a href="${updateLink}">Zmień</a>
								&nbsp;|&nbsp;
								<a href="${deleteLink}" onclick="if(!(confirm('Czy na pewno chcesz usunąć ten projekt?'))) return false">Usuń</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

</body>
</html>