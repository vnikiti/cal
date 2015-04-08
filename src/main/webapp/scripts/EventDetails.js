
(function(eventDetails, window, document, $){
    eventDetails.initialized = false;

    eventDetails.config = {
        container : $('#event-detail-container'),
        eventDetailUrl : '/eventDetail',
        cid: '',
        eid: '',
        calImgSrc: '',
        location: $("#location"),
        description: $("#description"),
        start: $("#start"),
        calImg: $("#calImg"),
        title: $("#title"),
        addToCalLink: $("#addToCalLink")
    };

    var $location, $calImg, $title, $start, $description;

    eventDetails.init = function(config){

        if(config && typeof(config) == "object"){
            $.extend(eventDetails.config, config);
        }

        eventDetails.initialized = true;

        // Cache some jQuery objects for manipulation
        $location = eventDetails.config.location;
        $calImg = eventDetails.config.calImg;
        $title = eventDetails.config.title;
        $start = eventDetails.config.start;
        $description = eventDetails.config.description;
    };

    var getDetails = function(){
        var request = $.ajax({
                    method: 'GET',
                    url: eventDetails.config.eventsUrl,
                    data: {cid: eventDetails.config.cid,
                            eid: eventDetails.config.eid}
                });
                // Process the request
                request.done(function(msg){
                    if(msg == null){
                        console.log(msg);
                    } else {
                        displayEventDetails(msg, eventDetails.calImgSrc)
                    }
                });

                request.fail(function(jqXHR, textStatus){
                    console.log("Request failed: " + textStatus);
                });
    };

    var displayEventDetails = function(event, imgSrc){
        $location.text(event.location);
        $title.text(event.summary);
        $description.text(event.description);
        $start.text(event.start.dateTime);
        $calImg.attr("src", imgSrc);
    };


    eventDetails.displayDemo = function(){
        var event = {"created":{"value":1424559263000,"dateOnly":false,"tzShift":0},"creator":{"displayName":"John Blondin","email":"blondin@ncsu.edu"},"description":"Alex Bataller, Department of Physics, UCLA\nSono(less)luminescence","end":{"dateTime":{"value":1425051000000,"dateOnly":false,"tzShift":-300}},"etag":"\"2849118527960000\"","htmlLink":"https://www.google.com/calendar/event?eid\u003daHZjcXBrNjlzdnU5NnJyZGJ2YnBsZmpmZmMgbmNzdS5lZHVfdmQ0Z3Y4dGVyNGtscjlzYTZlZm01dm1zcTBAZw","iCalUID":"hvcqpk69svu96rrdbvbplfjffc@google.com","id":"hvcqpk69svu96rrdbvbplfjffc","kind":"calendar#event","location":"RD 106","organizer":{"displayName":"NCSU Physics Events","email":"ncsu.edu_vd4gv8ter4klr9sa6efm5vmsq0@group.calendar.google.com","self":true},"reminders":{"useDefault":true},"sequence":0,"start":{"dateTime":{"value":1425047400000,"dateOnly":false,"tzShift":-300}},"status":"confirmed","summary":"CMB seminar","updated":{"value":1424559263980,"dateOnly":false,"tzShift":0}};
        displayEventDetails(event, "http://www.nodc.noaa.gov/media/images/seminars/calendar_plus_en.gif");
    };

}(window.eventDetails = window.eventDetails || {}, window, document, jQuery));


