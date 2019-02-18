<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!-- Reference Bootstrap files -->
	<link rel="stylesheet"
		 href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<title>Potwierdzenie rejestracji</title>
</head>
<body>
	<section class="pt-5 pb-5 bg-primary inner-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 class="mt-0 mb-3 text-white">Potwierdzenie rejestracji kierownika</h1>
				</div>
			</div>
		</div>
	</section>
	
	<div class="container">
		<div class="row" style="padding-top: 20px">
			<h4>
				Dodanie kierownika zakończone sukcesem!
			</h4>
		</div>
		<div class="row" style="padding-top: 10px">
			<div class="col-md-4">
				<button type="button" onclick="window.location.href='../koordynator/listaKierownikow'; return false;" class="btn-secondary btn-md btn-block">Lista wszystkich kierowników</button>
			</div>	
		</div>
		<div class="row" style="padding-top: 10px">
			<div class="col-md-4">
				<button type="button" onclick="window.location.href='../koordynator/rejestrujUzytkownika'; return false;" class="btn-secondary btn-md btn-block">Dodaj kolejnego kierownika</button>
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