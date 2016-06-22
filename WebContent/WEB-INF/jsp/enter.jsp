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
			<div>
				<a href="logout.jsp">
					<div id="logout">
						Logout
					</div>
					<div class="clear"></div>
				</a>
			</div>
			<div id="success">
				( : : )
				<br>
				<br>
				<b>Welcome</b>
				<br>
				<a id="profile" href="enter.jsp">&gt;&gt; <i><u>${message}</u></i> &lt;&lt;</a>
				<br>
				<br>
				: ) ( :
			</div>
			<div id="services">
				<div>
					<a href="new.jsp">Calculate New Split/Sharing</a>
				</div>
				<div></div>
				<div></div>
				<div>
					<a href="old.jsp">View Past History</a>
				</div>
			</div>
		</h3>
	</body>
</html>
