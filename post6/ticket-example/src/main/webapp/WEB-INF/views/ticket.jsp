<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template>
    <div class="row">
        <div class="col-md-2"><strong>Ticket ID</strong></div>
        <div class="col-md-10"><c:out value="${ticket.ticketId}"/></div>
    </div>
    <div class="row">
        <div class="col-md-2"><strong>Name</strong></div>
        <div class="col-md-10"><c:out value="${ticket.ticketholderName}"/></div>
    </div>
    <div class="row">
        <div class="col-md-2"><strong>Status</strong></div>
        <div class="col-md-10"><c:out value="${ticket.status}"/></div>
    </div>
    <div class="row">
        <div class="col-md-2"><strong>Created Date</strong></div>
        <div class="col-md-10"><fmt:formatDate value="${ticket.createdDate}"/></div>
    </div>
    <br/>
    <div class="row">
        <div class="col-md-1">
            <c:url var="pdfUrl" value="/ticket/${ticket.ticketId}.pdf"/>
            <a class="btn btn-default" href="${pdfUrl}">PDF</a>
        </div>
        <div class="col-md-11"></div>
    </div>
</t:template>

