<%@ page import="javax.inject.Inject" %>
<%@ page import="jsp.sessionBeans.EmployeeSessionBean" %><%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 4/9/2020
  Time: 7:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Import</title>
    <link rel="stylesheet" href="/allStyles.css">
</head>
<body>
<%!
    @Inject
    EmployeeSessionBean employeeSessionBean;
%>
<form action="/importType.jsp" method="post">
    <h2>What should it do with duplicate data?</h2>
    <input type="submit" name="Update" value="Update">
    <input type="submit" name="Ignore" value="Ignore">
</form>
<%
    if (request.getParameter("Update") != null)
    {
        employeeSessionBean.importEntities(false);
%>
<jsp:forward page="/webEmployeeView.jsp"/>
<%
    }
    if (request.getParameter("Ignore") != null)
    {
        employeeSessionBean.importEntities(true);
%>
<jsp:forward page="/webEmployeeView.jsp"/>
<%
    }
%>
</body>
</html>
