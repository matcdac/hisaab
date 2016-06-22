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
			<div id="welcome">
				Welcome to our Website
			</div>
			<div id="welcome2">
				Kindly <a href="register.jsp"><i>Register</i></a> or <a href="login.jsp"><i>Login</i></a>
			</div>
			<div id="main">
				<p>Kindly fill out the below details</p>
				<form id="logination" name="logination" action="enter.jsp" method="POST">
					<div>
						<div class="left">Email ID</div>
						<div class="right">
							<input type="text" name="email">
						</div>
						<div class="clear"></div>
					</div>
					<div>
						<div class="left">Password</div>
						<div class="right">
							<input type="password" name="password">
						</div>
						<div class="clear"></div>
					</div>
					<input id="submit" type="submit" value="Authenticate">
				</form>
			</div>
			<div id="about">
				Hope this Automation can be of use to you,<br>since it is meant to reduce complex calculations,<br>and not fry your brians.......
			</div>
		</h3>
	</body>
</html>
