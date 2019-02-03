<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="robots" content="noindex, nofollow"/>
    <title>Level up</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">

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
    <div class="container" >
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-head">
                    <h3>Se connecter</h3>

                    <c:if test="${param.message==1}">
                        <div class="alert alert-success" role="alert">
                            <p class="success"> Merci d'avoir créé votre profil !!!</p>
                            <p class="success"> Veuillez vous connecter  : </p>
                            <br />
                        </div>
                    </c:if>

                    <c:if test="${not empty error}">
                        <div class="alert alert-warning" role="alert">
                            Erreur de connexion : <br/>
                                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                        </div>
                    </c:if>

                    <c:url value="/login" var="login"/>

                    <c:if test="${not empty errorMsg}">
                        <div class="alert alert-warning" role="alert">
                            <p>${errorMsg}</p>
                        </div>
                    </c:if>
                </div>
                <div class="panel-body">
            <div  class="col-md-6 col-xs-12">
                <form action="/levelup/login?_csrf=${_csrf.token}" method="post">
                    <div class="form-group row">
                        <label for="username" class="col-2 col-form-label">Votre identifiant</label>
                        <div class="col-10">
                            <input class="form-control" type="text" name="username" value="" id="username">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="password" class="col-2 col-form-label">Votre mot de passe</label>
                        <input  class="form-control" id="password" type="password" value="" name="password" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-primary mb-2" name="Submit" value="Submit">Envoyer</button>
                    <button type="reset" class="btn btn-primary mb-2" name="reset" >Annuler</button>
                </form>
            </div>
            <div class="col-md-6 col-xs-12">
                <div class="panel panel-default" style="margin-top: 23px">
                    <div class="panel-heading">Vous n'avez pas de compte ?</div>
                    <div class="panel-body"> <a  href="/levelup/register">S'enregistrer</a></div>
                </div>
            </div>
        </div>
            </div>
        </div>
    </div>
</body>
</html>
