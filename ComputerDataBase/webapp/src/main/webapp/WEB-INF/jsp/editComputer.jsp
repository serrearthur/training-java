<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<form:form modelAttribute="${fields.getAttComputerForm()}" action=""
					method="POST">
					<form:label path="${fields.getAttComputerId()}"
						value="${computer.id}" id="${fields.getAttComputerId()}"
						class="label label-default pull-right">id: ${computer.id}</form:label>
					<h1><spring:message code='computer.edit' /></h1>
					<fieldset>
						<div class="form-group">
							<label for="${fields.getAttName()}"><spring:message code='computer.name' /></label>
							<spring:message code='computer.name' var="namePlaceholder"/>
							<form:input path="${fields.getAttName()}" type="text"
								class="form-control" id="${fields.getAttName()}"
								name="${fields.getAttName()}" placeholder="${namePlaceholder }"
								value="${computer.name}" required="true" />
						</div>
						<div class="form-group">
							<label for="${fields.getAttIntroduced()}"><spring:message code='computer.introduced' /></label>
							<spring:message code='computer.introduced' var="introducedPlaceholder"/>
							<form:input path="${fields.getAttIntroduced()}" type="date"
								class="form-control" id="${fields.getAttIntroduced()}"
								name="${fields.getAttIntroduced()}"
								placeholder="${introducedPlaceholder}" value="${computer.introduced}"
								pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD." />
						</div>
						<div class="form-group">
							<label for="${fields.getAttDiscontinued()}"><spring:message code='computer.discontinued' /></label>
							<spring:message code='computer.discontinued' var="discontinuedPlaceholder"/>
							<form:input path="${fields.getAttDiscontinued()}" type="date"
								class="form-control" id="${fields.getAttDiscontinued()}"
								name="${fields.getAttDiscontinued()}"
								placeholder="${discontinuedPlaceholder}" value="${computer.discontinued}"
								pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD." />
						</div>
						<div class="form-group">
							<label for="${fields.getAttCompanyId()}"><spring:message code='computer.company' /></label>
							<form:select path="${fields.getAttCompanyId()}"
								class="form-control" id="${fields.getAttCompanyId()}"
								name="${fields.getAttCompanyId()}">
								<option value="0">--</option>
								<c:forEach items="${companies}" var="c">
									<option value="${c.id}"
										${c.id eq computer.companyId? "selected" : "" }>
										<c:out value='${c.name}' />
									</option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="<spring:message code='edit.button' />" class="btn btn-primary">
						<a href="<c:url value="${fields.getViewHome()}"/>"
							class="btn btn-default"><spring:message code='cancel.button' /></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>