<%@ page import="java.math.BigInteger" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.inject.Inject" %>
<%@ page import="jsp.helpers.EmployeeJspHelper" %>
<%@ page import="jsp.sessionBeans.EmployeeSessionBean" %><%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 4/8/2020
  Time: 5:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
    <title>Choose Entities to Json</title>
</head>
<body>
<%!
    @Inject
    EmployeeSessionBean employeeSessionBean;

    EmployeeJspHelper helper = EmployeeJspHelper.getInstance();
%>
<h2>Choose entities you want to export</h2>
<form action="/JsonView.jsp" method="post">
    <%= helper.checkBocksEntities(employeeSessionBean, (String) request.getSession().getAttribute("exportJson"))%>
    <input type="submit" name="submit" value="submit" class="button">
</form>
<%
    if (request.getParameter("submit") != null)
    {
        List<BigInteger> entities = new ArrayList<>();
        for (String key : request.getParameterMap().keySet())
        {
            if (request.getParameterMap().get(key) != null && !"submit".equals(key))
            {
                BigInteger id;
                try
                {
                    id = BigInteger.valueOf(Long.parseLong(key));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    continue;
                }
                entities.add(id);
            }
        }

        request.getSession().setAttribute("ids", entities);
%>
<jsp:forward page="/exportConfirm.jsp"/>
<%
    }
%>
</body>
</html>
