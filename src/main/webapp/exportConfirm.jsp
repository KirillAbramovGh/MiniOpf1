<%@ page import="jsp.com.netcracker.students.o3.model.serialization.JsonEntitiesStorage" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.inject.Inject" %>
<%@ page import="jsp.helpers.EmployeeJspHelper" %>
<%@ page import="jsp.sessionBeans.EmployeeSessionBean" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="jsp.com.netcracker.students.o3.model.orders.Order" %>
<%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 4/9/2020
  Time: 10:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
    <title><%=(String) request.getSession().getAttribute("exportJson")%></title>
</head>
<body>
<h2><%=(String) request.getSession().getAttribute("exportJson")+"s to be exported"%></h2>
<%!
    @Inject
    EmployeeSessionBean employeeSessionBean;
    EmployeeJspHelper employeeJspHelper = EmployeeJspHelper.getInstance();
%>
<%
    String res = "[";
    List<BigInteger> ids = (List<BigInteger>) request.getSession().getAttribute("ids");
    JsonEntitiesStorage jsonEntitiesStorage = JsonEntitiesStorage.getInstance();
    switch ((String) request.getSession().getAttribute("exportJson"))
    {
        case "order":
            final List<Order> orders = employeeSessionBean.getOrders(ids);
            jsonEntitiesStorage.setOrders(new HashSet<>(orders));
            res += employeeJspHelper.transformOrdersToHtml(orders); break;
        case "service":
            jsonEntitiesStorage.setServices(new HashSet<>(employeeSessionBean.getServices(ids)));
            res += employeeJspHelper.transformServicesToHtml(employeeSessionBean.getServices(ids)); break;
        case "template":
            jsonEntitiesStorage.setTemplates(new HashSet<>(employeeSessionBean.getTemplates(ids)));
            res += employeeJspHelper.transformTemplatesToHtml(employeeSessionBean.getTemplates(ids)); break;
        case "area":
            jsonEntitiesStorage.setAreas(new HashSet<>(employeeSessionBean.getAreas(ids)));
            res += employeeJspHelper.transformAreasToHtml(employeeSessionBean.getAreas(ids)); break;
        case "employee":
            jsonEntitiesStorage.setEmployees(new HashSet<>(employeeSessionBean.getEmployees(ids)));
            res += employeeJspHelper.transformEmployeesToHtml(employeeSessionBean.getEmployees(ids)); break;
        case "customer":
            jsonEntitiesStorage.setCustomers(new HashSet<>(employeeSessionBean.getCustomers(ids)));
            res += employeeJspHelper.transformCustomersToHtml(employeeSessionBean.getCustomers(ids)); break;
    }
    res+="]";
%>
<pre>
    <%=res%>
    <form action="/exportConfirm.jsp" method="post">
    <input type="submit" name="submit1" value="ok" class="button">
    </form>
</pre>
<%
    if (request.getParameter("submit1") != null)
    {
        jsonEntitiesStorage.exportToFile();
%>
<jsp:forward page="/webEmployeeView.jsp"/>
<%
    }
%>
</body>
</html>
