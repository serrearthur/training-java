<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<div class="label label-default pull-right">id: ${computer.id}</div>
				<h1>Edit Computer</h1>
				<form:form modelAttribute="${fields.getAttComputerForm()}" action="" method="POST">
					<form:hidden path="${fields.getAttComputerId()}" value="${computer.id}" id="${fields.getAttComputerId()}" name="${fields.getAttComputerId()}" />
					<fieldset>
						<div class="form-group ${empty errors['name'] ? '' : 'has-error has-feedback'}">
							<label for="${fields.getAttName()}">Computer name</label>
							<span style="font-style: italic; color:red;">${errors['name']}</span>
							<form:input path="${fields.getAttName()}" type="text" class="form-control" id="${fields.getAttName()}" name="${fields.getAttName()}"
								placeholder="Computer name" value="${computer.name}" required="true"/>
						</div>
						<div class="form-group ${empty errors['introduced'] ? '' : 'has-error has-feedback'}">
							<label for="${fields.getAttIntroduced()}">Introduced date</label>
							<span style="font-style: italic; color:red;">${errors['introduced']}</span>
							<form:input path="${fields.getAttIntroduced()}" type="date" class="form-control" id="${fields.getAttIntroduced()}" name="${fields.getAttIntroduced()}"
								placeholder="Introduced date" value="${computer.introduced}"
								pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD."/>
						</div>
						<div class="form-group ${empty errors['discontinued'] ? '' : 'has-error has-feedback'}">
							<label for="${fields.getAttDiscontinued()}">Discontinued date</label>
							<span style="font-style: italic; color:red;">${errors['discontinued']}</span>
							<form:input path="${fields.getAttDiscontinued()}" type="date" class="form-control" id="${fields.getAttDiscontinued()}" name="${fields.getAttDiscontinued()}"
								placeholder="Discontinued date" value="${computer.discontinued}"
								pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD."/>
						</div>
						<div class="form-group ${empty errors['companyId'] ? '' : 'has-error has-feedback'}">
							<label for="${fields.getAttCompanyId()}">Company</label>
							<span style="font-style: italic; color:red;">${errors['companyId']}</span>
							<form:select path="${fields.getAttCompanyId()}" class="form-control" id="${fields.getAttCompanyId()}" name="${fields.getAttCompanyId()}">
								<option value="0">--</option>
								<c:forEach items="${companies}" var="c">
									<option value="${c.id}" ${c.id eq computer.companyId? "selected" : "" }>
										<c:out value='${c.name}'/>
									</option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Edit" class="btn btn-primary"> or 
						<a href="<c:url value="${fields.getViewHome()}"/>" class="btn btn-default">Cancel</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>