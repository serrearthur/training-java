<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="target" required="true" %>
<%@ attribute name="pagenb" required="false" %>
<%@ attribute name="limit" required="false" %>
<%@ attribute name="search" required="false" %>
<%@ attribute name="col" required="false" %>

<c:if test="${not empty target}">
	<c:set var="output" value="${target}?" />
	<c:choose>
		<c:when test="${not empty pagenb}">
			<c:set var="output" value="${output}pageNb=${pagenb}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].pageNb}">
				<c:set var="output" value="${output}pageNb=${requestScope['page'].pageNb}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty limit}">
			<c:set var="output" value="${output}limit=${limit}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].limit}">
				<c:set var="output" value="${output}limit=${requestScope['page'].limit}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty search}">
			<c:set var="output" value="${output}search=${search}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].search}">
				<c:set var="output" value="${output}search=${requestScope['page'].search}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
		<c:choose>
		<c:when test="${not empty col}">
			<c:set var="output" value="${output}order=${requestScope['page'].invertOrder(col)}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].col}">
				<c:set var="output" value="${output}order=${requestScope['page'].col}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:url value="${output}" />
</c:if>