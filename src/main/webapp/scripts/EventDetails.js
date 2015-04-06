
(function(eventDetails, window, document, $){
    eventDetails.initialized = false;

    eventDetails.config = {
        container : $('#container'),
        eventUrl : '/'
    };

    eventDetails.init = function(config){

        if(config && typeof(config) == "object"){
            $.extend(eventDetails.config, config);
        }

        eventDetails.initialized = true;
    };


}(window.eventDetails = window.eventDetails || {}, window, document, jQuery));


