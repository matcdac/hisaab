<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Hisaab Lagao</title>
		<spring:url value="/css/style.css" var="styleCSS" />
		<link href="${styleCSS}" rel="stylesheet" />
		<spring:url value="/js/script.js" var="scriptJS" />
		<script src="${scriptJS}"></script>
		<script>
			var users=2;
		</script>
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
					<button id="moreUserTextBox" onclick="moreUserTextBox();">Add More Users</button>
				</div>
				<form id="storeParticipants" name="storeParticipants" action="participants.jsp" method="POST">
					<div>
						<div class="left">Total Users</div>
						<div class="right">
							<input type="text" id="users" name="users" value="2" readonly>
						</div>
						<div class="clear"></div>
					</div>
					<div>
						<div class="left">Session Name (Date)</div>
						<div class="right">
							<input type="text" id="date" name="date" value="" readonly>
						</div>
						<div class="clear"></div>
					</div>
					<div id="whatToDo">
						Fill the names of all the people involved in the sharing & splitting
					</div>
					<div id="userNameDiv">
						<div class="users">
							<div class="left">User 1</div>
							<div class="right">
								<input type="text" id="user1" name="user1" value="user1">
							</div>
							<div class="clear"></div>
						</div>
						<div class="users">
							<div class="left">User 2</div>
							<div class="right">
								<input type="text" id="user2" name="user2" value="user2">
							</div>
							<div class="clear"></div>
						</div>
					</div>
					<input id="submit" type="submit" value="Next">
				</form>
			</div>
			<script>
				document.getElementById('date').value = Date();
			</script>
		</h3>
	</body>
</html>
