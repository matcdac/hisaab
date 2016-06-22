<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
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
			var check=0;
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
				<%
					String sessionName = (String) session.getAttribute("history");
					int totalItemCount = (Integer) session.getAttribute("itemsCount");
					List<String> itemNames = (ArrayList<String>) session.getAttribute("items");
					int totalParticipantCount = (Integer) session.getAttribute("participantsCount");
					List<String> participantNames = (ArrayList<String>) session.getAttribute("participants");
					double[][][] hisaabFinal = (double[][][]) session.getAttribute("hisaabFinal");
					Map<String, List<String>> finalHisaabText = (HashMap<String, List<String>>) session.getAttribute("finalHisaabText");
				%>
				<div class="showHide" onclick="showItemWise()">Show Item Wise Hisaab</div>
				<div></div>
				<div></div>
				<div class="showHide" onclick="showFinal()">Show Final Hisaab</div>
				<div id="itemWise" style="display:none">
					<%
						for(String item: itemNames) {
					%>
							<%= "<div class=\"initial\">" %>
							<%= "<div><h2><u>"+item+"</u></h2></div>" %>
						<%
							List<String> initialData = finalHisaabText.get(item);
							for(String str: initialData) {
						%>
								<%= "<div>"+str+"</div>" %>
						<%
							}
						%>
							
							<%= "</div>" %>
					<%	
						}
					%>

				</div>
				<div id="finalOnly" style="display:none">
					<div class="final">
						<div><h1><u>Final Hisaab</u></h1></div>
						<%
							List<String> finalData = finalHisaabText.get("final");
							for(String str: finalData) {
						%>
						<%= "<div>"+str+"</div>" %>
						<% } %>
					</div>
				</div>
			</div>
		</h3>
	</body>
</html>
