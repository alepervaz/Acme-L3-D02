<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="offer.form.label.instantiation" path="instantiation" readonly="true"/>
	<acme:input-textbox code="offer.form.label.heading" path="heading" readonly="true"/>
	<acme:input-moment code="offer.form.label.startDate" path="startDate" readonly="true"/>
	<acme:input-moment code="offer.form.label.endDate" path="endDate" readonly="true"/>
	<acme:input-textarea code="offer.form.label.summary" path="summary" readonly="true"/>
	<acme:input-url code="offer.form.label.link" path="link" readonly="true"/>
</acme:form>