<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/tabs.css">
    <title>CreateArea</title>
</head>
<body>
<form action="/CreateJSP/createArea.jsp" method="post">
    <div style="size: 200px">
        <div class="name">
            Name: <input type="text" name="name" value="">
        </div>

        <div class="">
            Description: <input type="text" name="description" value="">
        </div>
        <input type="submit" name="save" class="button">
    </div>
</form>
<%
    if (request.getParameter("save") != null)
    {
        try
        {
            String name = request.getParameter("name");
            String description = request.getParameter("description");

            ControllerImpl.getInstance().createArea(name, description);
        }
        catch (Exception e)
        {
            response.getWriter().println("Input error");
        }
%>
<jsp:forward page="/webEmployeeView.jsp"/>
<%

    }
%>
</body>
</html>
