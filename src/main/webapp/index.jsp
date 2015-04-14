<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <div class="row">
        <div class="col-xs-12 col-sm-6">
            <form id="searchForm" action="/event">
                <div class="input-group">
                    <div class="input-group-btn search-panel">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            <span id="searchCategory">Category</span> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#ncsu.edu_hpasl5cmtenq7biv0omve1nvq8@group.calendar.google.com">Computer Science</a></li>
                            <li><a href="#ncsu.edu_iv41gou4edva6l3sejfg9mjo2k@group.calendar.google.com">CCEE</a></li>
                            <li><a href="ncsu.edu_vd4gv8ter4klr9sa6efm5vmsq0@group.calendar.google.com">Physics</a></li>
                            <li><a href="ncsu.edu_507c8794r25bnebhjrrh3i5c4s@group.calendar.google.com">Academic</a></li>
                            <li class="divider"></li>
                            <li><a href="#">Everything</a></li>
                        </ul>
                    </div>
                    <input type="hidden" name="searchParam" value="" id="searchParam" />
                    <input type="text" class="form-control" name="x" placeholder="Search" id="searchQuery"/>
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="submit">Search</button>
                    </span>
                </div>

              </form>
        </div>
    </div>
    <div id="events-container" class="row">

    </div>
</t:layout>

<script type="text/javascript" src="scripts/Events.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $('.search-panel .dropdown-menu').find('a').click(function(e) {
            e.preventDefault();
            var param = $(this).attr("href").replace("#","");
            var concept = $(this).text();
            $('.search-panel span#searchCategory').text(concept);
            $('.input-group #searchParam').val(param);
        });

        var config = {
            container : $("#events-container"),
            eventUrl : "event"
        };

        events.init(config);
        // Temporary demonstration of getting all relevant events for a user
        events.getEvents("", "");

    });
</script>