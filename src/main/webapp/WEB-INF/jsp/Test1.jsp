<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form method="post" action="/busBookingApp/processLogin"
		modelAttribute="${test}">
		<pre>
UserName <input type=text name=username placeholder="UserName" />
Password <input type=password name=password placeholder="Password" />
<input type="submit" value="Login" />
</pre>
	</form>

	<form:form action="/busBookingApp/hello" modelAttribute="bean"  commandName="bean"
		enctype="multipart/form-data" method="post">
		<form:label path="username">
			<spring:message text="Username" />
		</form:label>
		<form:input path="username"  size="8" />

		<form:label path="password">
			<spring:message text="Password" />
		</form:label>
		<form:input path="password"  size="8" />
		<input type="submit" value="Login" />
	</form:form>
</body>
</html>