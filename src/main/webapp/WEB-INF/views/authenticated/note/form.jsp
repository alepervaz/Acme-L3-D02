<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@ page language="java" %>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://www.the-acme-framework.org/" %>

<acme:form>
	<acme:input-moment code="note.form.label.moment" path="moment" readonly="true"/>	
	<acme:input-textbox code="note.form.label.title" path="title"/>
	<acme:input-textbox code="note.form.label.author" path="author" readonly="true"/>
	<acme:input-textbox code="note.form.label.message" path="message"/>
	<acme:input-email code="note.form.label.email" path="emailAddress"/>
	<acme:input-url code="note.form.label.link" path="link"/>
	
	<jstl:choose>
        <jstl:when test="${_command == 'create'}">
        <acme:input-checkbox code="note.form.label.confirmation" path="confirmation"/>
			<acme:submit code="note.form.button.create" action="/authenticated/note/create"/>
        </jstl:when>
    </jstl:choose>
</acme:form>