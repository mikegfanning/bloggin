<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template barcodeUrl="/redeem" barcodeMethod="post">
    <jsp:body>
        <t:error errorMessage="${errorMessage}"/>
        <c:if test="${not empty ticket and 'REDEEMED' eq ticket.status}">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h3 class="panel-title">Ticket Redeemed</h3>
                        </div>
                        <div class="panel-body">
                            <c:out value="ID: ${ticket.ticketId} Name: ${ticket.ticketholderName}"/>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-12"><h2 class="text-center">Scan Ticket Barcode to Redeem</h2></div>
        </div>
    </jsp:body>
</t:template>