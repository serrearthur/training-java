<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<div class="label label-default pull-right">id: ${computer.id}</div>
				<h1>Edit Computer</h1>
				<form action="" method="POST">
					<input type="hidden" value="${computer.id}" id="id" name="id" />
					<fieldset>
						<div class="form-group ${empty errors['name'] ? '' : 'has-error has-feedback'}">
							<label for="name">Computer name</label>
							<span style="font-style: italic; color:red;">${errors['name']}</span>
							<input type="text" class="form-control" id="name" name="name"
								placeholder="Computer name" value="${computer.name}" required>
						</div>
						<div class="form-group ${empty errors['introduced'] ? '' : 'has-error has-feedback'}">
							<label for="introduced">Introduced date</label>
							<span style="font-style: italic; color:red;">${errors['introduced']}</span>
							<input type="date" class="form-control" id="introduced" name="introduced"
								placeholder="Introduced date" value="${computer.introduced}"
								pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD.">
						</div>
						<div class="form-group ${empty errors['discontinued'] ? '' : 'has-error has-feedback'}">
							<label for="discontinued">Discontinued date</label>
							<span style="font-style: italic; color:red;">${errors['discontinued']}</span>
							<input type="date" class="form-control" id="discontinued" name="discontinued"
								placeholder="Discontinued date" value="${computer.discontinued}"
								pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD.">
						</div>
						<div class="form-group ${empty errors['companyId'] ? '' : 'has-error has-feedback'}">
							<label for="companyId">Company</label>
							<span style="font-style: italic; color:red;">${errors['companyId']}</span>
							<select	class="form-control" id="companyId" name="companyId">
								<option value="0">--</option>
								<c:forEach items="${companies}" var="c">
									<option value="${c.id}" ${c.id eq computer.companyId? "selected":"" }>${c.name}</option>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Edit" class="btn btn-primary"> or 
						<a href="<c:url value="/home"/>" class="btn btn-default">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>