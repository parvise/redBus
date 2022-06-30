<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Welcome To Bank Application</h1>
	<h2 style="color: red;">${message}</h2>
	<!-- /soldProduct -->
	<a href="<c:url value='/bank/home' />">Accounts</a> ||
	<a href="<c:url value='/bank/sendMoney' />">Send Money</a>

	<form:form action="/app-logout" method="Post">
		<table>
			<tr>
				<td><h1>Welcome To ${user}</h1></td>
				<td><input type="submit" value="Logout" name="logout" /></td>
			</tr>

		</table>
	</form:form>
	<c:choose>
		<c:when test="${!empty accountList}">
			<table class="tg" border=1>
				<tr>
					<th width="120">Id</th>
					<th width="120">FullName</th>
					<th width="120">Balance</th>
					<th width="120">Version</th>
				</tr>
				<c:forEach items="${accountList}" var="test">
					<tr>
						<td><a
							href="<c:url value='/bank/sendMoney/${test.id}' />"
							style="align: center">${test.id}</a></td>
						<td>${test.fullName}</td>
						<td>${test.balance}</td>
						<td>${test.version}</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<form:form action="/busBookingApp/bank/sendMoney" method="Post"
				modelAttribute="bankAccount">
				<table>
					<tr>
						<td><form:label path="fromAccId">
								<spring:message text="From Account ID" />
							</form:label></td>
						<td><form:input path="fromAccId" /></td>
					</tr>
					<tr>
						<td><form:label path="balance">
								<spring:message text="Balance" />
							</form:label></td>
						<td><form:input path="balance" /></td>
					</tr>
					<tr>
						<td><form:label path="toAccId">
								<spring:message text="To Account Id" />
							</form:label></td>
						<td><form:input path="toAccId" /></td>
					</tr>
					<tr>
						<td><form:label path="amount">
								<spring:message text="Amount" />
							</form:label></td>
						<td><form:input path="amount" /></td>
					</tr>
					<form:hidden path="versionNo" />
					<form:hidden path="toVersionNo" />
					<tr>
						<td><input type="submit" value="SendMoney" name="mail" /></td>
					</tr>
				</table>
			</form:form>
		</c:otherwise>
	</c:choose>

</body>
</html>