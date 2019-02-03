<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="robots" content="noindex, nofollow"/>
    <title>Modifier une quotidienne</title>
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
            background: url('/levelup/images/daily.jpeg') no-repeat !important;
        }
        p {
            font-size: 20px;
        }
        .btn-block {
            background: white;
        }

        .p-l-10 {
            padding-left: 10px;
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

<h1 align="center">Modifier une tâche quotidienne</h1>

    <a href="${pageContext.request.contextPath}/showDailies/${user_id}" role="button" class="btn btn-secondary btn-lg btn-block">Voir la liste de mes tâches quotidiennes</a>

    <c:choose>
        <c:when test="${param.message==1}">
        <div class="alert alert-warning" role="alert">
           <p class="error"> Nom obligatoire</p>
            <br>
        </div>
        </c:when>
        <c:when test="${param.message==2}">
        <div class="alert alert-warning" role="alert">
            <p class="success"> Il y eu une erreur, merci de réessayer</p>
            <br>
        </div>
        </c:when>
        <c:otherwise>
            <br>
        </c:otherwise>
    </c:choose>



    <div class="panel panel-default">
        <div class="panel-head">
            <p class="p-l-10">Votre "quotidienne" : ${current_daily.name}</p>
              <p class="p-l-10">Modifier votre tâche quotidinne : </p>
        </div>
        <div class="panel-body">
            <form:form  method="POST" action="/levelup/modifiedDaily" modelAttribute="modified_daily">
                <form:hidden path="user_id" value="${user_id}" />

                <form:hidden path="id" value="${current_daily.id}" />

                <div class="form-group">
                    <label for="name">Nouveau nom : </label>
                    <form:input path="name" class="form-control" type="text" placeholder="nom" id="name"/>
                    <form:errors path="name" cssClass="error" />
                </div>
                <button type="submit" class="btn btn-primary" value="Ajouter">Modifier</button>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
