<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page import="jsp.com.netcracker.students.o3.model.area.Area" %>
<%@ page import="java.math.BigInteger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/tabs.css">
    <title>UpdateArea</title>
    <%!
        BigInteger areaId;
        Area area;
    %>
    <%
        areaId = (BigInteger) request.getSession().getAttribute("updateAreaId");
        area = ControllerImpl.getInstance().getArea(areaId);
    %>
</head>
<body>
<h4>Update Area</h4>
<form action="/UpdateJSP/updateArea.jsp" method="post">
    <div class="update_area">
        <div class="name">
            Name: <input type="text" name="name" value="<%=area.getName()%>">
        </div>

        <div class="">
            Description: <input type="text" name="description" value="<%=area.getDescription()%>">
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

            area.setName(name);
            area.setDescription(description);

            ControllerImpl.getInstance().setArea(area);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.getWriter().println("Input error");
        }
%>
<jsp:forward page="/webEmployeeView.jsp"/>
<%

    }
%>
</body>
</html>
