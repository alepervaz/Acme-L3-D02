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

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="assistant.dashboard.form.title.general-indicators"/>
</h2>

<h3>
	<acme:message code="assistant.dashboard.form.title.tutorial-length"/>
</h3>
<table class="table table-sm">
	<tr>
	<th scope="row">
			<acme:message code="assistant.dashboard.form.label.tutorial-length.average"/>
		</th>
		<td>
			<acme:print value="${tutorialLength.count}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.tutorial-length.average"/>
		</th>
		<td>
			<acme:print value="${tutorialLength.average}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.tutorial-length.min"/>
		</th>
		<td>
			<acme:print value="${tutorialLength.min}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.tutorial-length.max"/>
		</th>
		<td>
			<acme:print value="${tutorialLength.max}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.tutorial-length.deviation"/>
		</th>
		<td>
			<acme:print value="${tutorialLength.deviation}"/>
		</td>
	</tr>
</table>

<h3>
	<acme:message code="assistant.dashboard.form.title.session-length"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.session-length.average"/>
		</th>
		<td>
			<acme:print value="${sessionLength.count}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.session-length.average"/>
		</th>
		<td>
			<acme:print value="${sessionLength.average}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.session-length.min"/>
		</th>
		<td>
			<acme:print value="${sessionLength.min}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.session-length.max"/>
		</th>
		<td>
			<acme:print value="${sessionLength.max}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.session-length.deviation"/>
		</th>
		<td>
			<acme:print value="${sessionLength.deviation}"/>
		</td>
	</tr>
</table>
		
		
		<h2>
			<acme:message code="assistant.dashboard.form.label.Tutorial-length"/>
			<acme:print value="${totalNumberOfTutorial}"/>
		</h2>

<acme:return/>