
(function(events, window, document, $){
    events.initialized = false;

    events.config = {
        container : $('#container'),
        eventUrl : '',
        eventDetailUrl: 'eventDetail',
        searchForm: $("#searchForm"),
        imgUrls: {
            "ncsu.edu_hpasl5cmtenq7biv0omve1nvq8@group.calendar.google.com": "http://users.dsic.upv.es/~afernandez/images/logos/csc.png",
            "ncsu.edu_iv41gou4edva6l3sejfg9mjo2k@group.calendar.google.com": "http://yt3.ggpht.com/-zwmOVhYtX6Q/AAAAAAAAAAI/AAAAAAAAAAA/Y4Ot1GQ9VeA/s100-c-k-no/photo.jpg",
            "ncsu.edu_vd4gv8ter4klr9sa6efm5vmsq0@group.calendar.google.com": "http://www.physics.ncsu.edu/images/Physics.gif",
            "ncsu.edu_507c8794r25bnebhjrrh3i5c4s@group.calendar.google.com": "http://moss.csc.ncsu.edu/~mueller/cluster/arc/ncsu_block.gif",
            "default" : "http://moss.csc.ncsu.edu/~mueller/cluster/arc/ncsu_block.gif"
        }
    };

    events.init = function(config){

        if(config && typeof(config) == "object"){
            $.extend(events.config, config);
        }
        initClickHandlers();
        events.initialized = true;
    };

    var initClickHandlers = function(){
        events.config.searchForm.submit(function(e){
            e.preventDefault();
            var cal = $("#searchParam");
            var query = $("#searchQuery");
            console.log("Submitting query: " + query.val());

            events.getEvents(query.val(), cal.val());
        });
    }

    events.getEvents = function(query, cal){

        var request = $.ajax({
            method: 'GET',
            url: events.config.eventUrl,
            dataType: "json",
            data: {q: query, c: cal}
        });
        // Process the request
        request.done(function(msg){
            if(msg == null || msg.length == 0){
                events.config.container.text("No results found")
            } else{
                console.log(msg);
                displayEvents(msg);
            }
        });

        request.fail(function(jqXHR, textStatus){
            console.log("Request failed: " + textStatus);
        });
    };

    var displayEvents = function(eventsList){
        events.config.container.text("");
        for(var i = 0; i < eventsList.length; i++){
            if(eventsList[i].organizer != null && eventsList[i].id != null){
                var html = buildEventContent(eventsList[i]);

                events.config.container.append(html);
            }
        }
    };

    var buildEventContent = function(event){
        var params = {cid: event.organizer.email, eid: event.id};
        var detailUrl = events.config.eventDetailUrl + "?" + $.param(params);
        var strStart = "";
        if(event.start != null && event.start.dateTime != null){
            var start = event.start.dateTime.value == null ? null : new Date(event.start.dateTime.value);
            var dateOptions = {weekday: "long", year: "numeric", month: "long", day: "numeric", hour: "2-digit", minute: "2-digit"};
            strStart = start.toLocaleString("en-US", dateOptions)
        }else if(event.start != null && event.start.date != null){
            var start = event.start.date.value == null ? null : new Date(event.start.date.value);
            var dateOptions = {weekday: "long", year: "numeric", month: "long", day: "numeric"};
            strStart = start.toLocaleString("en-US", dateOptions)
        } else{
            strStart = "N/A";
        }
        var imgUrl = events.config.imgUrls["default"];
        if(events.config.imgUrls[event.organizer.email] != null){
            imgUrl = events.config.imgUrls[event.organizer.email]
        }
        var html = '<div class="col-xs-12 col-sm-6 text-truncate">' +
            '<div class="media-left">' +
                '<a href="#"><img src="' + imgUrl + '" style="width: 64px; height: 64px" /></a>' +
            '</div>' +
            '<div class="media-body">' +
                '<h2 class="media-heading">' + event.summary + '</h2>' +
                '<h4>' + strStart + '</h4>' +
                '<h5>' + (event.location == null? "N/A" : event.location) + '</h5>' +
                '<p>' + (event.description == null? "N/A" : event.description) + '</p>' +
                '<p><a href="'+ detailUrl + '" class="btn btn-default" role="button">Details</a></p>' +
            '</div>' +
            '</div>';

        return html;
    };

    events.displayDemo = function(){
        console.log("Showing demo events...");

        var sampleEvents = [];

        sampleEvents[0] = {
            imgUrl : "http://yt3.ggpht.com/-zwmOVhYtX6Q/AAAAAAAAAAI/AAAAAAAAAAA/Y4Ot1GQ9VeA/s100-c-k-no/photo.jpg",
            title : "Title",
            location : "Location",
            dateTime : "Date Time",
            description : "Description"
        };
        sampleEvents[1] = {
            imgUrl : "http://www.csc.ncsu.edu/news/images/NCSUbrick_CSC.jpg",
            title : "Title",
            location : "Location",
            dateTime : "Date Time",
            description : "Description"
        };

        sampleEvents[2] = {
            imgUrl : "http://moss.csc.ncsu.edu/~mueller/cluster/arc/ncsu_block.gif",
            title : "Title",
            location : "Location",
            dateTime : "Date Time",
            description : "Description"
        };


        displayEvents(sampleEvents);
    };
}(window.events = window.events || {}, window, document, jQuery));


