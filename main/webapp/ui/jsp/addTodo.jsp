<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="robots" content="noindex, nofollow"/>
    <title>Ajouter une "à faire"</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/jquery-3.1.1.js" ></script>

    <script type="text/javascript">
        $(document).ready(
            function () {
                $.getJSON('<spring:url value="/difficulties"/>', {
                    ajax : 'true'
                }, function (data) {
                    var html = '<option value="0">Choisissez une difficulté</option>';
                    var len = data.length;
                    for (var i = 0; i < len; i++) {
                        html += '<option value="' + data[i].id + '">'
                            + data[i].name + '</option>';
                    }
                    html += '</option>';

                    $('#difficulties').html(html);
                })
            }
        )
    </script>

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
            background: url('/levelup//images/todo.jpeg') no-repeat !important;
        }
        p {
            font-size: 20px;
        }
        .btn-block {
            background: white;
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

    <h1 align="center">Ajouter une tâche "à faire"</h1>

    <a href="${pageContext.request.contextPath}/showTodos/${user_id}" role="button" class="btn btn-secondary btn-lg btn-block">Voir la  liste des tâches "à faire"</a>

    <c:choose>
        <c:when test="${param.message==1}">
    <div class="alert alert-warning" role="alert">
           <p class="error"> Nom et  difficulté de la tâche obligatoires</p>
            <br />
    </div>
        </c:when>
        <c:when test="${param.message==2}">
    <div class="alert alert-success" role="alert">
            <p class="success"> La nouvelle tâche a été ajoutée avec succès</p>
            <br />
    </div>
        </c:when>
        <c:otherwise>
            <br />
        </c:otherwise>
    </c:choose>

    <form:form  method="POST" action="/levelup/newTodo" modelAttribute="todo">
        <form:hidden path="user_id" value="${user_id}" />
        <div class="form-group">
            <label for="name">Nom : </label>
            <form:input path="name" class="form-control" type="text" placeholder="nom" id="name"/>
            <form:errors path="name" cssClass="error" />
        </div>
        <div class="form-group">
            <label for="difficulties">Difficulté</label>
            <form:select class="form-control" path="difficulty_id" id="difficulties" >
            </form:select>
        </div>
        <button type="submit" class="btn btn-primary" value="Ajouter">Ajouter</button>
</form:form>

</div>
</body>
</html>
