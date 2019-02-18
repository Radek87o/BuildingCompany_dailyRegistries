<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet" href="css/style.css"/>

<!-- Reference Bootstrap files -->
	<link rel="stylesheet"
		 href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<style type="text/css">
		.error{color: red} 
	</style>

	<title>Rejestracja kierownika</title>

</head>
<body>

		<!-- Updating user -->
		
		<c:if test="${userId != null}">
			<c:url var="updateLink" value="/koordynator/weryfikujRejestracje" >
				<c:param name="userId" value="${userId}"/>
			</c:url>
			<div>
				<div id="loginbox" style="margin-top: 50px;" class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="panel-title">Rejestracja kierownika</div>
						</div>
						<div style="padding-top: 30px" class="panel-body">
							
							<!-- Registration form -->
							<form:form action="${updateLink}" modelAttribute="appUser" method="POST" class="form-horizontal">
								<!-- Insert here messages or alerts if there are validated fields in your code -->
								<div class="form-group">
									<div class="col-xs-15">
										<div>
											<c:if test="${registrationError!= null}">
												<div class="alert alert-danger col-xs-offset-1 col-xs-10">
													${registrationError}
												</div>
											</c:if>									
										</div>
									</div>
								</div>
								
								<!-- username -->
								<div style="margin-bottom: 25px" class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
									<form:errors path="username" cssClass="error"/>
									<form:input path="username" placeholder="nazwa użytkownika" class="form-control"/>
								</div>
								<!-- password -->
								<div style="margin-bottom: 25px" class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
									<form:errors path="password" cssClass="error"/>
									<form:password path="password" placeholder="wpisz hasło" class="form-control"/>
								</div>
								<!-- confirm password -->
								<div style="margin-bottom: 25px" class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
									<form:errors path="matchingPassword" cssClass="error"/>
									<form:password path="matchingPassword" placeholder="powtórz hasło" class="form-control"/>
								</div>
								<!-- first name -->
								<div style="margin-bottom: 25px" class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
									<form:errors path="firstName" cssClass="error"/>
									<form:input path="firstName" placeholder="imię" class="form-control"/>
								</div>
								<!-- last name -->
								<div style="margin-bottom: 25px" class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
									<form:errors path="lastName" cssClass="error"/>
									<form:input path="lastName" placeholder="nazwisko" class="form-control"/>
								</div>
								
								<!-- Register button -->
								<div style="margin-top: 10px" class="form-group">						
									<div class="col-sm-6 controls">
										<button type="submit" class="btn btn-primary">Zarejestruj użytkownika</button>
									</div>
								</div>
								
								<!-- return to home page-->
								<div style="margin-top: 10px" class="form-group">						
									<div class="col-sm-6 controls">
										<button type="button" onclick="window.location.href='../rejestr/wypelnijRejestr'; return false;" class="btn btn-secondary">Wróć do strony głównej</button>
									</div>
								</div>
								
								<!-- managers list-->
								<div style="margin-top: 10px" class="form-group">						
									<div class="col-sm-6 controls">
										<button type="button" onclick="window.location.href='../koordynator/listaKierownikow'; return false;" class="btn btn-secondary">Lista kierowników</button>
									</div>
								</div>
								
							</form:form>
						</div>
					</div>
				</div>
			</div>	
		</c:if>
	
	<!-- Adding new user -->
	<c:if test="${userId == null}">
		<div>
			<div id="loginbox" style="margin-top: 50px;" class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="panel-title">Rejestracja kierownika</div>
					</div>
					<div style="padding-top: 30px" class="panel-body">
						<form:form action="${pageContext.request.contextPath}/koordynator/weryfikujRejestracje" modelAttribute="appUser" method="POST" class="form-horizontal">
							<!-- Insert here messages or alerts if there are validated fields in your code -->
							<div class="form-group">
								<div class="col-xs-15">
									<div>
										<c:if test="${registrationError!= null}">
											<div class="alert alert-danger col-xs-offset-1 col-xs-10">
												${registrationError}
											</div>
										</c:if>									
									</div>
								</div>
							</div>
							<!-- username -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<form:errors path="username" cssClass="error"/>
								<form:input path="username" placeholder="nazwa użytkownika" class="form-control"/>
							</div>
							<!-- password -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<form:errors path="password" cssClass="error"/>
								<form:password path="password" placeholder="wpisz hasło" class="form-control"/>
							</div>
							<!-- confirm password -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<form:errors path="matchingPassword" cssClass="error"/>
								<form:password path="matchingPassword" placeholder="powtórz hasło" class="form-control"/>
							</div>
							<!-- first name -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<form:errors path="firstName" cssClass="error"/>
								<form:input path="firstName" placeholder="imię" class="form-control"/>
							</div>
							<!-- last name -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<form:errors path="lastName" cssClass="error"/>
								<form:input path="lastName" placeholder="nazwisko" class="form-control"/>
							</div>
								
							<!-- Register button -->
							<div style="margin-top: 10px" class="form-group">						
								<div class="col-sm-6 controls">
									<button type="submit" class="btn btn-primary">Zarejestruj użytkownika</button>
								</div>
							</div>
							
							<!-- return to home page-->
							<div style="margin-top: 10px" class="form-group">						
								<div class="col-sm-6 controls">
									<button type="button" onclick="window.location.href='../rejestr/wypelnijRejestr'; return false;" class="btn btn-secondary">Wróć do strony głównej</button>
								</div>
							</div>
							
							<!-- managers list-->
							<div style="margin-top: 10px" class="form-group">						
								<div class="col-sm-6 controls">
									<button type="button" onclick="window.location.href='../koordynator/listaKierownikow'; return false;" class="btn btn-secondary">Lista kierowników</button>
								</div>
							</div>
						</form:form>					
					</div>
				</div>	
			</div>		
		</div>		
	</c:if>
	
</body>
</html>