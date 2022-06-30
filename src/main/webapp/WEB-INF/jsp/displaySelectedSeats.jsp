<%@page import="com.demo.busBookingApp.entity.PassengerBookingTickets"%>
<%@page import="com.demo.busBookingApp.entity.Ticket"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html onload="hi">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/greenBus.css" rel="stylesheet" />
<script>
	function seatExipreCheck() {
		var passengerName = document.getElementById("passengerName").value;
		var serviceNo = document.getElementById("serviceNo").value;
		// alert("passengerName " + passengerName);
		url = "http://10.188.31.125:2020/busBookingApp/unblockSeats/"
				+ passengerName + "/" + serviceNo;
		if (window.XMLHttpRequest) {
			request = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			request = new ActiveXObject("Microsoft.XMLHTTP");
		}

		try {
			request.onreadystatechange = check;
			request.open("GET", url, true);
			request.send();
		} catch (e) {
			alert("Unable to connect to server");
		}

	}

	function check() {
		if (request.readyState == 4) {
			var val = request.responseText;
			// alert(val);
			if (val == "Expired") {
				alert("Slected seat time has expired.");
				window.location.href = "http://10.188.31.125:2020/busBookingApp/home";
			}

		}
	}
</script>
</head>
<!-- onload="seatExipreCheck()" -->
<body onload="seatExipreCheck()">
	<%
		List<Ticket> selectedSeatList = (List<Ticket>) request.getAttribute("selectedSeatList");
		List<PassengerBookingTickets> ticketBeanList = (List<PassengerBookingTickets>) request
				.getAttribute("passengerBean");

		//out.println("ticketBeanList" + ticketBeanList);
		request.setAttribute("passengerBean", ticketBeanList);
		//request.setAttribute("confirmedList", selectedSeatList);
	%>
	<h1 class="${message.className}">${message.messageName}</h1>
	<div id="countDownTime"></div>
	<c:choose>
		<c:when test="${!empty passengerBean}">
			<form method="post" action="/busBookingApp/bookTickets/${serviceNo}">
				<table class="tg" style="border: 1px solid green;">
					<tr style="background-color: gray; color: white; font-size: 20px;">
						<th width="120">S.No.</th>
						<th width="120">Service No.</th>
						<th width="120">Ticket No.</th>
						<th width="120">Passenger Name</th>
						<th width="120">Gender</th>
						<th width="120">Age</th>
						<th width="120">Mobile No.</th>
						<th width="120">Booked Date</th>
						<c:forEach items="${passengerBean}" var="bean" varStatus="loop">
							<tr style="text-align: center; font-size: 16px;">
								<td>${(bean.sNo)}</td>
								<td>${(bean.serviceNo)}</td>
								<td>${bean.ticketId}</td>
								<td>${bean.name}</td>
								<td>${bean.gender}</td>
								<td>${bean.age}</td>
								<td>${bean.mobileNo}</td>
								<td>${bean.bookeDateTime}</td>
								<input type="hidden" name="selectedSeatList"
									value="${bean.ticketId}">
								<input type="hidden" name="sNo" value="${bean.sNo}">
								<input type="hidden" name="ticketId[]" value="${bean.ticketId}">
								<input type="hidden" name="name[]" value="${bean.name}">
								<input type="hidden" name="gender[]" value="${bean.gender}">
								<input type="hidden" name="age[]" value="${bean.age}">
								<input type="hidden" name="mobileNo" value="${bean.mobileNo}">
								<input type="hidden" name="passengerName" id="passengerName"
									value="${bean.primaryPassName}">
								<input type="hidden" name="serviceNo" id="serviceNo"
									value="${bean.serviceNo}">
								<input type="hidden" name="emailId" value="${bean.emailId}">
							</tr>
						</c:forEach>
				</table>
				<input type="submit" value="Make Payment" />
			</form>
		</c:when>
	</c:choose>

</body>
</html>