<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="robots" content="noindex, nofollow"/>
    <title>Créer votre profil</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <style>
        .error {
            color: #ff0000;
        }

        body.image {
            background: url('./images/login.jpeg') no-repeat !important;
        }
        body{
            padding: 10px;
        }
        .panel {
            padding: 10px;

        }

    </style>
</head>
<body class="image">
<div class="container">

    <div class="col-lg-12">
        <div  class="col-md-6 col-xs-12">
            <div class="panel panel-default">
                <div class="panel-head">
                    <h1>Créer votre profil</h1>

                    <c:choose>
                        <c:when test="${param.message==1}">
                            <div class="alert alert-warning" role="alert">
                                <p class="error"> Vous devez choisir un autre nom utilisateur</p>

                            </div>
                        </c:when>
                        <c:when test="${param.message==2}">
                            <div class="alert alert-warning" role="alert">
                                <p class="error"> Les mots de passe doivent être identiques </p>

                            </div>
                        </c:when>
                        <c:otherwise>
                            <br />
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="panel-body">
            <form:form  method="POST" action="/levelup/registerUser" modelAttribute="user">
                <div class="form-group row">
                    <label for="username" class="col-2 col-form-label">Identifiant :</label>
                    <div class="col-10">
                        <form:input path="username" class="form-control"/>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="password" class="col-2 col-form-label">Mot de passe :</label>
                    <div class="col-10">
                    <form:password  path="password"  class="form-control" />
                    </div>
                </div>

                <div class="form-group row">
                    <label for="confirmPassword" class="col-2 col-form-label">Confirmer votre mot de passe :</label>
                    <form:password  path="confirmPassword" class="form-control" />
                </div>

                    <input type="submit" class="btn btn-primary mb-2" value="Créer votre profil">

            </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
