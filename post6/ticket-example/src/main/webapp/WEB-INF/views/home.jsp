<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template>
    <t:error errorMessage="${errorMessage}"/>
    <div class="row">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Ticket ID</th>
                <th>Name</th>
                <th>Status</th>
                <th>Created Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tickets}" var="t">
                <tr>
                    <c:url value="/ticket/${t.ticketId}" var="ticketUrl"/>
                    <td><a href="${ticketUrl}"><c:out value="${t.ticketId}"/></a></td>
                    <td><c:out value="${t.ticketholderName}"/></td>
                    <td><c:out value="${t.status}"/></td>
                    <td><fmt:formatDate value="${t.createdDate}"/></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <c:url value="/ticket" var="addUrl"/>
                <form action="${addUrl}" method="post">
                    <td></td>
                    <td><input class="form-control" type="text" name="ticketholderName"/></td>
                    <td>
                        <select class="form-control" name="status">
                            <c:forEach var="status" items="${statusValues}">
                                <option><c:out value="${status}"/></option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><button type="submit" class="btn btn-default">Add</button></td>
                </form>
            </tr>
            </tfoot>
        </table>
    </div>
</t:template>