<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">


<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
	
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">

<!-- Reference Bootstrap files -->
	<link rel="stylesheet"
		 href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Potwierdź rejestr</title>
</head>
<body>
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Potwierdź rejestr pracowników</h1>
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
			<tbody>
				<tr>
					<th>Projekt</th>
					<th>Data rejestru</th>
					<th>Wykonaj akcję</th>
				</tr>
				<tr>
					<td><b>${project.projectName}</b></td>		
					<td><b><fmt:formatDate type="date" value="${registryDate}"/></b></td>
					<c:url var="deleteLink" value="/rejestr/potwierdzRejestr/usun">
						<c:param name="projectId" value="${project.id}"/>
						<c:param name="registryDate" value="${registryDate}"/>
					</c:url>
					<td><a href="${deleteLink}" onclick="if(!(confirm('Czy na pewno chcesz usunąć rejestry?'))) return false">Usuń rejestry</a></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="container" style="padding-top: 30px">
		<table>
			<tbody>
				<tr>
					<th>Pracownik</th>
					<th>Czas Pracy</th>
					<th>Nieobecność</th>
					<th>Catering</th>
					<th>Nocleg</th>
					<th>Wykonaj akcję</th>
				</tr>
				<c:forEach var="tempRegistry" items="${registries}">
					
					<c:url var="updateLink" value="/rejestr/potwierdzRejestr/update">
						<c:param name="registryId" value="${tempRegistry.id}"></c:param>
					</c:url>
					<tr>
						<td><label>${tempRegistry.employee.firstName}&nbsp;${tempRegistry.employee.lastName}</label></td>
						<td><label>${tempRegistry.workingTime}</label></td>
						<td><label>${tempRegistry.absence}</label></td>
						<td><label>${tempRegistry.catering.cateringName}</label></td>
						<td><label>${tempRegistry.accommodation.accommodationName}</label></td>
						<td><a href="${updateLink}">Edytuj</a></td>
					</tr>
					
				</c:forEach>			
			</tbody>
		</table>
		<div class="row" style="padding-top: 20px">
			<div class="col-md-4">
				<button type="button" onclick="window.location.href='../rejestr/wypelnijRejestr'; return false;" class="btn-secondary btn-md btn-block">Wróć do strony głównej</button>
			</div>	
		</div>
	</div>
</body>
</html>