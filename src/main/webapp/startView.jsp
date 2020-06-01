<%@ page import="javax.inject.Inject" %>
<%@ page import="jsp.helpers.StartJspHelper" %>
<%@ page import="jsp.sessionBeans.StartSessionBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/startPageStyles.css">
    <meta charset="UTF-8">
    <title>MiniOPF</title>
</head>

<body>

<%!
    @Inject
    StartSessionBean startSessionBean;
    StartJspHelper startJspHelper = StartJspHelper.getInstance();
%>

<div class="wrapper">
    <div class="tabs">

        <div class="tabs__nav tabs-nav">
            <div class="tabs-nav__item is-active" data-tab-name="tab-1">Login</div>
            <div class="tabs-nav__item" data-tab-name="tab-2">RegCustomer</div>
            <div class="tabs-nav__item" data-tab-name="tab-3">RegAdmin</div>
        </div>

        <div style="text-align: center;" class="tabs__content">
            <div style="text-align: center" class="tab is-active tab-1">
                <form action="${pageContext.request.contextPath}/start" method="post">
                    <input type="text" name="login"><br/>
                    <input type="password" name="password" value="password"><br/>
                    <input type="submit" name="loginUser" align="center">
                </form>
            </div>

            <div class="tab tab-2">
                <form action="${pageContext.request.contextPath}/start" method="post">
                    <input type="text" name="fio" value="FIO"><br/>
                    <input type="text" name="login" value="login"><br/>
                    <input type="password" name="password" value="password"><br/>
                    <%=startJspHelper.showAreas(startSessionBean)%><br/>
                    <input type="submit" name="regCustomer" value="Register">
                </form>
            </div>

            <div class="tab tab-3">
                <form action="${pageContext.request.contextPath}/start" method="post">
                    <input type="text" name="fio" value="FIO"><br/>
                    <input type="text" name="login" value="login"><br/>
                    <input type="password" name="password" value="password"><br/>
                    <input type="submit" name="regAdmin" value="Register">
                </form>
            </div>

        </div>
    </div>
</div>

<%=startJspHelper.showErrorMessage(session)%>

<script src="${pageContext.request.contextPath}/main1.js"></script>

</body>

<footer>
    <div align="center">
        Разработал в рамках работы над кураторским заданием Романа Храпко в УНЦ Netcracker Абрамов Кирилл
    </div>
</footer>

</html>
