<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> 
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
			var items=1;
			var names = [
			<%
				List<String> party = (ArrayList<String>) session.getAttribute("participants");
				for (String name : party) {
			%>
					<%= "\""+name+"\", " %>
			<%
				}
			%>
			];
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
					<button id="moreItemTextBox" onclick="moreItemTextBox()">Add More Items</button>
				</div>
				<form id="storeItems" name="storeItems" action="items.jsp" method="POST">
					<div>
						<div class="left">Total Items</div>
						<div class="right">
							<input type="text" id="items" name="items" value="1" readonly >
						</div>
						<div class="clear"></div>
					</div>
					<div id="whatToDo">
						Fill the names of all the items (with details) involved in the sharing & splitting
					</div>
					<div id="itemDiv">
						<div class="items">
							<div>
								<div class="left">Item 1</div>
								<div class="right">
									<input type="text" id="item1" name="item1" value="item1">
								</div>
								<div class="clear"></div>
							</div>
							<div class="info">
								Kindly Tick the people who PAID for this Item & enter the AMOUNT paid by them
							</div>
							<div>
								<%
									party = (ArrayList<String>) session.getAttribute("participants");
									for (String name : party) {
								%>
										<%= "<div><input type=\"checkbox\" name=\"peoplePaid1\" value=\""+name+"\" onchange=\"disableEnable('"+name+"Paid1')\">"+name %>
										<%= "  <input type=\"text\" id=\""+name+"Paid1\" name=\""+name+"Paid1\" value=\"0\" disabled></div>" %>
								<%
									}
								%>
							</div>
							<div class="info">
								Kindly Tick the people who OWN / UTILIZE this Item
							</div>
							<div>
								<%
									party = (ArrayList<String>) session.getAttribute("participants");
									for (String name : party) {
								%>
										<%= "<div><input type=\"checkbox\" name=\"peopleOwn1\" value=\""+name+"\">"+name+"</div>" %>
								<%
									}
								%>
							</div>
						</div>
					</div>
					<input id="submit" type="submit" value="Calculate">
				</form>
			</div>
		</h3>
	</body>
</html>
