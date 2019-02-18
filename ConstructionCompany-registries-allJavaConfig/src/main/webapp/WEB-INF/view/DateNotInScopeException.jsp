<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Wystąpił błąd</title>
</head>
<body>
	<h3>ups ... wystąpił błąd. Podano datę spoza zakresu - możesz podać datę nie wcześniejszą niż '2019-01-01' i nie późniejszą niż data dzisiejsza</h3>
	<p>
		<a href="${pageContext.request.contextPath}/rejestr/wypelnijRejestr">Wróc do strony głównej</a>
	</p>
</body>
</html>