

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <div id="event-detail-container" class="row">
        <div class="media-left">
            <a href="#"><img id="calImg" class="media-object" src=${imgUrl} style="max-height: 128px;" /></a>
        </div>
        <div class="media-body">
            <h2 class="media-heading" id="title">${title}</h2>
            <h4 id="start">${start}</h4>
            <h4 id="location">${location}</h4>
            <p>
                <a id="addToCalLink" href=${addToCalUrl} target="_blank"><img src="http://www.fis.ncsu.edu/cashier/calendar/calendar_plus_en.gif" /></a>
            </p>
        </div>
        <hr>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h2 class="panel-title">Details</h2>
            </div>
            <div class="panel-body" id="description">${description}
            </div>
        </div>

    </div>
</t:layout>

<script type="text/javascript" src="scripts/EventDetails.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        var config = {
            container : $("#event-detail-container"),
            cid: '',
            eid: '',
            calImgSrc: '',
        };

        eventDetails.init(config);
        // eventDetails.displayDemo();
    });
</script>