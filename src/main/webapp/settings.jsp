<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>

	<div class="row">
		<form action="settings" method="post" class="form-group" id="settingsForm">
			<p class="small">Select the calendars you are interested in viewing:</p>
            <input type="hidden" value="jlschuma" name="userId"/>
            <c:forEach var="cal" items="${calNames}">
                <c:choose>
                    <c:when test="${userCalendars.contains(cal.key)}">
                        <label class="checkbox">
                            <input type="checkbox" name="calendarId" value="${cal.key}" checked="checked" /> ${cal.value}
                        </label>
                    </c:when>
                    <c:otherwise>
                        <label class="checkbox">
                            <input type="checkbox" name="calendarId" value="${cal.key}" /> ${cal.value}
                        </label>
                    </c:otherwise>
                </c:choose>

            </c:forEach>

			<button class="btn btn-default" type="submit">Save</button>
		</form>
		<p id="responseContainer">
		</p>
	</div>

</t:layout>

<script type="text/javascript">
    $(document).ready(function(){
        $("#settingsForm").submit(function(e){
            e.preventDefault();
            var checkboxes = $(this).find('input[type="checkbox"]');
            // At least one checkbox must be checked to save settings...
            if(!checkboxes.is(":checked")){
                $("#responseContainer").text("Please select at least one calendar!");
            } else{

                var data = $(this).serialize();

                var request = $.ajax({
                    url: "settings",
                    method: "POST",
                    data: data,
                    dataType: "json"
                });

                request.done(function(msg){
                    if(msg.success){
                        $("#responseContainer").text("Saved settings!");
                    } else{
                        $("#responseContainer").text("Failed to save settings!");
                        console.log("Error: " + msg.error);
                    }

                });

                request.fail(function(jqXHR, textStatus){
                    $("#responseContainer").text("Failed to save settings!");
                    console.log("Request failed: " + textStatus);
                });
        }

        });
    });
</script>