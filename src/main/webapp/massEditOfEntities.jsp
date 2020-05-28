<%@ page import="jsp.com.netcracker.students.o3.model.Entity" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="java.util.Set" %>
<%@ page import="javax.inject.Inject" %>
<%@ page import="jsp.helpers.EmployeeJspHelper" %>
<%@ page import="jsp.sessionBeans.EmployeeSessionBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/allStyles.css">
    <link rel="stylesheet" href="/tabs.css">
    <title>EditingOfEntities</title>
    <%!
        @Inject
        EmployeeSessionBean employeeSessionBean;
        EmployeeJspHelper jspHelper;
        String type;
    %>
</head>
<body>
<%
    try
    {
        PrintWriter writer = response.getWriter();
        writer.println("<form action='/jspModule_war_exploded/massEditOfEntities.jsp' method='post'>");
        jspHelper = EmployeeJspHelper.getInstance();
        Set<BigInteger> ids = (Set<BigInteger>) request.getSession().getAttribute("massEdit");
        type = (String) request.getSession().getAttribute("massEditType");

        writer.println("<h1>EntitiesToMassEdit</h1>");
        for (BigInteger id : ids)
        {
            Entity entity = employeeSessionBean.getEntity(id);
            response.getWriter().print(entity.getName() + "(" + id + ") ");
        }
        writer.println("<h2>Commons Entities fields </h2>");
        writer.println(jspHelper.getEntitiesEditForm(type));
        writer.println("<input type=\"submit\" name=\"submit\">");
        writer.println("</form>");
        if (request.getParameter("submit") != null)
        {
            employeeSessionBean.setFieldsOfEntities(ids, type, request.getParameterMap());

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
