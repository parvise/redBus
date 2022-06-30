<%@page import="com.demo.busBookingApp.entity.Ticket"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/greenBus.css" rel="stylesheet">
<style type="text/css">
.success {
	color: green;
}

.fail {
	color: red;
}
</style>
</head>
<body>
	<%
		List<Ticket> selectedSeatList = (List<Ticket>) request.getAttribute("selectedSeatList");
		//out.println("selectedSeatList" + selectedSeatList);
		//request.setAttribute("confirmedList", selectedSeatList);
	%>

	<h1 class="${message.className}">${message.messageName}</h1>
	<c:choose>
		<c:when test="${!empty passengerBean}">
			<form method="get" action="/busBookingApp/home">
				<table class="tg" style="border: 1px solid green;">
					<tr style="background-color: gray; color: white; font-size: 20px;">
						<th width="120">S.No.</th>
						<th width="120">Ticket No.</th>
						<th width="120">Passenger Name</th>
						<th width="120">Gender</th>
						<th width="120">Age</th>
						<th width="120">Mobile No.</th>
						<c:forEach items="${passengerBean}" var="bean" varStatus="loop">
							<tr style="text-align: center; font-size: 16px;">
								<td>${(bean.sNo)}</td>
								<td>${bean.ticketId}</td>
								<td>${bean.name}</td>
								<td>${bean.gender}</td>
								<td>${bean.age}</td>
								<td>${bean.mobileNo}</td>
							</tr>
						</c:forEach>
				</table>
				<input type="submit" value="GoToHome" />
			</form>
		</c:when>
	</c:choose>


</body>
</html>