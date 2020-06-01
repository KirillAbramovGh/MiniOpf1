<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page import="jsp.com.netcracker.students.o3.model.services.ServiceStatus" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.Controller" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/tabs.css">
    <title>CreateService</title>
</head>
<body>
<form action="/CreateJSP/createService.jsp" method="post">
    <div style="size: 200px">
        <div class="">
            TemplateId: <input type="text" name="templateId">
        </div>

        <div class="">
            UserId: <input type="text" name="userId">
        </div>
        <div class="">
            Status: <select name="status">
            <option>Planned</option>
            <option>Active</option>
            <option>Processing</option>
            <option>Disconnected</option>
            <option>Suspended</option>
        </select>
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
            String templateId = request.getParameter("templateId");
            String userId = request.getParameter("userId");
            String status = request.getParameter("status");

            controller.createService(
                    controller.getCustomer(BigInteger.valueOf(Long.parseLong(userId))),
                    controller.getTemplate(BigInteger.valueOf(Long.parseLong(templateId))),
                    ServiceStatus.valueOf(status)
            );
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
