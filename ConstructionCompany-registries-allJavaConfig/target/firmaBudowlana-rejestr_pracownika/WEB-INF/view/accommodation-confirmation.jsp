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

<title>Potwierdzenie - nocleg</title>
</head>
<body>
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Potwierdzenie dodania - nocleg</h1>
				</div>
			</div>
		</div>
	</section>
	
	<!-- logout button -->
	<section>
		<div class="container">
			<div class="row" style="padding-top: 20px">
				<div class="col-md-11" align="right">
       				<form:form action="${pageContext.request.contextPath}/logout" method="POST">
       					<input type="submit" value="Wyloguj" class="btn btn-danger btn-md"/>		
       				</form:form>
				</div>
			</div>
		</div>
	</section>
	
	<div class="container">
		<div class="row" style="padding-top: 20px">
			<h4>
				Obiekt <b>${accommodation.accommodationName}</b> został dodany do listy noclegów
			</h4>
		</div>
		<div class="row" style="padding-top: 10px">
			<div class="col-md-4">
				<button type="button" onclick="window.location.href='../rejestr/dodajNocleg'; return false;" class="btn-secondary btn-md btn-block">Dodaj kolejny obiekt</button>
			</div>	
		</div>
		<div class="row" style="padding-top: 10px">
			<div class="col-md-4">
				<button type="button" onclick="window.location.href='../rejestr/wypelnijRejestr'; return false;" class="btn-secondary btn-md btn-block">Wróć do strony głównej</button>
			</div>	
		</div>
	</div>
</body>
</html>