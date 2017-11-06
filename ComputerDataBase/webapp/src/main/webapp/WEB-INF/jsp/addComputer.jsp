<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1><spring:message code='computer.add' /></h1>
				<form:form modelAttribute="${fields.getAttComputerForm()}" action=""
					method="POST">
					<fieldset>
						<div class="form-group">
							<label for="${fields.getAttName()}"><spring:message code='computer.name' /></label>
							<spring:message code='computer.name' var="namePlaceholder"/>
							<form:errors path="${fields.getAttName()}"
								style="font-style: italic; color:red;" />
							<form:input path="${fields.getAttName()}" type="text"
								class="form-control" id="${fields.getAttName()}"
								name="${fields.getAttName()}" placeholder="${namePlaceholder }"
								required="true" />
						</div>
						<div class="form-group">
							<label for="${fields.getAttIntroduced()}"><spring:message code='computer.introduced' /></label>
							<spring:message code='computer.introduced' var="introducedPlaceholder"/>
							<form:errors path="${fields.getAttIntroduced()}"
								style="font-style: italic; color:red;" />
							<form:input path="${fields.getAttIntroduced()}" type="date"
								class="form-control" id="${fields.getAttIntroduced()}"
								name="${fields.getAttIntroduced()}"
								placeholder="${introducedPlaceholder}"
								pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD" />
						</div>
						<div class="form-group">
							<label for="${fields.getAttDiscontinued()}"><spring:message code='computer.discontinued' /></label>
							<spring:message code='computer.discontinued' var="discontinuedPlaceholder"/>
							<form:errors path="${fields.getAttDiscontinued()}"
								style="font-style: italic; color:red;" />
							<form:input path="${fields.getAttDiscontinued()}" type="date"
								class="form-control" id="${fields.getAttDiscontinued()}"
								name="${fields.getAttDiscontinued()}"
								placeholder="${discontinuedPlaceholder}"
								pattern="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$" title="YYYY-MM-DD" />
						</div>
						<div class="form-group">
							<label for="${fields.getAttCompanyId()}"><spring:message code='computer.company' /></label>
							<form:errors path="${fields.getAttCompanyId()}"
								style="font-style: italic; color:red;" />
							<form:select path="${fields.getAttCompanyId()}"
								class="form-control" id="${fields.getAttCompanyId()}"
								name="${fields.getAttCompanyId()}">
								<option value="0" selected>--</option>
								<c:forEach items="${companies}" var="c">
									<option value="<c:out value='${c.id}'/>"><c:out
											value="${c.name}" /></option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="<spring:message code='add.button' />" class="btn btn-primary">
						<a href="<c:url value="${fields.getViewHome()}"/>"
							class="btn btn-default"><spring:message code='cancel.button' /></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>