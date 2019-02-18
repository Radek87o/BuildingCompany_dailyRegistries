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

<title>Edytuj rejestr</title>
</head>
<body>
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Edytuj rejestr</h1>
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
		<form:form action="zapiszRejestr" modelAttribute="registry" method="POST" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="project.id"/>
			<form:hidden path="employee.id"/>
			<div class="form-group">
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label">Projekt:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label"><b>${registry.project.projectName}</b></label>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label">Pracownik:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label"><b>${registry.employee.firstName}&nbsp;${registry.employee.lastName}</b></label>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="date">Data:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<%-- <form:input path="date" class="form-control input-md"/> --%>
						<input type="text" name="date" value="${strDate}" class="form-control input-md"/>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="workingTime">Czas pracy:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<form:input path="workingTime" class="form-control input-md"/>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="absence">Nieobecność:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<form:select path="absence" class="form-control">
							<form:option value="nd." label="nd." />
							<form:options items="${absence}" />
						</form:select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="catering.id">Catering:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<form:select path="catering.id" class="form-control">
							<form:option value="0" label="--------" />
							<c:forEach var="tempCatering" items="${cateringList}">
								<form:option value="${tempCatering.id}"
									label="${tempCatering.cateringName}" />
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label" for="accommodation.id">Nocleg:</label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<form:select path="accommodation.id" class="form-control">
							<form:option value="0" label="--------"/>
							<c:forEach var="tempAccommodation" items="${accommodationList}">
								<form:option value="${tempAccommodation.id}"
									label="${tempAccommodation.accommodationName}" />
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label"></label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<button type="submit" class="btn btn-success btn-block">Zapisz</button>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="padding-top: 10px">
						<label class="control-label"></label>
					</div>
					<div class="col-md-4" style="padding-top: 10px">
						<button type="button" onclick="window.location.href='../wypelnijRejestr'; return false;" class="btn btn-secondary btn-block">Wróć do strony głównej</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>