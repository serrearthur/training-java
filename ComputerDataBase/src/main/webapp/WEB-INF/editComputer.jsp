<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<div class="label label-default pull-right">id: ${computer.id}</div>
				<h1>Edit Computer</h1>

				<form action="" method="POST">
					<input type="hidden" value="${computer.id}" id="id" name="id" />
					<!-- TODO: Change this value with the computer id -->
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label>
							<input type="text" class="form-control" id="computerName" name="computerName"
								placeholder="Computer name" value="${computer.name}">
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label>
							<input type="date" class="form-control" id="introduced" name="introduced"
								placeholder="Introduced date" value="${computer.introduced}">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label>
							<input type="date" class="form-control" id="discontinued" name="discontinued"
								placeholder="Discontinued date" value="${computer.discontinued}">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label>
							<select	class="form-control" id="companyId" name="companyId">
								<option value="0">--</option>
								<c:forEach items="${companies}" var="c">
									<option value="${c.id}" ${c.id eq computer.companyId? "selected":"" }>${c.name}</option>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Edit" class="btn btn-primary">
						or <a href="<c:url value="/home"/>" class="btn btn-default">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>