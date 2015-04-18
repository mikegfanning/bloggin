<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="errorMessage" required="true" %>
<c:if test="${not empty errorMessage}">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-danger">
                <div class="panel-heading">
                    <h3 class="panel-title">Error</h3>
                </div>
                <div class="panel-body"><c:out value="${errorMessage}"/></div>
            </div>
        </div>
    </div>
</c:if>