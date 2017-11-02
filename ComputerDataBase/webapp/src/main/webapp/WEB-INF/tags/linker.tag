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
			<c:set var="output" value="${output}${fields.getAttPageNumber()}=${pagenb}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].number}">
				<c:set var="output" value="${output}${fields.getAttPageNumber()}=${requestScope['page'].number+1}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty limit}">
			<c:set var="output" value="${output}${fields.getAttPageLimit()}=${limit}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['page'].size}">
				<c:set var="output" value="${output}${fields.getAttPageLimit()}=${requestScope['page'].size}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty search}">
			<c:set var="output" value="${output}${fields.getAttSearch()}=${search}&" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty requestScope['search']}">
				<c:set var="output" value="${output}${fields.getAttSearch()}=${requestScope['search']}&" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty col}">
			<c:set var="output" value="${output}${fields.getAttCol()}=${col}&" />
			<c:set var="order" value='${empty requestScope["page"].getSort().getOrderFor(col)? "ASC" :
			(requestScope["page"].getSort().getOrderFor(col).isAscending()? "DESC" : "ASC")}' />
			<c:set var="output" value="${output}${fields.getAttOrder()}=${order}&" />
		</c:when>
		<c:otherwise>
		    <c:set var="requestCol" value="${requestScope['page'].getSort().iterator().hasNext() ?
		    requestScope['page'].getSort().iterator().next().getProperty() : ''}"/>
		    <c:set var="requestOrder" value="${requestScope['page'].getSort().getOrderFor(requestCol)}" />
			<c:if test="${not empty requestCol}">
				<c:set var="output" value="${output}${fields.getAttCol()}=${requestCol}&" />
				<c:if test="${not empty requestOrder}">
					<c:set var="output" value='${output}${fields.getAttOrder()}=${requestOrder.isAscending()? "ASC" : "DESC"}&' />
				</c:if>
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:url value="${output}" />
</c:if>