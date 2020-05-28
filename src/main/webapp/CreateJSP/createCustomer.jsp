<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page import="jsp.com.netcracker.students.o3.model.users.Customer" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.Controller" %>
<%@ page import="jsp.com.netcracker.students.o3.model.services.ServiceStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/allStyles.css">
    <link rel="stylesheet" href="/tab.css">
    <title>CreateCustomer</title>
</head>
<body>
<%! Controller controller = ControllerImpl.getInstance();%>
<form action="/CreateJSP/createCustomer.jsp" method="post">
    <div style="size: 200px">
        <div class="name">
            Name: <input type="text" name="fio" value="">
        </div>

        <div class="">
            Login: <input type="text" name="login" value="">
        </div>

        <div class="password">
            Password: <input type="text" name="password">
        </div>

        <div class="selectArea">
            Area: <input type="text" name="area">
        </div>
        <div>
            ConnectedTemplatesId: <input type="text" name="connectedTemplatesId">
        </div>
        <input type="submit" name="save" class="button">
    </div>
</form>
<%
    if(request.getParameter("save")!=null)
    {
        try
        {
            String name = request.getParameter("fio");
            String password = request.getParameter("password");
            String login = request.getParameter("login");
            String area = request.getParameter("area");
            String servicesValue = request.getParameter("connectedTemplatesId");
            String[] templates = servicesValue.split(",");

           Customer customer = ControllerImpl.getInstance()
                   .createCustomer(name,login,password,controller.getArea(BigInteger.valueOf(Long.parseLong(area))));

           BigInteger customerId = customer.getId();
            for (String s : templates)
            {
                if (s != null && !s.isEmpty()){
                    BigInteger templateId = BigInteger.valueOf(Long.parseLong(s));
                    controller.createService(
                            controller.getCustomer(customerId),
                            controller.getTemplate(templateId), ServiceStatus.Planned);
                }
            }
            ControllerImpl.getInstance().setCustomer(customer);

%>
<jsp:forward page="/webEmployeeView.jsp" />
<%
        }catch (Exception e){
            response.getWriter().println("Input error");
        }
    }
%>
</body>
</html>
