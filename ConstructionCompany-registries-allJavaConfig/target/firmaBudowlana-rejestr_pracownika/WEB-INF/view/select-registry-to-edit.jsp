<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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

<title>Edycja Rejestru</title>
</head>
<body>
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Edycja rejestrów - wybierz projekt i podaj datę rejestru</h1>
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
		<form:form action="edytujRejestr/lista" modelAttribute="project" method="POST">
			<div class="form-group">
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label">Kierownik budowy:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label"><b>${user.firstName}&nbsp;${user.lastName}</b></label>
					</div>
				</div>
				<sec:authorize access="hasRole('ADMIN')">
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="id">Wybierz projekt:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<form:select path="id" class="form-control">
							<c:forEach var="tempProject" items="${fullProjectList}">
								<form:option value="${tempProject.id}" label="${tempProject.projectName}"/>
							</c:forEach>	
						</form:select>
					</div>
				</div>
				</sec:authorize>
				<sec:authorize access="!hasRole('ADMIN')">
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
				</sec:authorize>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="registryDate">Data rejestru (*):</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<input type="text" name="registryDate" placeholder="yyyy-MM-dd" class="form-control input-md"/>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label"></label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<button type="submit" class="btn btn-success btn-block">Potwierdź</button>
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
		<div class="row" style="padding-top: 20px">
			(*) - pole obowiązkowe
		</div>
	</div>	

</body>
</html>