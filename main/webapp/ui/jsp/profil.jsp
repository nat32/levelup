
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta name="robots" content="noindex, nofollow"/>
    <title>Votre profil</title>
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
        .error {
            color: #ff0000;
        }
        .success {
            color : rgba(71,132,35,0.99)
        }
        body.image {
            background: url('/levelup/images/profil.jpeg') no-repeat !important;
        }
        p{
            font-size: 20px;
        }
        .btn {
            background: white;
        }
    </style>

</head>
<body class="image">
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-left">
            <li class="active"><a href="/levelup">Accueil</a></li>
            <li><a href="/levelup/showProfil/${id}">Mon Profil</a></li>
            <li><a href="/levelup/showDailies/${id}">Tâches quotidiennes</a></li>
            <li><a href="/levelup/showTodos/${id}">Tâches à faire</a></li>
            <li><a href="/levelup/doneTodos/${id}">Tâches accomplies</a></li>
            <li><a href="/levelup/showPrizes/${id}">Récompenses à gagner</a></li>
            <li><a href="/levelup/wonPrizes/${id}">Récompenses gagnées</a></li>

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

<table>
    <h3 align="center">Bienvenue ${name} !!! </h3>
    <tr>
        <td></td>
    </tr>
    <tr>
        <td >
            <c:choose>
                <c:when test="${param.message==1}">
                <div class="alert alert-warning" role="alert">
                    <p class="error">  Vous avez perdu un niveau ! Mais ne vous découragez pas! Au boulot !! :)
                    </p>
                </div>
                </c:when>
                <c:when test="${param.message==2}">
                <div class="alert alert-warning" role="alert">
                    <p class="error">  Vous avez perdu des points ! Mais ne vous découragez pas! Au boulot !! :)
                    </p>
                    <br>
                </div>
                </c:when>
            </c:choose>
        </td>
    </tr>

</table>

    <div align="center">
        <p>Votre niveau  : ${level}</p>
        <p>Vos points : ${points}</p>
    </div>

</div>
</body>

