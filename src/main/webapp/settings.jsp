<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
	<div class="row">
		<form action="settings" method="post" class="form-group">
			<p class="small">Select the calendars you are interested in viewing:</p>

            <c:forEach var="cal" items="${calNames}">
                <c:choose>
                    <c:when test="${userCalendars.contains(cal.key)}">
                        <label class="checkbox">
                            <input type="checkbox" name="${cal.key}" value="${cal.key}" checked="checked" /> ${cal.value}
                        </label>
                    </c:when>
                    <c:otherwise>
                        <label class="checkbox">
                            <input type="checkbox" name="${cal.key}" value="${cal.key}" /> ${cal.value}
                        </label>
                    </c:otherwise>
                </c:choose>

            </c:forEach>

			<button class="btn btn-default" type="submit">Save</button>
		</form>
	</div>

</t:layout>

