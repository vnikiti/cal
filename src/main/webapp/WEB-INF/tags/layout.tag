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


          <ul class="nav navbar-nav navbar-right">
            <% if(request.getSession().getAttribute("userinfo") == null) { %>
                <li><a href="/login">Log in</a></li>
            <% } else { %>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"><%= ((com.google.api.services.oauth2.model.Userinfoplus)request.getSession().getAttribute("userinfo")).getName() %><span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="/settings">Settings</a></li>
                        <li class="divider"></li>
                        <li><a href="https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://cal-csc510.rhcloud.com/logout">Log out</a></li>
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