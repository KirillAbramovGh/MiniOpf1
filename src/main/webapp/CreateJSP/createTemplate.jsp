<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page import="jsp.com.netcracker.students.o3.model.services.Service" %>
<%@ page import="jsp.com.netcracker.students.o3.model.services.ServiceStatus" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="jsp.com.netcracker.students.o3.model.templates.Template" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jsp.com.netcracker.students.o3.model.area.Area" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.Controller" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/allStyles.css">
    <link rel="stylesheet" href="/tab.css">
    <title>CreateTemplate</title>
</head>
<body>
<form action="/CreateJSP/createTemplate.jsp" method="post">
    <div style="size: 200px">
        <div class="">
            Name: <input type="text" name="name">
        </div>

        <div class="">
            Cost: <input type="text" name="cost">
        </div>
        <div class="">
            Description: <input type="text" name="description">
        </div>
        <div>
            PossibleAreasId: <input type="text" name="areas">
        </div>
        <input type="submit" name="save" class="button">
    </div>
</form>
<%
    try
    {
        if (request.getParameter("save") != null)
        {
            Controller controller = ControllerImpl.getInstance();
            String name = request.getParameter("name");
            String cost = request.getParameter("cost");
            String description = request.getParameter("description");
            String[] areas = request.getParameter("areas").split(",");

           Template template = ControllerImpl.getInstance().createTemplate(
                    name,BigDecimal.valueOf(Double.valueOf(cost)),description
            );


            List<Area> set = new ArrayList<>();
            for(String id : areas){
                if(id!= null && !id.isEmpty()){
                    set.add(controller.getArea(BigInteger.valueOf(Long.parseLong(id))));
                }
            }
            template.setPossibleAreas(set);
            ControllerImpl.getInstance().setTemplate(template);

%>
<jsp:forward page="/webEmployeeView.jsp"/>
<%
        }
    }
    catch (Exception e)
    {
        response.getWriter().println("Wrong input!");
    }
%>
</body>
</html>
