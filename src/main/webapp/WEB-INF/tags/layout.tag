<%@tag description="Layout" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- jQuery from CDN -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Bootstrap 3 from CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  </head>
  <body>
    <!-- Navbar -->
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <!-- User must have been authenticated -->
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/">MyWolfPackNavigator</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">

          </ul>
          <form id="searchForm" class="navbar-form navbar-left" role="search">
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

          <ul class="nav navbar-nav navbar-right">
            <% if(request.getSession().getAttribute("userinfo") == null) { %>
                <li><a href="/login">Log in</a></li>
            <% } else { %>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"><%= ((com.google.api.services.oauth2.model.Userinfoplus)request.getSession().getAttribute("userinfo")).getName() %><span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="/settings">Settings</a></li>
                        <li class="divider"></li>
                        <li><a href="https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://cal-csc510.rhcloud.com/">Log out</a></li>
                    </ul>
                </li>
            <% } %>

          </ul>
        </div><!-- /.navbar-collapse -->
    </div>
      </div><!-- /.container-fluid -->
    </nav>
    <div id="container" class="container">
      <jsp:doBody/>
    </div>
  </body>
</html>

<script type="text/javascript">
    $(document).ready(function(e){
        $('.search-panel .dropdown-menu').find('a').click(function(e) {
            e.preventDefault();
            var param = $(this).attr("href").replace("#","");
            var concept = $(this).text();
            $('.search-panel span#searchCategory').text(concept);
            $('.input-group #searchParam').val(param);
        });
    });
</script>