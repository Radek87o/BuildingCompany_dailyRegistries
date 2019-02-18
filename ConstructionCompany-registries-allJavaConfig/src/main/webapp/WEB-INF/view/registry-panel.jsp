<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

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

<title>Panel rejestru</title>
</head>
<body>
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Dzienny rejestr pracowników</h1>
				</div>
			</div>
		</div>
	</section>
	
	<!-- login data section -->
	<section>
		<div class="container">
			<div class="row" style="padding-top: 20px">
				<div class="col-sm-4">
       				<div>
       					<b>Użytkownik:&nbsp; <sec:authentication property="principal.username"/></b>
      				</div>
				</div>
				<div class="col-sm-4">
       				<div>
       					<b>Stanowisko:&nbsp; ${position}</b>
      				</div>
				</div>
				<div class="col-sm-4" align="right" >
       				<form:form action="${pageContext.request.contextPath}/logout" method="POST">
       					<input type="submit" value="Wyloguj" class="btn btn-danger btn-md"/>		
       				</form:form>
				</div>
			</div>
		</div>
	</section>
			
	<!-- buttons directing to the admin panel functions -->	
			
		<sec:authorize access="hasRole('ADMIN')">
			<div class="container">
				<div class="row" style="padding-top: 10px">	
					<div class="col-sm-4" style="padding-top: 5px">
						<button type="button" onclick="window.location.href='../koordynator/rejestrujUzytkownika'; return false;" class="btn btn-primary btn-md btn-block">Rejestruj kierownika</button>
					</div>
					<div class="col-sm-4" style="padding-top: 5px">
						<button type="button" onclick="window.location.href='../koordynator/listaKierownikow'; return false;" class="btn btn-primary btn-md btn-block">Lista Kierowników</button>
					</div>
					<div class="col-sm-4" style="padding-top: 5px">	
						<button type="button" onclick="window.location.href='../koordynator/dodajProjekt'; return false;" class="btn btn-primary btn-md btn-block">Dodaj projekt</button>
					</div>
				</div>
				<div class="row" style="padding-top: 10px">	
					<div class="col-sm-4" style="padding-top: 5px">
						<button type="button" onclick="window.location.href='../koordynator/dodajPracownika'; return false;" class="btn btn-primary btn-md btn-block">Dodaj pracownika</button>
					</div>
					<div class="col-sm-4" style="padding-top: 5px">
						<button type="button" onclick="window.location.href='../koordynator/przypiszPracownika'; return false;" class="btn btn-primary btn-md btn-block">Przypisz pracownika do projektu</button>
					</div>
					<div class="col-sm-4" style="padding-top: 5px">
						<button type="button" onclick="window.location.href='../koordynator/pelnaListaRejestrow'; return false;" class="btn btn-primary btn-md btn-block">Lista rejestrów</button>															
					</div>
				</div>
			</div>
		</sec:authorize>	
	
	<!-- copy or edit previous registries -->
			
	<section>
		<div class="container">
			<div class="row text-center">
				<div class="col-md-12" style="padding-top: 20px">
					<legend>Edycja lub kopia rejestrów (opcjonalne)</legend>
				</div>
				<div class="row" style="padding-top: 10px">
					<div class="col-md-6" style="padding-top: 5px">
						<button type="button" onclick="window.location.href='../rejestr/wybierzProjektDoSkopiowania'; return false;" class="btn btn-md btn-block">Skopiuj poprzednie rejestry</button>
					</div>
					<div class="col-md-6" style="padding-top: 5px">
						<button type="button" onclick="window.location.href='../rejestr/edytujRejestr'; return false;" class="btn btn-md btn-block">Edytuj Rejestry</button>					
					</div>
				</div>
			</div>
		</div>	
	</section>
	
	<section>
		<div class="container">
			<div class="row text-center">
				<div class="col-md-12" style="padding-top: 20px">
					<legend>Wypełnij rejestr zbiorczy dla wszystkich pracowników(*) </legend>
				</div>
				<form:form action="potwierdzRejestr" modelAttribute="registry" method="POST" class="form-horizontal">
					<form:hidden path="id" />
					<div class="col-md-12">
						<p class="registry-paragraph" align="left">
							<b>1. Wybierz catering i nocleg jeśli pracownicy korzystają</b>
						</p>
					</div>
					<div class="form-group">					
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" for="catering">Catering:</label>
							</div>
							<div class="col-md-4">
								<form:select path="catering" class="form-control">
									<form:option value="0" label="--------" />
									<c:forEach var="tempCatering" items="${cateringList}">
										<form:option value="${tempCatering.id}"
											label="${tempCatering.cateringName}" />
									</c:forEach>
								</form:select>
							</div>
							<div class="col-md-4">
								<button type="button" onclick="window.location.href='dodajCatering'; return false;" class="btn btn-md btn-block">Dodaj Catering</button>
							</div>		
						</div>
						<div class="row" style="padding-top: 10px">
							<div class="col-md-4">
								<label class="control-label" for="accommodation">Nocleg:</label>
							</div>
							<div class="col-md-4">
								<form:select path="accommodation" class="form-control">
									<form:option value="0" label="--------" />
									<c:forEach var="tempAccommodation" items="${accommodationList}">
										<form:option value="${tempAccommodation.id}"
											label="${tempAccommodation.accommodationName}" />
									</c:forEach>
								</form:select>
							</div>
							<div class="col-md-4">
								<button type="button" onclick="window.location.href='dodajNocleg'; return false;" class="btn btn-md btn-block">Dodaj Nocleg</button>
							</div>		
						</div>
					</div>
					<div class="col-md-12">
						<p class="registry-paragraph" align="left" style="padding-top: 10px">
							<b>2. Wybierz Projekt</b>
						</p>
					</div>
					<div class="form-group">
						<sec:authorize access="hasRole('ADMIN')">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" for="project">Nazwa projektu:</label>
							</div>
							<div class="col-md-4">
								<form:select path="project" class="form-control">
									<c:forEach var="tempProject" items="${fullProjectList}">
										<form:option value="${tempProject.id}"
												label="${tempProject.projectName}" />
									</c:forEach>
								</form:select>
							</div>		
						</div>
						</sec:authorize>
						<sec:authorize access="!hasRole('ADMIN')">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" for="project">Nazwa projektu:</label>
							</div>
							<div class="col-md-4">
								<form:select path="project" class="form-control">
									<c:forEach var="tempProject" items="${projectList}">
										<form:option value="${tempProject.id}"
												label="${tempProject.projectName}" />
									</c:forEach>
								</form:select>
							</div>		
						</div>	
						</sec:authorize>
					</div>
					<div class="col-md-12">
						<p class="registry-paragraph" align="left" style="padding-top: 10px">
							<b>3. Wpisz datę rejestru</b>
						</p>
					</div>
					<div class="form-group">
						<div class="col-md-4">
							<label class="control-label" for="date">Data rejestru:</label>
						</div>
						<div class="col-md-4">
							<%-- <form:errors path="strDate" class="error"/>
							<form:input path="strDate" placeholder="yyyy-MM-dd" class="form-control input-md"/> --%>
							<input type="text" name="registryDate" placeholder="yyyy-MM-dd" class="form-control input-md"/>
						</div>
					</div>
					<div class="col-md-12">
						<p class="registry-paragraph" align="left" style="padding-top: 10px">
							<b>4. Uzupełnij czas pracy i ew. nieobecność</b>
						</p>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" for="workingTime">Czas pracy (pełne godziny):</label>
							</div>
							<div class="col-md-4">
								<form:input path="workingTime" class="form-control input-md"/>
							</div>
						</div>
						<div class="row" style="padding-top: 10px">
							<div class="col-md-4">
								<label class="control-label" for="absence">Nieobecność:</label>
							</div>
							<div class="col-md-4">
								<form:select path="absence" class="form-control">
									<form:option value="nd." label="nd." />
									<form:options items="${absence}" />
								</form:select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4">
							<label class="control-label"></label>
						</div>
						<div class="col-md-4">
							<button type="submit" class="btn btn-success btn-block">Potwierdź</button>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4">
							<label class="control-label"></label>
						</div>
						<div class="col-md-4">
							<button type="button" onclick="window.location.href='../rejestr/wypelnijRejestr'; return false;" class="btn btn-md btn-block">Wypełnij ponownie</button>
						</div>
					</div>
					<div>
						<div class="col-md-12" style="padding-top: 15px">
							<p class="registry-paragraph" align="left" style="padding-top: 10px">
								(*) - w kolejnym kroku można łatwo edytować rejestr dla poszczególnych pracowników
							</p>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</section>

</body>
</html>