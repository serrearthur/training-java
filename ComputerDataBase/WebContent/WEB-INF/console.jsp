<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>CLI Interface</title>
</head>
<body>
	<div>
	    Available command lines :<br/>
	    <blockquote>
	    	-`<i>list cpt</i>' : List computers<br/>
		    -`<i>list cpn</i>' : List companies<br/>
		    -`<i>show [-i ID | -n NAME]</i>' : Show computer details (the detailed information of only one computer)<br/>
		    -`<i>create NAME</i>' : Create a computer<br/>
		    -`<i>update ID NAME [DATE_INTRODUCED DATE_DISCONTINUED COMPANY_ID]</i>' : Update a computer<br/>
		    -`<i>delete [-i ID | -n NAME]</i>' : Delete a computer<br/>
	    </blockquote>
	</div>

	<form action="" method="post">
		<input name="command" placeholder="Entrez vos commandes ici" style="width:100%;"/>
		<button type="submit">Send</button>
	</form>

	<!-- request was parsed correctly ? -->
	<c:if test="${not empty parse_status }">
		${parse_status?
			"<p style=\"color:green\">Request executed with success</p>" :
			"<p style=\"color:red\">Request failed</p>"
		}		
	</c:if>

	<c:if test="${not empty computers }">
		<table border="1">
			<thead>
		   		<tr>                                           
					<th>ID</th>
					<th>Name</th>
					<th>Date Introduced</th>
					<th>Date Discontinued</th>
					<th>ID Company</th>
	           </tr>
		   	</thead>
		   	<tbody>
				<c:forEach items="${computers}" var="cpt">
					<tr>
						<td>${cpt.id }</td>
						<td>${cpt.name }</td>
 						<td>${cpt.introduced.toString() }</td>
						<td>${cpt.discontinued.toString() }</td>
						<td>${cpt.companyId }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	
	<c:if test="${not empty companies }">
		<table border="1">
			<thead>
		   		<tr>                                           
					<th>ID</th>
					<th>Name</th>
	           </tr>
		   	</thead>
		   	<tbody>
				<c:forEach items="${companies}" var="cpn">
					<tr>
						<td>${cpn.id }</td>
						<td>${cpn.name }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>