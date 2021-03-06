<%@ taglib tagdir="/WEB-INF/tags/" prefix="ex"%>

<section id="main">
	<div class="container">
		<h1 id="homeTitle">${page.totalCount} Computers found</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">
					<input type="search" id="searchbox" name="${page.getAttSearch()}" class="form-control" placeholder="Search name" />
					<input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="<c:url value="/AddComputer"/>">Add Computer</a>
				<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
			</div>
		</div>
	</div>
	
	<form id="deleteForm" action="#" method="POST">
		<input type="hidden" name="${page.getAttDelete()}" >
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
 			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<th class="editMode" style="width: 60px; height: 22px;" >
						<input type="checkbox" id="selectall" />
						<span style="vertical-align: top;"> - 
							 <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();" >
							 	<i class="fa fa-trash-o fa-lg"></i>
							</a>
						</span>
					</th>
					<!-- Table header for Computer Name -->
					<th>
						<a style="color:black;text-decoration: none;cursor:pointer;" title="Sort this column"
						href="<ex:linker target="/home" col="cpt.name"/>">
							Computer name
						</a>
					</th>
					<!-- Table header for Introduced Date -->
					<th>
						<a style="color:black;text-decoration: none;cursor:pointer;" title="Sort this column"
							href="<ex:linker target="/home" col="cpt.introduced"/>">
							Introduced date
						</a>
					</th>
					<!-- Table header for Discontinued Date -->
					<th>
						<a style="color:black;text-decoration: none;cursor:pointer;" title="Sort this column"
							href="<ex:linker target="/home" col="cpt.discontinued"/>">
							Discontinued date
						</a>
					</th>
					<!-- Table header for Company -->
					<th>
						<a style="color:black;text-decoration: none;cursor:pointer;" title="Sort this column"
							href="<ex:linker target="/home" col="cpn.name"/>">
							Company
						</a>
					</th>
				</tr>
 			</thead>
			<!-- Browse attribute computers -->
 			<tbody id="results">
				<c:forEach items="${page.data}" var="c">
					<tr>
						<td class="editMode">
							<input type="checkbox" name="cb" class="cb" value="${c.id}"></td>
						<td><a href="<c:url value="EditComputer?id=${c.id}"/>" >${c.name}</a></td>
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
		<ex:pagination displayrange="${page.paginationBorders}" totalpage="${page.totalPage}"
			currentpage="${page.pageNb}" target="/home"/>
		
		<div class="btn-group btn-group-sm pull-right" role="group">
			<a class='btn btn-default ${page.limit eq 10? "disabled":"" }' href="<ex:linker target="/home" pagenb="1" limit="10"/>">10</a>
			<a class='btn btn-default ${page.limit eq 50? "disabled":"" }' href="<ex:linker target="/home" pagenb="1" limit="50"/>">50</a>
			<a class='btn btn-default ${page.limit eq 100? "disabled":"" }' href="<ex:linker target="/home" pagenb="1" limit="100"/>">100</a>
		</div>
	</div>
</footer>