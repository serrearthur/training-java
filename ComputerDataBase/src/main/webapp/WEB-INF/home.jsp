<section id="main">
	<div class="container">
		<h1 id="homeTitle">${page.getTotalCount()} Computers found</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">

					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="Search name" /> <input
						type="submit" id="searchsubmit" value="Filter by name"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="addComputer.html">Add
					Computer</a> <a class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();">Edit</a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="#" method="POST">
		<input type="hidden" name="selection" value="bloup">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->

					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href="#"
							id="deleteSelected" onclick="$.fn.deleteSelected();" > <i
								class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
					<th>Computer name</th>
					<th>Introduced date</th>
					<!-- Table header for Discontinued Date -->
					<th>Discontinued date</th>
					<!-- Table header for Company -->
					<th>Company</th>

				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<tbody id="results">
				<c:forEach items="${page.currentPage}" var="c">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="${c.id}"></td>
						<td><a href='<c:url value="/EditComputer?computerId=${c.id}"/>' onclick="">${c.name}</a></td>
						<td>${c.introduced}</td>
						<td>${c.discontinued}</td>
						<td>${c.company}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</section>

<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<ul class="pagination">
			<li><a href="<c:url value="/home?pageNumber=1"/>"
				aria-label="First"> <span aria-hidden="true">&lt;&lt;</span></a></li>
			<li><a
				href="<c:url value="/home?pageNumber=${page.getPreviousPageNumber()}"/>"
				aria-label="Previous"> <span aria-hidden="true">&lt;</span></a></li>
			<c:forEach var="i" begin="${page.getDisplayMin()}" end="${page.getDisplayMax()}">
				<li class=${i eq page.currentPageNumber?"active":"" }><a
					href="<c:url value="/home?pageNumber=${i}"/>">${i}</a></li>
			</c:forEach>
			<li><a
				href="<c:url value="/home?pageNumber=${page.getNextPageNumber()}"/>"
				aria-label="Next"> <span aria-hidden="true">&gt;</span></a></li>
			<li><a
				href="<c:url value="/home?pageNumber=${page.totalPage}"/>"
				aria-label="Last"> <span aria-hidden="true">&gt;&gt;</span></a></li>
		</ul>
		
		<div class="btn-group btn-group-sm pull-right" role="group">
			<form action="" method="post">
				<button type="submit" name="itemPerPage" value="10"
					class="btn btn-default" ${page.elementPerPage eq 10? "disabled":"" }>10</button>
				<button type="submit" name="itemPerPage" value="50"
					class="btn btn-default" ${page.elementPerPage eq 50? "disabled":"" }>50</button>
				<button type="submit" name="itemPerPage" value="100"
					class="btn btn-default" ${page.elementPerPage eq 100? "disabled":"" }>100</button>
			</form>
		</div>
	</div>
</footer>