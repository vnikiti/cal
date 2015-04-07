<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <div id="events-container" class="row">

    </div>
</t:layout>

<script type="text/javascript" src="scripts/Events.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        var config = {
            container : $("#events-container"),
            eventUrl : "event"
        };

        events.init(config);

    });
</script>