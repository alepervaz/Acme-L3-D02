<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${_command=='create'}">
		<acme:input-moment code="administrator.bulletin.form.label.moment" path="moment"/>	
	</jstl:if>	
	<acme:input-textbox code="administrator.bulletin.form.label.title" path="title"/>
	<acme:input-textarea code="administrator.bulletin.form.label.message" path="message"/>
	<acme:input-checkbox code="administrator.bulletin.form.label.flags" path="flags"/>
	<acme:input-url code="administrator.bulletin.form.label.link" path="link"/>
	
	<jstl:choose>
        <jstl:when test="${_command == 'create'}">
        <acme:input-checkbox code="administrator.bulletin.form.label.confirmation" path="confirmation"/>
			<acme:submit code="administrator.bulletin.form.button.create" action="/administrator/bulletin/create"/>
        </jstl:when>
    </jstl:choose>
</acme:form>