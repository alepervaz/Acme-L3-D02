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
			<acme:print value="${tutorialLength.average}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.session-length.min"/>
		</th>
		<td>
			<acme:print value="${tutorialLength.min}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.session-length.max"/>
		</th>
		<td>
			<acme:print value="${tutorialLength.max}"/>
		</td>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.session-length.deviation"/>
		</th>
		<td>
			<acme:print value="${tutorialLength.deviation}"/>
		</td>
	</tr>
</table>


<h2>
	<acme:message code="assistant.dashboard.form.title.total-number-tutorial-by-month"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
	Total ${totalNumberOfTutorialByMonth.get('FEBRUARY')}
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${totalNumberOfTutorialByMonth.get('JANUARY')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('FEBRUARY')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('MARCH')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('APRIL')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('MAY')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('JUNE')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('JULY')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('AUGUST')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('SEPTEMBER')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('OCTOBER')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('NOVEMBER')}"/>,
						<jstl:out value="${totalNumberOfTutorialByMonth.get('DECEMBER')}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>