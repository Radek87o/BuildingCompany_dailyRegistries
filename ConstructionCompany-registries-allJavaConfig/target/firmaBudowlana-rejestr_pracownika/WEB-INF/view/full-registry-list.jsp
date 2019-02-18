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

<title>Pełna lista rejestrów</title>
</head>
<body>
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Lista rejestrów widok koordynatora</h1>
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
		<form:form action="pelnaListaRejestrow/Filtruj" class="form-horizontal">
			<div class="form-group">
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="projectId">Wybierz projekt:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<select name="projectId" class="form-control">
							<option value="0" label="--------">
							<c:forEach var="tempProject" items="${projectList}">
								<option value="${tempProject.id}">${tempProject.projectName}</option>
							</c:forEach> 
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="startDate">Podaj datę początkową:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<select name="startDate" class="form-control">
							<c:forEach var="tempDate" items="${registryDates}">
								<option value="${tempDate}" label="${tempDate}"/>						
							</c:forEach> 
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="endDate">Podaj datę końcową:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<select name="endDate" class="form-control">
							<c:forEach var="tempDate" items="${registryDates}">
								<option value="${tempDate}" label="${tempDate}"/>						
							</c:forEach> 
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="status">Podaj status projektu:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<select name="status" class="form-control">
							<option value="false" label="aktywny">
							<option value="true" label="zakończony">
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label"></label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<button type="submit" class="btn btn-success btn-block">Filtruj rejestry</button>
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
		 <legend>Pełna lista rejestrów</legend>
			<table>
				<tbody>
					<tr>
						<th>Data rejestru</th>
						<th>Projekt</th>
						<th>Pracownik</th>
						<th>Czas Pracy</th>
						<th>Nieobecność</th>
						<th>Catering</th>
						<th>Nocleg</th>
					</tr>
					<c:forEach var="tempRegistry" items="${registries}">
						<tr>
							<td>${tempRegistry.date}</td>
							<td>${tempRegistry.project.projectName}</td>
							<td>${tempRegistry.employee.firstName}&nbsp;${tempRegistry.employee.lastName}</td>
							<td>${tempRegistry.workingTime}</td>
							<td>${tempRegistry.absence}</td>
							<td>${tempRegistry.catering.cateringName}</td>
							<td>${tempRegistry.accommodation.accommodationName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
</body>
</html>