<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="bulletin.list.label.title" path="title" width="30%"/>
	<acme:list-column code="bulletin.list.label.flags" path="flags" width="30%"/>
	<acme:list-column code="bulletin.list.label.moment" path="moment" width="70%"/>
</acme:list>