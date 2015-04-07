
(function(events, window, document, $){
    events.initialized = false;

    events.config = {
        container : $('#container'),
        eventUrl : '',
        eventDetailUrl: 'eventDetail',
        searchForm: $("#searchForm")
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
            var input = $(this).find("input").first();
            var query = input.val();
            console.log("Submitting query: " + query);
            events.getEvents(query);
        });
    }

    events.getEvents = function(query){

        var request = $.ajax({
            method: 'GET',
            url: events.config.eventUrl,
            dataType: "json",
            data: {q: query}
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
            var html = buildEventContent(eventsList[i]);
            events.config.container.append(html);
        }
    };

    var buildEventContent = function(event){
        var params = {cid: event.organizer.email, eid: event.id};
        var detailUrl = events.config.eventDetailUrl + "?" + $.param(params);
        var html = '<div class="col-xs-12 col-sm-6 col-md-4 text-truncate">' +
            '<div class="media-left">' +
                '<a href="#"><img src="' + '" style="width: 64px; height: 64px" /></a>' +
            '</div>' +
            '<div class="media-body">' +
                '<h2 class="media-heading">' + event.summary + '</h2>' +
                '<h4>' + event.start.dateTime + '</h4>' +
                '<h5>' + event.location + '</h5>' +
                '<p>' + event.description + '</p>' +
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


