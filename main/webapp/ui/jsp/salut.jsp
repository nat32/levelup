

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta name="robots" content="noindex, nofollow"/>
    <title>Level Up</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <style>
        .logout {
            background: none !important;

            color: inherit;

            border: none;

            margin: 15px 10px 0 0;

            font: inherit;

            cursor: pointer;
        }
        body.image {
            background: url('/levelup/images/accueil.jpeg') no-repeat !important;
        }
        .btn {
            background: white;
        }
        h1{
            color:white;
        }
    </style>
</head>
<body class="image">
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-left">
            <li class="active"><a href="/levelup">Accueil</a></li>
            <li><a href="/levelup/showProfil/${user_id}">Mon Profil</a></li>
            <li><a href="/levelup/showDailies/${user_id}">Tâches quotidiennes</a></li>
            <li><a href="/levelup/showTodos/${user_id}">Tâches à faire</a></li>
            <li><a href="/levelup/doneTodos/${user_id}">Tâches accomplies</a></li>
            <li><a href="/levelup/showPrizes/${user_id}">Récompenses à gagner</a></li>
            <li><a href="/levelup/wonPrizes/${user_id}">Récompenses gagnées</a></li>

        </ul>

        <ul class="nav navbar-nav navbar-right">
            <li><c:url var="logoutUrl" value="/logout"/>
                <form  action="${logoutUrl}" method="post">
                    <input class="logout" type="submit" value="Se déconnecter" />
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
<h1 align="center">${message}</h1>

    <a href="${pageContext.request.contextPath}/showProfil/${user_id}" role="button" class="btn btn-secondary btn-lg btn-block">Voir votre profil</a>

</div>
</body>
</html>


