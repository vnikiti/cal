<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <div id="event-detail-container" class="row">

    </div>
</t:layout>

<script type="text/javascript" src="scripts/EventDetails.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        var config = {
            container : $("#event-detail-container"),
        };

        eventDetails.init(config);

    });
</script>