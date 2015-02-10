<!DOCTYPE html>
<html ng-app="liztube">
<head>
    <title>LizTube - {{title}}</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="../app/dist/css/all.css">
    <base href="/"/>
</head>
<body>
    <div ng-view></div>
    <!-- inject:js -->
    <!-- endinject -->
    <script type="text/javascript" src="../app/dist/js/all.js"></script>
</body>
</html>