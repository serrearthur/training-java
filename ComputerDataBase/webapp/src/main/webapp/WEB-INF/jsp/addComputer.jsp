<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Add Computer</h1>
				<form:form modelAttribute="computerForm" action="" method="POST">
					<fieldset>
						<div class="form-group ${empty errors['name'] ? '' : 'has-error has-feedback'}">
							<label for="name">Computer name</label>
							<%-- <span style="font-style: italic; color:red;">${errors['name']}</span> --%>
							<form:errors path="name" style="font-style: italic; color:red;"/> 
							<form:input path="name" type="text" class="form-control" id="name" name="name" placeholder="Computer name" required="true"/>
						</div>
						<div class="form-group ${empty errors['introduced'] ? '' : 'has-error has-feedback'}">
							<label for="introduced">Introduced date</label>
							<%-- <span style="font-style: italic; color:red;">${errors['introduced']}</span> --%>
							<form:errors path="introduced" style="font-style: italic; color:red;"/> 
							<form:input path="introduced" type="date" class="form-control" id="introduced" name="introduced"
							placeholder="Introduced date" pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD"/>
						</div>
						<div class="form-group ${empty errors['discontinued'] ? '' : 'has-error has-feedback'}">
							<label for="discontinued">Discontinued date</label>
							<%-- <span style="font-style: italic; color:red;">${errors['discontinued']}</span> --%>
							<form:errors path="discontinued" style="font-style: italic; color:red;"/> 
							<form:input path="discontinued" type="date" class="form-control" id="discontinued" name="discontinued"
							placeholder="Discontinued date" pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD"/>
						</div>
						<div class="form-group ${empty errors['companyId'] ? '' : 'has-error has-feedback'}">
							<label for="companyId">Company</label>
							<%-- <span style="font-style: italic; color:red;">${errors['companyId']}</span> --%>
							<form:errors path="companyId" style="font-style: italic; color:red;"/> 
							<form:select path="companyId" class="form-control" id="companyId" name="companyId">
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