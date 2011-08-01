<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>List</title>
	</head>
	<body>
		<h1>ClientList</h1>
		<table>
			<thead>
				<tr>
					<td>ID</td>
					<td>CODE</td>
					<td>NAME</td>
				</tr>
			</thead>
			<c:forEach var="client" items="${results}">
				<tr>
					<td>
						<spring:url var="showUrl" value="/web/clients/{number}">
							<spring:param name="number" value="${client.code}" />
						</spring:url>
						<a href="${showUrl}">${client.id}</a>
					</td>
					<td>
						<a href="${showUrl}">${client.code}</a>
					</td>
					<td>
						${client.name}
					</td>
				</tr>
			</c:forEach>
		</table>
		<spring:url var="newUrl" value="/web/clients/new"/>
		<spring:url var="showUrl" value="/web/clients"/>		
		<spring:url var="searchUrl" value="/web/clients/search"/>		
		<a href="${newUrl}">Nuevo</a>|<a href="${showUrl}">Lista</a>|<a href="${searchUrl}">Buscar</a>
	</body>
</html>