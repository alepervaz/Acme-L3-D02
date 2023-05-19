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

<%@page language="java" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/" %>

<acme:form>
    <acme:input-textbox code="company.session-practicum.form.label.title" path="title"/>
    <acme:input-textarea code="company.session-practicum.form.label.abstractSession" path="abstractSession"/>
    <acme:input-textarea code="company.session-practicum.form.label.description" path="description"/>
    <acme:input-moment code="company.session-practicum.form.label.start" path="start"/>
    <acme:input-moment code="company.session-practicum.form.label.end" path="end"/>
    <acme:input-url code="company.session-practicum.form.label.link" path="link"/>
    <jstl:if test="${_command == 'create' && !draftMode}">
        <acme:input-checkbox code="company.session-practicum.form.label.confirmed" path="confirmed"/>
    </jstl:if>
    <jstl:choose>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode}">
            <acme:submit code="company.session-practicum.form.button.update"
                         action="/company/session-practicum/update"/>
            <acme:submit code="company.session-practicum.form.button.delete"
                         action="/company/session-practicum/delete"/>
        </jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="company.session-practicum.form.button.create"
                         action="/company/session-practicum/create?masterId=${masterId}"/>
        </jstl:when>
    </jstl:choose>
</acme:form>
