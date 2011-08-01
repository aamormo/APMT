<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>Form</title>
	</head>
	<body>
		<h1>ClientForm</h1>
		<spring:url value="/web/clients/{number}" var="clientUrl">
			<spring:param name="number" value="${result.id}" />
		</spring:url> 
		<form:form action="${clientUrl}" method="post" modelAttribute="result">
			<div>
				<c:if test="${operation != 'new'}">
					<label for="form_id">ID:</label>
					<form:input id="form_id" path="id"/>
				</c:if>
				<label for="form_code">Code:</label>
				<form:input id="form_code" path="code"/>
				<label for="form_name">Name:</label>
				<form:input id="form_name" path="name"/>
			</div>
			<div>
				<c:choose>
					<c:when test="${operation == 'new'}">
						<input type="submit" name="op_add" value="Add"/>
					</c:when>
					<c:when test="${operation == 'search'}">
						<input type="submit" name="op_find" value="Find"/>
					</c:when>
					<c:otherwise>
						<input type="submit" name="op_update" value="Update"/>
						<input type="submit" name="op_delete" value="Delete"/>
					</c:otherwise>
				</c:choose>
			</div>
		</form:form>
		<spring:url var="newUrl" value="/web/clients/new"/>
		<spring:url var="showUrl" value="/web/clients"/>		
		<spring:url var="searchUrl" value="/web/clients/search"/>		
		<a href="${newUrl}">Nuevo</a>|<a href="${showUrl}">Lista</a>|<a href="${searchUrl}">Buscar</a>
	</body>
</html>