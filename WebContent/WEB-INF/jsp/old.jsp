<%@page import="debryan.model.JsonUtil"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="debryan.model.End" %>
<%@ page import="debryan.model.FinalHisaab" %>
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
				<% String oldExists = (String)session.getAttribute("oldExists"); %>
				<% if( oldExists.equals("true") ) { %>
					<%= "<div></div>" %>
					<%= "<div>Data Exists</div>" %>
					<%= "<div></div>" %>
					<% Map<String, FinalHisaab> map = (Map<String, FinalHisaab>)session.getAttribute("old"); %>
					<% for(String key:map.keySet()) { %>
						<%= "<div></div>" %>
						<%= "<div><u>"+key+"</u></div>" %>
						<%= "<div></div>" %>
						<%= "<div>"+map.get(key)+"</div>" %>
						<%= "<div></div>" %>
					<% } %>
				<% } else { %>
					<%= "Data Does Not Exist" %>
				<% } %>
			</div>
		</h3>
	</body>
</html>
