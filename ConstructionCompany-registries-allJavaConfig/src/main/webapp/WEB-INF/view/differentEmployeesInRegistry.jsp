<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Wystąpił błąd</title>
</head>
<body>
	<h3>ups ... wystąpił błąd. Obecna lista pracowników projektu różni się od listy pracowników z rejestru dla żądanej daty historycznej</h3>
	<p>
		- wypełnij listę rejestrów z poziomu strony głównej
	</p>
	<p>
		- lub wybierz inną datę historyczną
	</p>
	<p>
		<a href="${pageContext.request.contextPath}/rejestr/wypelnijRejestr">Wróc do strony głównej</a>
	</p>
</body>
</html>