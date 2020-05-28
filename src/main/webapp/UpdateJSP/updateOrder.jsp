<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page import="jsp.com.netcracker.students.o3.model.orders.Order" %>
<%@ page import="jsp.com.netcracker.students.o3.model.orders.OrderAction" %>
<%@ page import="jsp.com.netcracker.students.o3.model.orders.OrderStatus" %>
<%@ page import="jsp.com.netcracker.students.o3.model.services.Service" %>
<%@ page import="jsp.com.netcracker.students.o3.model.services.ServiceStatus" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="java.util.List" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.Controller" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/allStyles.css">
    <link rel="stylesheet" href="/tabs.css">
    <title>UpdateOrder</title>
    <%!
        BigInteger orderId;
        Order order;
    %>
    <%
        orderId = (BigInteger) request.getSession().getAttribute("updateOrderId");
        order = ControllerImpl.getInstance().getOrder(orderId);
    %>
</head>
<body>
<form action="/UpdateJSP/updateOrder.jsp" method="post">
    <div style="size: 200px">
        <div class="name">
            EmployeeId: <input type="text" name="employeeId" value="<%=order.getEmployee()%>">
        </div>

        <div class="">
            ServiceId: <input type="text" name="serviceId" value="<%=order.getService()%>">
        </div>

        <div class="password">
            Status: <select name="status">
            <option>Entering</option>
            <option>Active</option>
            <option>Processing</option>
            <option>Disconnected</option>
        </select>
        </div>

        <div class="selectArea">
            Action: <select name="action">
            <option>New</option>
            <option>Disconnect</option>
            <option>Resume</option>
            <option>Suspend</option>
        </select>
        </div>
        <input type="submit" name="save" class="button">
    </div>
</form>
<%
    try
    {
        List<Service> services = ControllerImpl.getInstance().getServices();
        for (Service service : services)
        {
            if (service.getId().equals(order.getService()) &&
                    !service.getStatus().equals(ServiceStatus.Disconnected))
            {
                response.getWriter().println("У этого order есть service");
                break;
            }
        }
        if (request.getParameter("save") != null)
        {
            Controller controller = ControllerImpl.getInstance();
            String employeeId = request.getParameter("employeeId");
            String serviceId = request.getParameter("serviceId");
            String status = request.getParameter("status");
            String action = request.getParameter("action");

            BigInteger serviceIdValue = BigInteger.valueOf(Long.parseLong(serviceId));
            Service service = ControllerImpl.getInstance().getService(serviceIdValue);

            order.setEmployee(controller.getEmployee(BigInteger.valueOf(Long.parseLong(employeeId))));
            order.setService(controller.getService(serviceIdValue));
            order.setTemplate(service.getTemplate());
            order.setStatus(OrderStatus.valueOf(status));
            order.setAction(OrderAction.valueOf(action));

            ControllerImpl.getInstance().setOrder(order);

%>
<jsp:forward page="/webEmployeeView.jsp"/>
<%
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
        response.getWriter().println("Input Error");
    }
%>
</body>
</html>
