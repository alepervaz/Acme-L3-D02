<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="bulletin.form.label.moment" path="moment" readonly="true"/>
	<acme:input-textbox code="bulletin.form.label.title" path="title" readonly="true"/>
	<acme:input-textbox code="bulletin.form.label.message" path="message" readonly="true"/>
	<acme:input-textbox code="bulletin.form.label.flags" path="flags" readonly="true"/>
	<acme:input-url code="bulletin.form.label.link" path="link" readonly="true"/>
</acme:form>