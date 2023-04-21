<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${isAdmin}">
		<acme:input-moment code="offer.form.label.instantiation" path="instantiation" readonly="true"/>
	</jstl:if>
	<acme:input-textbox code="offer.form.label.heading" path="heading" readonly="${!editable}"/>
	<acme:input-moment code="offer.form.label.startDate" path="startDate" readonly="${!editable}"/>
	<acme:input-moment code="offer.form.label.endDate" path="endDate" readonly="${!editable}"/>
	<acme:input-textarea code="offer.form.label.summary" path="summary" readonly="${!editable}"/>
	<acme:input-textbox code="offer.form.label.price" path="price" readonly="${!editable}"/>
	<acme:input-url code="offer.form.label.link" path="link" readonly="${!editable}"/>
	
	<jstl:if test="${isAdmin}">
		<acme:input-checkbox readonly="true" code="offer.form.label.draftMode" path="draftMode"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && editable }">
			<acme:submit code="offer.form.button.update" action="/authenticated/offer/update"/>
			<acme:submit code="offer.form.button.delete" action="/authenticated/offer/delete"/>
			<acme:submit code="offer.form.button.publish" action="/authenticated/offer/publish"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && isAdmin }">
			<acme:submit code="offer.form.button.delete" action="/authenticated/offer/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="offer.form.button.create" action="/authenticated/offer/create"/>
		</jstl:when>		
	</jstl:choose>	
</acme:form>