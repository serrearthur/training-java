<%@ taglib tagdir="/WEB-INF/tags/" prefix="t"%>

<section id="main">
	<div class="container">
		<h1 id="homeTitle">${page.totalElements} <spring:message code="computer.count" /></h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">
					<input type="search" id="searchbox" name="${fields.getAttSearch()}"
						class="form-control" placeholder="<spring:message code='search.field' />" />
					<input type="submit" id="searchsubmit" value="<spring:message code='search.button' />"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer"
					href="<c:url value="add_computer"/>"><spring:message code='add.button' /></a>
				<a class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();"><spring:message code='edit.button' /></a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="#" method="POST">
		<input type="hidden" name="${fields.getAttDelete()}">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href="#"
							id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
								class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
					<!-- Table header for Computer Name -->
					<th><a
						style="color: black; text-decoration: none; cursor: pointer;"
						title="Sort this column"
						href="<t:linker target="${fields.getViewHome()}" col="name"/>">
							<spring:message code='computer.name' /> </a></th>
					<!-- Table header for Introduced Date -->
					<th><a
						style="color: black; text-decoration: none; cursor: pointer;"
						title="Sort this column"
						href="<t:linker target="${fields.getViewHome()}" col="introduced"/>">
							<spring:message code='computer.introduced' /> </a></th>
					<!-- Table header for Discontinued Date -->
					<th><a
						style="color: black; text-decoration: none; cursor: pointer;"
						title="Sort this column"
						href="<t:linker target="${fields.getViewHome()}" col="discontinued"/>">
							<spring:message code='computer.discontinued' /></a></th>
					<!-- Table header for Company -->
					<th><a
						style="color: black; text-decoration: none; cursor: pointer;"
						title="Sort this column"
						href="<t:linker target="${fields.getViewHome()}" col="companyName"/>">
							<spring:message code='computer.company' /> </a></th>
				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<tbody id="results">
				<c:forEach items="${page.content}" var="c">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="${c.id}"></td>
						<td><a href="<c:url value="edit_computer?id=${c.id}"/>">
							<c:out value="${c.name}" /></a></td>
						<td><c:out value="${c.introduced}" /></td>
						<td><c:out value="${c.discontinued}" /></td>
						<td><c:out value="${c.companyName}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</section>

<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<t:pagination displayrange="${fields.getDisplayRange()}"
			totalpage="${page.totalPages }" currentpage="${page.number+1}"
			target="${fields.getViewHome()}" />

		<div class="btn-group btn-group-sm pull-right" role="group">
			<a class='btn btn-default ${page.size eq 10? "disabled" : "" }'
				href="<t:linker target="${fields.getViewHome()}" pagenb="1" limit="10"/>">10</a>
			<a class='btn btn-default ${page.size eq 50? "disabled" : "" }'
				href="<t:linker target="${fields.getViewHome()}" pagenb="1" limit="50"/>">50</a>
			<a class='btn btn-default ${page.size eq 100? "disabled" : "" }'
				href="<t:linker target="${fields.getViewHome()}" pagenb="1" limit="100"/>">100</a>
		</div>
	</div>
</footer>
<script>
	var i18n = {
		"view" : "<spring:message code="view.button" />",
		"edit" : "<spring:message code="edit.button" />",
		"confirm" : "<spring:message code="confirm.message" />"
	};
</script>