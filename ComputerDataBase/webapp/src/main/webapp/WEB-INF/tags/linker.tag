<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="target" required="true" type="java.lang.String"%>
<%@ attribute name="pagenb" required="false" type="java.lang.Integer"%>
<%@ attribute name="limit" required="false" type="java.lang.Integer"%>
<%@ attribute name="search" required="false" type="java.lang.String"%>
<%@ attribute name="col" required="false" type="java.lang.String"%>
<%@ attribute name="order" required="false" type="java.lang.String"%>

<c:if test="${not empty target}">
	<c:set var="output" value="${target}?" />
	<c:choose>
		<c:when test="${not empty pagenb}">
			<c:set var="output" value="${output}${page.getAttPageNumber()}=${pagenb}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].pageNb}">
				<c:set var="output" value="${output}${page.getAttPageNumber()}=${requestScope['page'].pageNb}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty limit}">
			<c:set var="output" value="${output}${page.getAttPageLimit()}=${limit}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].limit}">
				<c:set var="output" value="${output}${page.getAttPageLimit()}=${requestScope['page'].limit}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty search}">
			<c:set var="output" value="${output}${page.getAttSearch()}=${search}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].search}">
				<c:set var="output" value="${output}${page.getAttSearch()}=${requestScope['page'].search}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty col}">
			<c:set var="output" value="${output}${page.getAttCol()}=${col}&" />
			<c:set var="output" value="${output}${page.getAttOrder()}=${requestScope['page'].getNewOrder(col)}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].col}">
				<c:set var="output" value="${output}${page.getAttCol()}=${requestScope['page'].col}&" />
				<c:if test="${not empty requestScope['page'].order}">
					<c:set var="output" value="${output}${page.getAttOrder()}=${requestScope['page'].order}&" />
				</c:if>
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:url value="${output}" />
</c:if>