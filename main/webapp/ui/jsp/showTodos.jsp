<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta name="robots" content="noindex, nofollow"/>
    <title>Tâches "à faire"</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">

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
        table {
            border-collapse: collapse;
            border: none;
        }

        table, th, td {
            padding: 10px;
            text-align: center;
        }

        .btn {
            background: white;
        }

        body.image {
            background: url('/levelup/images/todo.jpeg') no-repeat !important;
        }
        p {
            font-size: 20px;
        }
        h3{
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


<h1 align="center" >Tâches à faire</h1>

    <a href="${pageContext.request.contextPath}/addTodo/${user_id}"  role="button" class="btn btn-secondary btn-lg btn-block"> Ajouter une nouvelle tâche à faire</a>

<c:choose>
    <c:when test="${param.message==1}">
        <div class="alert alert-warning" role="alert">
        <p class="error"> La tâche n'a pas pu être validée, merci de réessayer </p>
        </div>
    </c:when>
    <c:when test="${param.message==2}">
        <div class="alert alert-success" role="alert">
        <p class="success">   La tâche a été supprimée avec succès </p>
        </div>
    </c:when>
    <c:when test="${param.message==3}">
            <div class="alert alert-warning" role="alert">
        <p class="error">   La tâche n'a pas pu être supprimée, merci de réessayer </p>
            </div>
    </c:when>

    <c:when test="${param.message==4}">
                <div class="alert alert-success" role="alert">
        <p class="success">   La tâche a été modifiée avec succèss </p>
                </div>
    </c:when>


    <c:otherwise>
        <br />
    </c:otherwise>
</c:choose>

    <div class="col-xs-12 col-md-12">


    <div class="panel panel-default">
        <div class="panel-head">
            <h3> Voici votre liste de tâches restant "A faire"</h3>

        </div>
        <div class="panel-body">

<table>
    <thead>
    <tr>
        <td>Nom</td>
        <td>Difficulté</td>
        <td>Valider</td>
        <td>Modifier</td>
        <td>Supprimer</td>
    </tr>
    </thead>
    <tbody>
<c:forEach var="todo" items="${todos}">
    <tr>
    <td><c:out value="${todo.name}"/></td>
    <td> <c:out value="${todo.difficulty_name}"/> </td>
    <td> <a href="/levelup/checkTodo/${todo.id}/${user_id}" ><i class="far fa-check-square"></i> </a> </td>
    <td> <a href="/levelup/modifyTodo/${todo.id}/${user_id}" ><i class="fas fa-edit"></i> </a>  </td>
    <td> <a href="/levelup/deleteTodo/${todo.id}/${user_id}/showTodos" ><i class="far fa-trash-alt"></i></a>  </td>
    </tr>
</c:forEach>
    </tbody>
</table>
        </div>
    </div>
</div>
</div>
</body>
</html>


