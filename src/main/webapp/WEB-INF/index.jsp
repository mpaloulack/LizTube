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
    <link rel="SHORTCUT ICON" href="${pageContext.request.contextPath}/app/dist/img/favicon.png" />

    <!-- inject:css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/app/dist/libs/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/app/dist/libs/angular-material/angular-material.css">
    <!-- endinject -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/app/dist/css/all.css">
    <base href="/"/>
</head>
<body ng-controller="mainCtrl">
    <header></header>
    <div data-ng-view></div>
    <!-- inject:js -->
    <script src="${pageContext.request.contextPath}/app/dist/libs/angular/angular.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/angular-animate/angular-animate.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/angular-aria/angular-aria.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/angular-route/angular-route.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/ng-file-upload/angular-file-upload-shim.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/ng-file-upload/angular-file-upload.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/lodash/dist/lodash.compat.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/angular-messages/angular-messages.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/angular-mocks/angular-mocks.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/angular-gravatar/build/angular-gravatar.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/angular-material/angular-material.js"></script>
    <script src="${pageContext.request.contextPath}/app/dist/libs/restangular/dist/restangular.js"></script>
    <!-- endinject -->
    <script>
        window.user = ${userConnected};
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/app/dist/js/all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/app/dist/partials/partials.js"></script>
</body>
</html>