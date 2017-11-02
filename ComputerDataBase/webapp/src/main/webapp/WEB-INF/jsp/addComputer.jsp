<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Add Computer</h1>
				<form:form modelAttribute="${fields.getAttComputerForm()}" action="" method="POST">
					<fieldset>
						<div class="form-group ${empty errors['name'] ? '' : 'has-error has-feedback'}">
							<label for="${fields.getAttName()}">Computer name</label>
							<%-- <span style="font-style: italic; color:red;">${errors['name']}</span> --%>
							<form:errors path="${fields.getAttName()}" style="font-style: italic; color:red;"/> 
							<form:input path="${fields.getAttName()}" type="text" class="form-control" id="${fields.getAttName()}" name="${fields.getAttName()}" placeholder="Computer name" required="true"/>
						</div>
						<div class="form-group ${empty errors['introduced'] ? '' : 'has-error has-feedback'}">
							<label for="${fields.getAttIntroduced()}">Introduced date</label>
							<%-- <span style="font-style: italic; color:red;">${errors['introduced']}</span> --%>
							<form:errors path="${fields.getAttIntroduced()}" style="font-style: italic; color:red;"/> 
							<form:input path="${fields.getAttIntroduced()}" type="date" class="form-control" id="${fields.getAttIntroduced()}" name="${fields.getAttIntroduced()}"
							placeholder="Introduced date" pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD"/>
						</div>
						<div class="form-group ${empty errors['discontinued'] ? '' : 'has-error has-feedback'}">
							<label for="${fields.getAttDiscontinued()}">Discontinued date</label>
							<%-- <span style="font-style: italic; color:red;">${errors['discontinued']}</span> --%>
							<form:errors path="${fields.getAttDiscontinued()}" style="font-style: italic; color:red;"/> 
							<form:input path="${fields.getAttDiscontinued()}" type="date" class="form-control" id="${fields.getAttDiscontinued()}" name="${fields.getAttDiscontinued()}"
							placeholder="Discontinued date" pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD"/>
						</div>
						<div class="form-group ${empty errors['companyId'] ? '' : 'has-error has-feedback'}">
							<label for="${fields.getAttCompanyId()}">Company</label>
							<%-- <span style="font-style: italic; color:red;">${errors['companyId']}</span> --%>
							<form:errors path="${fields.getAttCompanyId()}" style="font-style: italic; color:red;"/> 
							<form:select path="${fields.getAttCompanyId()}" class="form-control" id="${fields.getAttCompanyId()}" name="${fields.getAttCompanyId()}">
								<option value="0" selected>--</option>
								<c:forEach items="${companies}" var="c">
									<option value="<c:out value='${c.id}'/>" ><c:out value="${c.name}"/></option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Add" class="btn btn-primary"> or 
						<a href="<c:url value="/home"/>" class="btn btn-default">Cancel</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>