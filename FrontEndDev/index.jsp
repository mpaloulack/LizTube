<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html data-ng-app="liztube">
<head>
    <title data-ng-bind="title"></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="description" content ="">
    <meta name="keywords" content="">
    <meta name="Author" content="LizTube" >
    <meta name="Robots" content="all" >
    <meta name="Rating" content="general" >
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="SHORTCUT ICON" href="#" />

    <!-- inject:css -->
    <!-- endinject -->
    <link rel="stylesheet" href="../app/dist/css/all@MINIFY@css">
    <base href="/"/>
</head>
<body>
    <div data-ng-view></div>
    <!-- inject:js -->
    <!-- endinject -->
    <script>
        window.user = ${userConnected};
    </script>
    <script type="text/javascript" src="../app/dist/js/all@MINIFY@js"></script>
    <script type="text/javascript" src="../app/dist/partials/partials@MINIFY@js"></script>
</body>
</html>