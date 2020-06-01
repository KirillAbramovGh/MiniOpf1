<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="jsp.helpers.EmployeeJspHelper" %>
<%@ page import="jsp.sessionBeans.EmployeeSessionBean" %>
<%@ page import="javax.inject.Inject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=ControllerImpl.getInstance().getEntity(
            BigInteger.valueOf(Long.parseLong(request.getParameter("entityId")))).getClass().getSimpleName()
    %></title>
    <%!
        @Inject
        EmployeeSessionBean employeeSessionBean;

        private EmployeeJspHelper employeeJspHelper = EmployeeJspHelper.getInstance();
    %>
</head>
<body>
<h1 align="right">
    <form action="${pageContext.request.contextPath}/employeeServlet" method="post" class="header">
        <div></div>
        <div>You are logged in as:<%=employeeSessionBean.getEmployee((BigInteger) request.getSession().getAttribute("id")).getName()%>
        <input type="submit" name="out" value="Out">
            </div>
    </form>
</h1>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/tabs.css">
<h2>JSON Entity</h2>
<pre>
    <%
        try
        { %>
          <%=ControllerImpl.getInstance().getEntity(
                  BigInteger.valueOf(Long.parseLong(request.getParameter("entityId"))))%>
    <%
        }
        catch (Exception e)
        {
            response.getWriter().println("Несуществующая сущность");
        }
    %>
</pre>
</body>
</html>
