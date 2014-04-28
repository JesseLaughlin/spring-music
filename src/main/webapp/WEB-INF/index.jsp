<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" class="en" ng-app="SpringMusic">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="Spring Music">
    <meta name="title" content="Spring Music">
    <link rel="shortcut icon" href="assets/favicon.ico">
    <title>Spring Music</title>

    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.1.1/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="assets/css/app.css">
    <link rel="stylesheet" type="text/css" href="assets/css/multi-columns-row.css">
</head>

<body>
    <div class="container">
        <div class="row">
            <div ng-include="'assets/templates/header.html'"></div>
        </div>

        <div id="body" class="row">
            <ng-view></ng-view>
        </div>

        <div class="row">
            <div ng-include="'assets/templates/footer.html'"></div>
        </div>
    </div>

    <script type="text/javascript" src="webjars/jquery/2.1.0/jquery.min.js"></script>

    <script type="text/javascript" src="webjars/angularjs/1.2.16/angular.js"></script>
    <script type="text/javascript" src="webjars/angularjs/1.2.16/angular-resource.js"></script>
    <script type="text/javascript" src="webjars/angularjs/1.2.16/angular-route.js"></script>
    <script type="text/javascript" src="webjars/angular-ui/0.4.0/angular-ui.js"></script>
    <script type="text/javascript" src="webjars/angular-ui-bootstrap/0.10.0/ui-bootstrap.js"></script>
    <script type="text/javascript" src="webjars/angular-ui-bootstrap/0.10.0/ui-bootstrap-tpls.js"></script>

    <script type="text/javascript" src="webjars/bootstrap/3.1.1/js/bootstrap.js"></script>

    <script type="text/javascript" src="assets/js/app.js"></script>
    <script type="text/javascript" src="assets/js/albums.js"></script>
    <script type="text/javascript" src="assets/js/errors.js"></script>
    <script type="text/javascript" src="assets/js/info.js"></script>
    <script type="text/javascript" src="assets/js/status.js"></script>
</body>
</html>
