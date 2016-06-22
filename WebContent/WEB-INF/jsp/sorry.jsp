<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Hisaab Lagao</title>
		<spring:url value="/css/style.css" var="styleCSS" />
		<link href="${styleCSS}" rel="stylesheet" />
	</head>
	<body>
		<h3>
			<div id="error">
				There was some Problem Logging-In. Kindly Login again.
			</div>
			<div id="welcome2">
				Kindly <a href="register.jsp"><i>Register</i></a> or <a href="login.jsp"><i>Login</i></a>
			</div>
		</h3>
	</body>
</html>
