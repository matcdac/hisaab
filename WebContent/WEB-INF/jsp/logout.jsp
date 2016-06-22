<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Hisaab Lagao</title>
		<spring:url value="/css/style.css" var="styleCSS" />
		<link href="${styleCSS}" rel="stylesheet" />
	</head>
	<body>
		<%
			session.setAttribute("Hisaab-email",null);
			session.setAttribute("Hisaab-password",null);
			session.setAttribute("Hisaab-name",null);
			session.setAttribute("Hisaab-phone",null);
			session.setAttribute("Hisaab-loggedin",null);
			session.invalidate();
		%>
	</body>
</html>
