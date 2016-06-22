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
			${message}
			<div id="welcome2">
				Kindly <a href="register.jsp"><i>Register</i></a> or <a href="login.jsp"><i>Login</i></a>
			</div>
			<div id="main">
				<p>Kindly fill out the below details</p>
				<form id="registration" name="registration" action="save.jsp" method="POST">
					<div>
						<div class="left">Name</div>
						<div class="right">
							<input type="text" name="name">
						</div>
						<div class="clear"></div>
					</div>
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
					<div>
						<div class="left">Phone Number</div>
						<div class="right">
							<input type="text" name="phone">
						</div>
						<div class="clear"></div>
					</div>
					<input id="submit" type="submit" value="Authorize">
				</form>
			</div>
			${about}
		</h3>
	</body>
</html>
