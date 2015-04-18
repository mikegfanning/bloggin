<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="barcodeForm" type="java.lang.Boolean" %>
<%@ attribute name="barcodeUrl" %>
<%@ attribute name="barcodeMethod" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <title>Ticket Example</title>
</head>
<body>

<!-- Static navbar -->
<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <c:url value="/" var="homeUrl"/>
            <a class="navbar-brand" href="${homeUrl}">Ticket Example</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-right">
                <li>
                    <c:url value="/redeem" var="redeemUrl"/>
                    <a class="btn btn-primary navbar-btn" href="${redeemUrl}">Redeem</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
<jsp:doBody/>
</div>

<c:if test="${empty barcodeForm or barcodeForm}">
    <c:url var="barcodeUrl" value="${empty barcodeUrl ? '/barcode' : barcodeUrl}"/>
    <form id="barcode-form" action="${barcodeUrl}" method="${empty barcodeMethod ? 'get' : barcodeMethod}">
        <input id="barcode-data" type="hidden" name="data"/>
    </form>
</c:if>

<script src="https://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<c:if test="${empty barcodeForm or barcodeForm}">
    <c:url var="wedgeJs" value="/resources/wedge.js"/>
    <script src="${wedgeJs}"></script>
    <script type="text/javascript">
        Wedge(document, {
            startSeq: [33, 33, 94],
            endSeq: [94, 33, 33],
            onScanEnd: function(data) {
                var str = String.fromCharCode.apply(this, data);
                document.getElementById('barcode-data').setAttribute('value', str);
                document.getElementById('barcode-form').submit();
            }
        });
    </script>
</c:if>

</body>
</html>